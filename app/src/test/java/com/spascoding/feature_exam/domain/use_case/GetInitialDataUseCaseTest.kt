package com.spascoding.feature_exam.domain.use_case

import com.spascoding.englishstructure.feature_exam.data.repository.ExamPatternRepositoryImpl
import com.spascoding.englishstructure.feature_exam.domain.repository.ExamPatternRepository
import com.spascoding.englishstructure.feature_exam.domain.use_case.GetExamPatternsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetInitialDataUseCaseTest {

    private lateinit var repository: ExamPatternRepository

    @Before
    fun setup() {
        repository = ExamPatternRepositoryImpl()
    }

    @Test
    fun getBeginnerData() = runBlocking {
        val data = GetExamPatternsUseCase(repository).invoke()
        for (exam in data) {
//            for (value in exam.info()) {
//                println(value)
//            }
        }
    }

}