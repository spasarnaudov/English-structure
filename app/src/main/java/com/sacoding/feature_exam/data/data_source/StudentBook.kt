package com.sacoding.feature_exam.data.data_source

import com.sacoding.feature_exam.domain.Level
import com.sacoding.feature_exam.domain.Tens
import com.sacoding.feature_exam.domain.model.Lessen
import com.sacoding.feature_exam.domain.model.addArticle

object StudentBook {
    val presentSimpleLessens = listOf(
        Lessen(
            name = "Where are you from?",
            level = Level.BEGINNER,
            tenses = listOf(Tens.PRESENT_SIMPLE),
            subjects = Pronouns.Subject.singular.plus(Pronouns.Subject.plural).plus(Pronouns.Subject.names),
            verbs = listOf(Verbs.toBe),
            prepositions = Prepositions.originOrSourceOfMovement.plus(Prepositions.enclosedSpaces),
            objectVals = Objects.countries.plus(Objects.cities),
        ),
        Lessen(
            name = "She is American",
            level = Level.BEGINNER,
            tenses = listOf(Tens.PRESENT_SIMPLE),
            subjects = Pronouns.Subject.singular.plus(Pronouns.Subject.plural).plus(Pronouns.Subject.names),
            verbs = listOf(Verbs.toBe),
            objectVals = Objects.nationals,
        ),
        Lessen(
            name = "Where are you live?",
            level = Level.BEGINNER,
            tenses = listOf(Tens.PRESENT_SIMPLE),
            subjects = Pronouns.Subject.singular.plus(Pronouns.Subject.plural).plus(Pronouns.Subject.names),
            verbs = Verbs.countriesAndCities,
            prepositions = Prepositions.enclosedSpaces,
            objectVals = Objects.countries.plus(Objects.cities),
        ),
        Lessen(
            name = "She love me?",
            level = Level.BEGINNER,
            tenses = listOf(Tens.PRESENT_SIMPLE),
            subjects = Pronouns.Subject.singular,
            verbs = Verbs.feelings,
            objectVals = Pronouns.Subject.names,
        ),
        Lessen(
            name = "What is your job?",
            level = Level.BEGINNER,
            tenses = listOf(Tens.PRESENT_SIMPLE),
            subjects = Pronouns.Subject.singular.plus(Pronouns.Subject.names),
            verbs = listOf(Verbs.toBe),
            objectVals = Objects.jobs.addArticle(),
        ),
    )
}