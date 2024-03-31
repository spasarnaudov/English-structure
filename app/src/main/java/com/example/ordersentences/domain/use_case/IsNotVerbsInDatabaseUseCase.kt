package com.example.ordersentences.domain.use_case

import com.example.ordersentences.domain.repository.VerbRepository

class IsNotVerbsInDatabaseUseCase(
    private val repository: VerbRepository
) {

    suspend operator fun invoke(): Boolean {
        return repository.isNoVerbs()
    }

}