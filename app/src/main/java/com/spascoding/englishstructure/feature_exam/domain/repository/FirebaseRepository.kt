package com.spascoding.englishstructure.feature_exam.domain.repository

interface FirebaseRepository {
    fun getUnlockTensAccuracy(): Int
    fun getUnlockTensSentenceCount(): Int
}