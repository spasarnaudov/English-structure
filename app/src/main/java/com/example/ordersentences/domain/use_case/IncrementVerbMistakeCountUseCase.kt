package com.example.ordersentences.domain.use_case

import com.example.ordersentences.domain.model.Verb
import com.example.ordersentences.domain.repository.VerbRepository

class IncrementVerbMistakeCountUseCase(
    private val repository: VerbRepository
) {

    suspend operator fun invoke(verb: Verb) {
        val modifiedVerb = verb.copy(
            mistakeCount = verb.mistakeCount + 1
        )
        return repository.upsertVerb(modifiedVerb)
    }

}