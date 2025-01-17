package com.spascoding.englishstructure.feature_exam.presentation.topic_detail_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.spascoding.englishstructure.feature_exam.domain.enums.Tense
import com.spascoding.englishstructure.feature_exam.domain.model.sentence.entity.Sentence
import com.spascoding.englishstructure.feature_exam.domain.use_case.TopicsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TopicDetailViewModel @Inject constructor(
    private val topicsUseCases: TopicsUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

//    private val _state = mutableStateOf(TopicDetailState())
//    val state: State<TopicDetailState> = _state


    fun getSentences(): Flow<List<Sentence>> {
        return topicsUseCases.getSentencesUseCase.invoke()
    }

}