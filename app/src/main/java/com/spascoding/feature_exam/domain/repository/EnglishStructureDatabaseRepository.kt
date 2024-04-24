package com.spascoding.feature_exam.domain.repository

import com.spascoding.feature_exam.domain.enums.Tens
import com.spascoding.feature_exam.domain.model.sentence.entity.Sentence

interface EnglishStructureDatabaseRepository {
    suspend fun upsertSentence(sentence: Sentence)
    suspend fun upsertSentences(sentences: List<Sentence>)
    suspend fun getExamNames(tens: Tens): List<String>
    suspend fun getSentence(tens: Tens): Sentence
    suspend fun getSentences(tens: Tens, examName: String): List<Sentence>
    suspend fun getSentenceAndIncrementUsageCount(tens: Tens): Sentence
}