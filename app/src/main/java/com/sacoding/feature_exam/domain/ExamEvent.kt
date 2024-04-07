package com.sacoding.feature_exam.domain

import com.sacoding.feature_exam.domain.model.Exam

sealed interface ExamEvent {
    data object StartGame : ExamEvent
    data class EndGame(val answerText: String) : ExamEvent
    data class EnterText(val answerText: String) : ExamEvent
    data class SelectExam(val exam: Exam) : ExamEvent
    data class SelectLevel(val level: Level) : ExamEvent
}