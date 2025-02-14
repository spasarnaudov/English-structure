package com.spascoding.englishstructure.feature_exam.presentation.topics_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.spascoding.englishstructure.feature_exam.domain.enums.Tense
import com.spascoding.englishstructure.feature_exam.domain.model.TenseInfo
import com.spascoding.englishstructure.feature_exam.domain.model.TopicInfo
import com.spascoding.englishstructure.feature_exam.domain.use_case.TopicsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TopicsViewModel @Inject constructor(
    private val topicsUseCases: TopicsUseCases,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = mutableStateOf(TopicsViewModelState())
    val state: State<TopicsViewModelState> = _state

    init {
        savedStateHandle.get<Int>("tense")?.also { tenseInt ->
            val tense: Tense = Tense.fromInt(tenseInt)
            _state.value = state.value.copy(
                tense = tense,
            )
        }
    }

    fun getTenseInfoFlow(): Flow<List<TenseInfo>> {
        return topicsUseCases.getTenseInfoUseCase.invoke()
    }

    fun getTopicInfoFlow(): Flow<List<TopicInfo>> {
        return topicsUseCases.getTopicInfoUseCase.invoke(state.value.tense)
    }

    fun getTopics(): Flow<List<String>> {
        return topicsUseCases.getTopicsUseCase.invoke(state.value.tense)
    }
}