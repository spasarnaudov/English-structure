package com.spascoding.englishstructure.feature_exam.domain.use_case

import com.spascoding.englishstructure.feature_exam.domain.use_case.database.GetTenseInfoUseCase
import com.spascoding.englishstructure.feature_exam.domain.use_case.database.GetUserInfoUseCase
import javax.inject.Inject

data class TenseUseCases @Inject constructor(
    val getUserInfoUseCase: GetUserInfoUseCase,
    val getTenseInfoUseCase: GetTenseInfoUseCase,
)