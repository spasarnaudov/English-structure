package com.sacoding.feature_exam.domain.model.noun

import com.sacoding.feature_exam.domain.model.noun.MultiSubjectPronoun
import com.sacoding.feature_exam.domain.model.noun.SingularPronoun
import com.sacoding.feature_exam.domain.model.noun.SubjectPronoun
import org.junit.Test

class MultiSubjectPronounTest {

    @Test
    fun testBuild() {
        val subjectPronouns = mutableListOf(
            SingularPronoun(SingularPronoun.Values.YOU),
            SubjectPronoun("Emily"),
        )
        val twoSubjectPronoun = MultiSubjectPronoun(subjectPronouns).build()
        println(twoSubjectPronoun)
        assert(twoSubjectPronoun == "${SingularPronoun.Values.YOU.value} and Emily")

        subjectPronouns.add(SubjectPronoun("John"))

        val threeSubjectPronoun = MultiSubjectPronoun(subjectPronouns).build()
        println(threeSubjectPronoun)
        assert(threeSubjectPronoun == "${SingularPronoun.Values.YOU.value}, Emily and John")
    }
}