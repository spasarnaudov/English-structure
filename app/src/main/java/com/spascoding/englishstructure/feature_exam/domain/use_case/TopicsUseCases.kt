package com.spascoding.englishstructure.feature_exam.domain.use_case

import com.spascoding.englishstructure.feature_exam.domain.use_case.database.GetTopicsUseCase
import com.spascoding.englishstructure.feature_exam.domain.use_case.database.GetTopicSentencesUseCase
import com.spascoding.englishstructure.feature_exam.domain.use_case.database.GetTopicInfoUseCase
import com.spascoding.englishstructure.feature_exam.domain.use_case.database.GetRecentSentencesUseCase
import com.spascoding.englishstructure.feature_exam.domain.use_case.database.GetTenseInfoUseCase
import javax.inject.Inject

data class TopicsUseCases @Inject constructor(
    val getTenseInfoUseCase: GetTenseInfoUseCase,
    val getSentencesUseCase: GetTopicSentencesUseCase,
    val getTopicsUseCase: GetTopicsUseCase,
    val getHistoryUseCase: GetRecentSentencesUseCase,
    val getTopicInfoUseCase: GetTopicInfoUseCase,
)