package com.storygenerator.features.text_writer.presentation.states

import com.storygenerator.features.text_writer.domain.models.WriterItem
import com.storygenerator.features.text_writer.presentation.models.llm.LlmConfig

data class WriterState(
    val llmConfig: LlmConfig? = null,

    val writerItem : WriterItem = WriterItem(),
    val writerItemsHistory : List<WriterItem> = emptyList(),

    val selectedLength : String = "short",
    val selectedControllersMap : Map<String , String> = emptyMap(),
//   (title : String , prompt : String)

    //Textfield values
    val prompt : String = "",
    val promptErrorr : String = "",
    val promptLength : Int = 0,
    val promptMaxLength : Int = 400,


    //loading
    val isLoading : Boolean = false
)