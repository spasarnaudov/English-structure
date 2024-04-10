package com.sacoding.feature_exam.data.data_source

import com.sacoding.feature_exam.R
import com.sacoding.feature_exam.domain.Level
import com.sacoding.feature_exam.domain.Tens
import com.sacoding.feature_exam.domain.model.Exam
import com.sacoding.feature_exam.domain.model.addArticle
import com.sacoding.feature_exam.domain.model.addPossessiveAdjective
import com.sacoding.feature_exam.domain.model.plural

object StudentBook {
    object Beginner {
        val exams = listOf(
            Exam(
                name = R.string.where_are_you_from,
                level = Level.BEGINNER,
                tenses = listOf(Tens.PRESENT_SIMPLE),
                subjects = Pronouns.Subject.singular.plus(Pronouns.Subject.plural).plus(Pronouns.Subject.names),
                verbs = listOf(Verbs.toBe),
                prepositions = Prepositions.originOrSourceOfMovement,
                objectVals = Objects.countries,
            ),
            Exam(
                name = R.string.she_is_american,
                level = Level.BEGINNER,
                tenses = listOf(Tens.PRESENT_SIMPLE),
                subjects = Pronouns.Subject.singular.plus(Pronouns.Subject.plural).plus(Pronouns.Subject.names),
                verbs = listOf(Verbs.toBe),
                objectVals = Objects.nationals,
            ),
            Exam(
                name = R.string.where_do_you_live,
                level = Level.BEGINNER,
                tenses = listOf(Tens.PRESENT_SIMPLE),
                subjects = Pronouns.Subject.singular.plus(Pronouns.Subject.plural).plus(Pronouns.Subject.names),
                verbs = Verbs.countriesAndCities,
                prepositions = Prepositions.enclosedSpaces,
                objectVals = Objects.cities,
            ),
            Exam(
                name = R.string.she_love_john,
                level = Level.BEGINNER,
                tenses = listOf(Tens.PRESENT_SIMPLE),
                subjects = Pronouns.Subject.singular.plus(Pronouns.Subject.plural),
                verbs = Verbs.feelings,
                objectVals = Pronouns.Subject.names,
            ),
            Exam(
                name = R.string.what_is_your_job,
                level = Level.BEGINNER,
                tenses = listOf(Tens.PRESENT_SIMPLE),
                subjects = Pronouns.Subject.singular.plus(Pronouns.Subject.names),
                verbs = listOf(Verbs.toBe),
                objectVals = Objects.jobs.addArticle(),
            ),
            Exam(
                name = R.string.what_is_your_phone_number,
                level = Level.BEGINNER,
                tenses = listOf(Tens.PRESENT_SIMPLE),
                subjects = listOf("phone number").addPossessiveAdjective(Pronouns.PossessiveAdjective.all),
                verbs = listOf(Verbs.toBe),
                objectVals = Objects.phoneNumbers,
            ),
            Exam(
                name = R.string.what_is_your_phone_number,
                level = Level.BEGINNER,
                tenses = listOf(Tens.PRESENT_SIMPLE),
                subjects = listOf("phone number").addPossessiveAdjective(Pronouns.PossessiveAdjective.all),
                verbs = listOf(Verbs.toBe),
                objectVals = Objects.phoneNumbers,
            ),
            Exam(
                name = R.string.is_this_your,
                level = Level.BEGINNER,
                tenses = listOf(Tens.PRESENT_SIMPLE),
                subjects = Pronouns.Demonstratives.singular,
                verbs = listOf(Verbs.toBe),
                objectVals = Objects.thinks.addPossessiveAdjective(Pronouns.PossessiveAdjective.all.plus(Pronouns.PossessiveAdjective.name)),
            ),
            Exam(
                name = R.string.are_these_yours,
                level = Level.BEGINNER,
                tenses = listOf(Tens.PRESENT_SIMPLE),
                subjects = Pronouns.Demonstratives.plural,
                verbs = listOf(Verbs.toBe),
                objectVals = Objects.thinks.plural().addPossessiveAdjective(Pronouns.PossessiveAdjective.all.plus(Pronouns.PossessiveAdjective.name)),
            ),
            Exam(
                name = R.string.this_is_main,
                level = Level.BEGINNER,
                tenses = listOf(Tens.PRESENT_SIMPLE),
                subjects = Pronouns.Demonstratives.all.plus(Pronouns.Demonstratives.name),
                verbs = listOf(Verbs.toBe),
                objectVals = Pronouns.PossessivePronoun.all.plus(Pronouns.PossessivePronoun.name),
            ),
        )
    }
    object Elementary {
        val exams = listOf(
            Exam(
                name = R.string.distances,
                level = Level.ELEMENTARY,
                tenses = listOf(Tens.PRESENT_SIMPLE, Tens.PAST_SIMPLE),
                subjects = listOf(
                    "Birmingham"
                ),
                verbs = listOf(Verbs.toBe),
                prepositions = listOf(
                    "250 kilometres from",
                    "250 kilometres away from",
                ),
                objectVals = listOf(
                    "London"
                ),
            ),
            Exam(
                name = R.string.where_are_you_from,
                level = Level.ELEMENTARY,
                tenses = listOf(Tens.PRESENT_SIMPLE),
                subjects = Pronouns.Subject.singular.plus(Pronouns.Subject.plural).plus(Pronouns.Subject.names),
                verbs = listOf(Verbs.toBe),
                prepositions = Prepositions.originOrSourceOfMovement,
                objectVals = Objects.countries,
            ),
        )
    }
    object Intermediate {
        val exams = listOf(
            Exam(
                name = R.string.they_help_each_other,
                level = Level.INTERMEDIATE,
                tenses = listOf(Tens.PRESENT_SIMPLE, Tens.PAST_SIMPLE),
                subjects = Pronouns.Subject.plural,
                verbs = Verbs.mutualOrReciprocalActions,
                objectVals = Pronouns.reciprocal,
            ),
        )
    }
    object UpperIntermediate {
        val exams = listOf(
            Exam(
                name = R.string.where_are_you_from,
                level = Level.UPPER_INTERMEDIATE,
                tenses = listOf(Tens.PRESENT_SIMPLE),
                subjects = Pronouns.Subject.singular.plus(Pronouns.Subject.plural).plus(Pronouns.Subject.names),
                verbs = listOf(Verbs.toBe),
                prepositions = Prepositions.originOrSourceOfMovement,
                objectVals = Objects.countries,
            ),
        )
    }
    object Advanced {
        val exams = listOf(
            Exam(
                name = R.string.where_are_you_from,
                level = Level.ADVANCED,
                tenses = listOf(Tens.PRESENT_SIMPLE),
                subjects = Pronouns.Subject.singular.plus(Pronouns.Subject.plural).plus(Pronouns.Subject.names),
                verbs = listOf(Verbs.toBe),
                prepositions = Prepositions.originOrSourceOfMovement,
                objectVals = Objects.countries,
            ),
        )
    }
    object Proficient {
            val exams = listOf(
                Exam(
                    name = R.string.where_are_you_from,
                    level = Level.PROFICIENT,
                    tenses = listOf(Tens.PRESENT_SIMPLE),
                    subjects = Pronouns.Subject.singular.plus(Pronouns.Subject.plural).plus(Pronouns.Subject.names),
                    verbs = listOf(Verbs.toBe),
                    prepositions = Prepositions.originOrSourceOfMovement,
                    objectVals = Objects.countries,
                ),
            )
        }
}