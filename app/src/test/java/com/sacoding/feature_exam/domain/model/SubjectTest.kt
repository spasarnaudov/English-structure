package com.sacoding.feature_exam.domain.model

import com.sacoding.feature_exam.domain.Article
import org.junit.Test

class SubjectTest {

    @Test
    fun testAYoungBoy() {
        val boy = SingularNoun(
            article = Article.DEFINITE,
            adjective = Adjective("little"),
            value = "boy"
        )
        val i = SingularPronoun(SingularPronoun.Values.I)
        val they = PluralPronoun(PluralPronoun.Values.THEY)
        val john = SubjectPronoun("John")
        val multiSubjectPronoun = MultiSubjectPronoun(listOf(boy, john, they, i))
        val subject = Subject(multiSubjectPronoun)
        println(subject.build())
    }
}