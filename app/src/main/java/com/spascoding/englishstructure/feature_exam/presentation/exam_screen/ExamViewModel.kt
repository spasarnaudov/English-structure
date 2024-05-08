package com.spascoding.englishstructure.feature_exam.presentation.exam_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.spascoding.englishstructure.feature_exam.domain.enums.Tense
import com.spascoding.englishstructure.feature_exam.domain.model.sentence.entity.Sentence
import com.spascoding.englishstructure.feature_exam.domain.repository.FirebaseRepository
import com.spascoding.englishstructure.feature_exam.domain.use_case.CommonUseCases
import com.spascoding.englishstructure.feature_exam.domain.use_case.TopicsUseCases
import com.spascoding.englishstructure.feature_exam.presentation.utils.scratchWords
import com.spascoding.englishstructure.feature_exam.presentation.utils.shuffleSentence
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(
    private val commonUseCases: CommonUseCases,
    private val topicsUseCases: TopicsUseCases,
    private val savedStateHandle: SavedStateHandle,
    private val firebaseRepository: FirebaseRepository,
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
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        val newSentence = commonUseCases.getSentenceUseCase.invoke(Tense.fromInt(tense), topic)
                        val history = topicsUseCases.getUsedSentencesByTenseAndTopicUseCase.invoke(Tense.fromInt(tense), topic, firebaseRepository.getAccuracySentencesCount())
                        withContext(Dispatchers.Main) {
                            _state.value = state.value.copy(
                                sentences = listOf(newSentence),
                                sentence = newSentence.value,
                                shuffledSentence = newSentence.value.shuffleSentence(" / "),
                                history = history,
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: ExamEvent) {
        when (event) {
            is ExamEvent.EnterText -> {
                _state.value = state.value.copy(
                    enteredSentence = event.answerText,
                    answerText = mutableStateOf(TextFieldValue(text = event.answerText, selection = TextRange(event.answerText.length))),
                )
            }

            is ExamEvent.CheckExam -> {
                val originSentence = state.value.sentences[0]

                val tens = state.value.tense
                val topic = state.value.topic

                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        updateCurrentSentence(originSentence, event.answerText)
                        val newSentence = commonUseCases.getSentenceUseCase.invoke(tens, topic)
                        val history = topicsUseCases.getUsedSentencesByTenseAndTopicUseCase.invoke(tens, topic, firebaseRepository.getAccuracySentencesCount())
                        withContext(Dispatchers.Main) {
                            _state.value = state.value.copy(
                                sentences = listOf(newSentence),
                                sentence = newSentence.value,
                                shuffledSentence = newSentence.value.shuffleSentence(" / "),
                                enteredSentence = "",
                                answerText = mutableStateOf(TextFieldValue(text = "", selection = TextRange(0))),
                                history = history,
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun updateCurrentSentence(originSentence: Sentence, answerText: String) {
        var mistakeCount = originSentence.mistakeCount
        if (!isCorrectAnswer()) {
            mistakeCount++
        }
        val sentence = originSentence.copy(
            usedCount = state.value.sentences[0].usedCount + 1,
            mistakeCount = mistakeCount,
            userValue = answerText,
            userValueTime = Date().time
        )
        commonUseCases.updateSentenceUseCase.invoke(sentence)
    }

    fun getShuffledText(): AnnotatedString {
        return scratchWords(
            textToScratch = state.value.enteredSentence,
            shuffledSentence = state.value.shuffledSentence
        )
    }

    private fun isCorrectAnswer(): Boolean {
        return state.value.enteredSentence == state.value.sentence
    }

}