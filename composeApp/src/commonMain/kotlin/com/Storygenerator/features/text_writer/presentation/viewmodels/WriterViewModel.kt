package com.storygenerator.features.text_writer.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.storygenerator.core.events.controllers.SnackbarController
import com.storygenerator.core.events.navigator.interfaces.Navigator
import com.storygenerator.core.utils.logging.Log
import com.storygenerator.features.text_writer.domain.models.WriterItem
import com.storygenerator.features.text_writer.domain.repository.WriterRepository
import com.storygenerator.features.text_writer.presentation.events.WriterEvents
import com.storygenerator.features.text_writer.presentation.models.llm.LlmConfig
import com.storygenerator.features.text_writer.presentation.states.WriterState
import com.storygenerator.features.text_writer.presentation.ui_main.nav_graphs.WriterScreens
import kmpstarter.composeapp.generated.resources.Res
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class WriterViewModel(private val repository: WriterRepository , private val navigator: Navigator) : ViewModel() {

    companion object {
        private val PATH_LLM_CONFIG = "files/llm_config.json"
    }



    private val _state = MutableStateFlow(WriterState())
    val state = _state.asStateFlow()

    private var generateTextJob: Job? = null
    private var readLlmConfigJob : Job? = null

    private var insertHistoryJob : Job? = null
    private var getHistoryJob : Job? = null

    init {
        getHistory()
        readllmConfig()
    }

    fun onEvent(events: WriterEvents) {
        when (events) {
            is WriterEvents.GenerateText -> generateText()
        }
    }

    private fun generateText() {


        generateTextJob?.cancel()
        generateTextJob = CoroutineScope(Dispatchers.IO).launch {

            if (state.value.prompt.isEmpty() || _state.value.promptErrorr.isNotEmpty()){
                SnackbarController.sendAlert(
                    message = "Please enter Prompt"
                )
                return@launch
            }

            if (!isAllControllerSelected()){
                SnackbarController.sendAlert(message = "Please select all config")
                return@launch
            }

            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            val prompt = makePrompt()
            try {
               val writerItem =  repository.generateText(prompt = prompt)
                _state.update {
                  it.copy(
                      isLoading = false,
                      writerItem = writerItem,
                      promptLength = 0,
                      prompt = ""
                  )
                }
                Log.d("promptCheck" ,"Prompt response $writerItem")
                insertHistory(writerItem = writerItem)
                navigator.navigateTo(
                    route = WriterScreens.Preview
                )
            }
            catch (e : Exception){
               _state.update {
                   it.copy(
                       isLoading = false
                   )
               }
            }

        }
    }

    private fun insertHistory(writerItem: WriterItem) {
        insertHistoryJob?.cancel()
        insertHistoryJob = CoroutineScope(Dispatchers.IO).launch{
            repository.insertHistory(
                writerItem = writerItem).onSuccess {
              getHistory()
            }.onFailure {

            }
        }
    }

    private fun getHistory() {
        getHistoryJob?.cancel()
        getHistoryJob = CoroutineScope(Dispatchers.IO).launch {
            repository.getHistory().collect { List ->
                _state.update {
                    it.copy(
                        writerItemsHistory = List
                    )
                }

            }
        }
    }


    private fun readllmConfig(){
        readLlmConfigJob?.cancel()
        readLlmConfigJob = CoroutineScope(Dispatchers.IO).launch {
            val llmConfigRawJson = Res.readBytes(path = PATH_LLM_CONFIG).decodeToString()
            val llmconfig = Json.decodeFromString<LlmConfig>(llmConfigRawJson)

            _state.update {
                it.copy(llmConfig = llmconfig )
            }
        }
    }

    private fun makePrompt(): String {
        val llmConfig = _state.value.llmConfig ?: throw IllegalStateException("Llm config is null")
         var masterPrompt =  llmConfig.systemInstructions.prompt
        val selectedLengthValue = _state.value.selectedLength
        val selectedLengthPrompt = llmConfig.systemInstructions.lengthPrompts.getPrompt(
            length = selectedLengthValue
        )

        masterPrompt = masterPrompt.replace(
            "{lengthInstructions}",
            selectedLengthPrompt
        )

        masterPrompt = masterPrompt.replace(
           "{user_prompt}",
            _state.value.prompt
        )

        val selectedControllerMap = _state.value.selectedControllersMap

        selectedControllerMap.forEach { (title  , promptKey) ->
            val controller = llmConfig.controllers.find {
                it.title == title
            } ?: throw IllegalStateException("Controller with title $title not found")


            val masterPromptKey = controller.promptKey

            val controllerPrompt = controller.prompts[promptKey] ?: throw IllegalStateException("Controller prompt with key $promptKey not found")

            masterPrompt = masterPrompt.replace(
                masterPromptKey,
                controllerPrompt

            )
        }

        return masterPrompt

    }


    private fun isAllControllerSelected() : Boolean{
        val controllers = _state.value.llmConfig?.controllers ?: throw IllegalStateException ("Llm config is null")

        val selectedControllerMap = _state.value.selectedControllersMap

        val result = controllers.filter {
            if(!it.required)  return true

           it.title in selectedControllerMap.keys
        }

        return result.size == controllers.size
    }


    fun onWriterItemChange(Item : WriterItem) = _state.update {
        it.copy(
            writerItem = Item
        )
    }


    fun onPromptChange(value : String){
        val error = when{
            value.length > _state.value.promptMaxLength -> "Max length is ${_state.value.promptMaxLength}"
            value.isEmpty() -> "Please enter prompt"
            else -> ""
        }

        _state.update {
            it.copy(
                prompt = value,
                promptErrorr = error,
                promptLength =  value.length
            )
        }
    }

    fun onLengthChange(value : String){
        _state.update {
            it.copy(
                selectedLength = value
            )
        }
    }

    fun onControllerSelected(controllerTitle : String , prompt : String){
        _state.update {
            it.copy(
               selectedControllersMap = it.selectedControllersMap + mapOf(controllerTitle to prompt )
            )
        }

    }







}
