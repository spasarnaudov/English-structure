package com.spascoding.englishstructure.feature_exam.presentation.exam_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.spascoding.englishstructure.feature_exam.domain.enums.Tense
import com.spascoding.englishstructure.feature_exam.domain.model.sentence.entity.Sentence
import com.spascoding.englishstructure.feature_exam.domain.repository.ConfigRepository
import com.spascoding.englishstructure.feature_exam.domain.use_case.CommonUseCases
import com.spascoding.englishstructure.feature_exam.domain.use_case.TopicsUseCases
import com.spascoding.englishstructure.feature_exam.presentation.utils.scratchWords
import com.spascoding.englishstructure.feature_exam.presentation.utils.shuffleSentence
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(
    private val commonUseCases: CommonUseCases,
    private val topicsUseCases: TopicsUseCases,
    private val savedStateHandle: SavedStateHandle,
    private val configRepository: ConfigRepository,
) : ViewModel() {

    private val _state = mutableStateOf(ExamViewModelState())
    val state: State<ExamViewModelState> = _state

    init {
        savedStateHandle.get<Int>("tense")?.also { tense ->
            savedStateHandle.get<String>("topic")?.also { topic ->
                _state.value = state.value.copy(
                    tense = Tense.fromInt(tense),
                    topic = topic
                )
                loadSentenceFromDatabase(state.value.tense, state.value.topic)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun onEvent(event: ExamEvent) {
        when (event) {
            is ExamEvent.EnterText -> {
                _state.value = state.value.copy(
                    userSentence = event.answerText,
                )
            }

            is ExamEvent.CheckExam -> {
                val originSentence = state.value.sentence ?: return
                GlobalScope.launch {
                    loadSentenceFromDatabase(state.value.tense, state.value.topic)
                    withContext(Dispatchers.IO) {
                        updateCurrentSentence(originSentence, event.answerText)
                    }
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadSentenceFromDatabase(tense: Tense, topic: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val newSentence = commonUseCases.getSentenceUseCase.invoke(tense, topic)
            withContext(Dispatchers.Main) {
                _state.value = state.value.copy(
                    sentence = newSentence,
                    originalSentence = newSentence.value,
                    shuffledSentence = newSentence.value.shuffleSentence(" / "),
                    userSentence = "",
                )
            }
        }
    }

    private suspend fun updateCurrentSentence(sentence: Sentence, newUserValue: String) {
        var mistakeCount = sentence.mistakeCount
        if (!isCorrectAnswer()) {
            mistakeCount++
        }
        val newSentence = sentence.copy(
            usedCount = sentence.usedCount + 1,
            mistakeCount = mistakeCount,
            userValue = newUserValue,
            userValueTime = Date().time
        )
        commonUseCases.updateSentenceUseCase.invoke(newSentence)
    }

    fun getShuffledText(): AnnotatedString {
        return scratchWords(
            textToScratch = state.value.userSentence,
            text = state.value.shuffledSentence
        )
    }

    private fun isCorrectAnswer(): Boolean {
        return state.value.userSentence == state.value.originalSentence
    }

    fun getRecentSentences(): Flow<List<Sentence>> {
        return topicsUseCases.getHistoryUseCase.invoke(
            state.value.tense,
            state.value.topic,
            configRepository.getRecentSentenceCount()
        )
    }

}