package com.sacoding.feature_lessens_list.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.sacoding.core.constants.Padding
import com.sacoding.core.presentation.SegmentedButtonItem
import com.sacoding.core.presentation.SingleChoiceSegmentedButton
import com.sacoding.feature_exam.domain.ExamEvent
import com.sacoding.feature_exam.domain.Level
import com.sacoding.feature_exam.domain.toSegmentedItem
import com.sacoding.feature_exam.presentation.ExamViewModel
import com.sacoding.feature_exam.presentation.Screen
import kotlinx.coroutines.launch

@Composable
fun ExamsList(
    navController: NavController,
    viewModel: ExamViewModel,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val levels = listOf(
            SegmentedButtonItem(Level.BEGINNER.code, Level.BEGINNER),
            SegmentedButtonItem(Level.ELEMENTARY.code, Level.ELEMENTARY),
//            SegmentedButtonItem(Level.PRE_ELEMENTARY.code, Level.PRE_ELEMENTARY),
            SegmentedButtonItem(Level.INTERMEDIATE.code, Level.INTERMEDIATE),
//            SegmentedButtonItem(Level.UPPER_INTERMEDIATE.code, Level.UPPER_INTERMEDIATE),
//            SegmentedButtonItem(Level.ADVANCED.code, Level.ADVANCED),
//            SegmentedButtonItem(Level.PROFICIENT.code, Level.PROFICIENT),
        )
        SingleChoiceSegmentedButton(
            modifier = Modifier.padding(
                horizontal = Padding.SMALL,
                vertical = Padding.MEDIUM
            ),
            items = levels,
            selectedItem = toSegmentedItem(viewModel.state.value.level)
        ) { segmentedButtonItem ->
            viewModel.onEvent(ExamEvent.SelectLevel(segmentedButtonItem.item))
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            viewModel.viewModelScope.launch {
                viewModel.getExams().forEach() { exam ->
                    item {
                        ExamElement(
                            onClickItem = {
                                viewModel.onEvent(ExamEvent.SelectExam(exam))
                                navController.navigate(Screen.ExamScreen.route)
                            },
                            onClickInfo = {
                                viewModel.onEvent(ExamEvent.SelectExam(exam))
                                navController.navigate(Screen.ExamDetail.route)
                            },
                            examName = exam.name,
                            levelName = exam.level.text,
                        )
                    }
                }
            }

        }
    }
}