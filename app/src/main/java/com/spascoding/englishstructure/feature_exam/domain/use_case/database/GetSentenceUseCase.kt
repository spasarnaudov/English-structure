package com.spascoding.englishstructure.feature_exam.domain.use_case.database

import com.spascoding.englishstructure.feature_exam.data.repository.EnglishStructureDatabaseRepositoryImpl
import com.spascoding.englishstructure.feature_exam.domain.enums.Tense
import com.spascoding.englishstructure.feature_exam.domain.model.sentence.entity.Sentence
import javax.inject.Inject

class GetSentenceUseCase @Inject constructor(
    private val repository: EnglishStructureDatabaseRepositoryImpl
) {

    suspend operator fun invoke(tense: Tense, examName: String): Sentence {
        return repository.getSentence(tense, examName)
    }

}