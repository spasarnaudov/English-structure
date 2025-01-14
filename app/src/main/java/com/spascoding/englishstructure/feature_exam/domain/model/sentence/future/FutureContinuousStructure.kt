package com.spascoding.englishstructure.feature_exam.domain.model.sentence.future

import com.spascoding.englishstructure.feature_exam.data.data_source.sentence_parts.Verbs
import com.spascoding.englishstructure.feature_exam.domain.model.noun.Noun
import com.spascoding.englishstructure.feature_exam.domain.model.sentence.Structure
import com.spascoding.englishstructure.feature_exam.domain.model.verb.Verb

/**
 * The future continuous is made up of two elements:
 * the simple future of the verb "to be" + the present participle (base+ing)
 */
class FutureContinuousStructure(
    private val subject: Noun,
    private val verb: Verb,
    private val objectVal: String,
) : Structure {

    /**
     * Positive: Subject + will be + verb-ing object.
     * "She will be walking around."
     */
    override fun positive(): String {
        if (verb == Verbs.toBe) {
            return "${subject.build()} will be being $objectVal."
        }
        return "${subject.build()} will be ${verb.continuous()} $objectVal."
    }

    /**
     * Negative: Subject + will not be + verb-ing + object.
     * "She will not be walking around."
     */
    override fun negative(): String {
        if (verb == Verbs.toBe) {
            return "${subject.build()} will not be being $objectVal."
        }
        return "${subject.build()} will not be ${verb.continuous()} $objectVal."
    }

    /**
     * Question: Will + subject + be + verb-ing + object?
     * "Will she be walking around?"
     */
    override fun question(): String {
        if (verb == Verbs.toBe) {
            return "will ${subject.build()} be being $objectVal?"
        }
        return "will ${subject.build()} be ${verb.continuous()} $objectVal?"
    }
}