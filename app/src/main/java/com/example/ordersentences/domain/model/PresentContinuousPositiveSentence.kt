package com.example.ordersentences.domain.model

class PresentContinuousPositiveSentence(
    private val subject: String,
    private val verb: Verb,
    private val objectVal: String,
    private val prepositionalPhrase: String = "",
): Sentence {
    override fun buildSentence(): String {
        return "$subject ${verb.baseForm}ing $objectVal."
    }
}