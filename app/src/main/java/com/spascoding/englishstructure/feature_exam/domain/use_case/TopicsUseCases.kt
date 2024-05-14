package com.spascoding.englishstructure.feature_exam.domain.use_case

import com.spascoding.englishstructure.feature_exam.domain.use_case.database.GetTopicsUseCase
import com.spascoding.englishstructure.feature_exam.domain.use_case.database.GetTopicSentencesUseCase
import com.spascoding.englishstructure.feature_exam.domain.use_case.database.GetTopicInfoUseCase
import com.spascoding.englishstructure.feature_exam.domain.use_case.database.GetHistoryUseCase
import javax.inject.Inject

data class TopicsUseCases @Inject constructor(
    val getSentencesUseCase: GetTopicSentencesUseCase,
    val getTopicsUseCase: GetTopicsUseCase,
    val getHistoryUseCase: GetHistoryUseCase,
    val getTopicInfoUseCase: GetTopicInfoUseCase,
)