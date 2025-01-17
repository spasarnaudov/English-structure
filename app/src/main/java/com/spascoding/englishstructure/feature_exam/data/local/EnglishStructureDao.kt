package com.spascoding.englishstructure.feature_exam.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.spascoding.englishstructure.feature_exam.domain.model.TenseInfo
import com.spascoding.englishstructure.feature_exam.domain.model.TopicInfo
import com.spascoding.englishstructure.feature_exam.domain.model.UserInfo
import com.spascoding.englishstructure.feature_exam.domain.model.sentence.entity.Sentence
import kotlinx.coroutines.flow.Flow

@Dao
interface EnglishStructureDao {
    /**
     * import not existing sentences into database
     */
    @Delete
    suspend fun removeExistedSentences(sentences: List<Sentence>)

    /**
     * import not existing sentences into database
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun importNotExistedSentences(sentences: List<Sentence>)

    /**
     * update sentence during the exercise
     */
    @Update
    suspend fun updateSentence(sentence: Sentence)

    /**
     * get sentence for exercise
     */
    @Query("SELECT * FROM sentences ORDER BY usedCount ASC, mistakeCount DESC, RANDOM() LIMIT 1")
    suspend fun getSentence(): Sentence

    // TENSE QUERIES

    /**
     * Get all sentences for specific tens and topic
     */
    @Query("SELECT * FROM sentences")
    suspend fun getAllSentences(): List<Sentence>

    @Query(
        "SELECT " +
                "tense, " +
                "CAST(SUM(usedCount - mistakeCount) AS FLOAT) / SUM(usedCount) AS accuracy," +
                "COUNT(*) AS sentenceCount " +
                "FROM sentences " +
                "WHERE usedCount > 0 "
    )
    fun getUserAccuracy(): Flow<UserInfo>

    @Query(
        "SELECT " +
                "tense, " +
                "CAST(SUM(usedCount - mistakeCount) AS FLOAT) / SUM(usedCount) AS accuracy," +
                "COUNT(*) AS sentenceCount " +
                "FROM sentences " +
                "WHERE usedCount > 0 " +
                "GROUP BY tense"
    )
    fun getTenseInfo(): Flow<List<TenseInfo>>

    // TOPICS QUERIES

    /**
     * Get all sentences for specific tens and topic
     */
    @Query("SELECT * FROM sentences WHERE usedCount > 0 ORDER BY userValueTime DESC")
    fun getSentences(): Flow<List<Sentence>>

    @Query("SELECT DISTINCT topic FROM sentences WHERE tense=:tens")
    fun getTopics(tens: Int): Flow<List<String>>

    @Query("SELECT * FROM sentences WHERE usedCount > 0 ORDER BY userValueTime DESC LIMIT :sentenceCount")
    fun getRecentSentences(sentenceCount: Int): Flow<List<Sentence>>

    @Query(
        "SELECT topic," +
                " (CAST(SUM(usedCount - mistakeCount) AS FLOAT) / NULLIF(CAST(SUM(usedCount) AS FLOAT), 0)) AS accuracy," +
                " COUNT(*) AS sentenceCount" +
                " FROM sentences" +
                " WHERE tense =:tense AND usedCount > 0" +
                " GROUP BY topic"
    )
    fun getTopicInfo(tense: Int): Flow<List<TopicInfo>>
}