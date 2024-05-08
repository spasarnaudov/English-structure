package com.spascoding.englishstructure.feature_exam.presentation.topics_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.spascoding.englishstructure.feature_exam.domain.enums.Tense
import com.spascoding.englishstructure.feature_exam.domain.model.getElementByTopic
import com.spascoding.englishstructure.feature_exam.domain.repository.FirebaseRepository
import com.spascoding.englishstructure.feature_exam.domain.use_case.CommonUseCases
import com.spascoding.englishstructure.feature_exam.domain.use_case.TopicsUseCases
import com.spascoding.englishstructure.feature_exam.domain.utils.SentencesGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class TopicsViewModel @Inject constructor(
    private val commonUseCases: CommonUseCases,
    private val topicsUseCases: TopicsUseCases,
    private val savedStateHandle: SavedStateHandle,
    private val firebaseRepository: FirebaseRepository,
) : ViewModel() {

    private val _state = mutableStateOf(TopicsViewModelState())
    val state: State<TopicsViewModelState> = _state

    init {
        savedStateHandle.get<Int>("tense")?.also { tenseInt ->
            val tense: Tense = Tense.fromInt(tenseInt)

            _state.value = state.value.copy(
                tense = tense,
            )

            saveSentencesToDatabase(tense).also {
                getExams(tense)
            }

            GlobalScope.launch {
                withContext(Dispatchers.IO) {
                    val topicsAccuracyInfo = topicsUseCases.getTopicsAccuracyInfoUseCase.invoke(tense, firebaseRepository.getAccuracySentencesCount())
                    withContext(Dispatchers.Main) {
                        _state.value = state.value.copy(
                            topicsAccuracyInfo = topicsAccuracyInfo,
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: TopicsScreenEvent) {
        when (event) {
            is TopicsScreenEvent.SelectTopic -> {

            }
            is TopicsScreenEvent.SelectTopicInfo -> {

            }
        }
    }

    private fun saveSentencesToDatabase(tense: Tense) {
        GlobalScope.launch {
            var topics: List<String>
            withContext(Dispatchers.IO) {
                val examPatterns = commonUseCases.getExamPatternsUseCase.invoke()
                val sentences = SentencesGenerator(tense, examPatterns).generate()
                commonUseCases.importNotExistedSentencesUseCase.invoke(sentences)
                topics = topicsUseCases.getTopicsUseCase.invoke(tense)
            }.also {
                withContext(Dispatchers.Main) {
                    _state.value = state.value.copy(
                        topics = topics,
                    )
                }
            }
        }
    }

    private fun getExams(tense: Tense) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val examNames = topicsUseCases.getTopicsUseCase.invoke(tense)
                withContext(Dispatchers.Main) {
                    _state.value = state.value.copy(
                        topics = examNames,
                    )
                }
            }
        }
    }

    fun isSuccess(topic: String): Boolean {
        return getAccuracy(topic) >= firebaseRepository.getUnlockTopicAccuracy()
                && getSentencesCount(topic) >= firebaseRepository.getUnlockTopicSentenceCount()
    }

    fun getAccuracy(topic: String): Int {
        val tenseAccuracyInfo = state.value.topicsAccuracyInfo.getElementByTopic(topic)
        if (tenseAccuracyInfo != null) {
            return tenseAccuracyInfo.accuracy()
        }
        return 0
    }

    fun getLastSentencesCount(topic: String): Int {
        val tenseAccuracyInfo = state.value.topicsAccuracyInfo.getElementByTopic(topic)
        if (tenseAccuracyInfo != null) {
            val sentencesCounts = tenseAccuracyInfo.sentencesCount
            if (sentencesCounts > firebaseRepository.getAccuracySentencesCount()) {
                return firebaseRepository.getAccuracySentencesCount()
            }
            return sentencesCounts
        }
        return 0
    }

    fun getSentencesCount(topic: String): Int {
        val tenseAccuracyInfo = state.value.topicsAccuracyInfo.getElementByTopic(topic)
        if (tenseAccuracyInfo != null) {
            return tenseAccuracyInfo.sentencesCount
        }
        return 0
    }

}