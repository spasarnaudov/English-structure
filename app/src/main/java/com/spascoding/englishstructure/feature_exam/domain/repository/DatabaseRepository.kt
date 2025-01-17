package com.spascoding.englishstructure.feature_exam.domain.repository

import com.spascoding.englishstructure.feature_exam.domain.enums.Tense
import com.spascoding.englishstructure.feature_exam.domain.model.TenseInfo
import com.spascoding.englishstructure.feature_exam.domain.model.TopicInfo
import com.spascoding.englishstructure.feature_exam.domain.model.UserInfo
import com.spascoding.englishstructure.feature_exam.domain.model.sentence.entity.Sentence
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun removeExistedSentences(sentences: List<Sentence>)
    suspend fun importNotExistedSentences(sentences: List<Sentence>)
    suspend fun updateSentence(sentence: Sentence)
    suspend fun getSentence(): Sentence
    fun getUserInfo(): Flow<UserInfo>
    // TENSES
    suspend fun getAllSentences(): List<Sentence>
    fun getTenseInfo(): Flow<List<TenseInfo>>
    // TOPICS
    fun getTopicInfo(tense: Tense): Flow<List<TopicInfo>>
    fun getTopicSentences(): Flow<List<Sentence>>
    fun getTopics(tense: Tense): Flow<List<String>>
    fun getRecentSentences(tense: Tense, topic: String, sentenceCount: Int): Flow<List<Sentence>>
}