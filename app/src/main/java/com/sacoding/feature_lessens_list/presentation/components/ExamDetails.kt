package com.sacoding.feature_lessens_list.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.sacoding.core.constants.FontSize
import com.sacoding.core.constants.Padding
import com.sacoding.feature_exam.R
import com.sacoding.feature_exam.domain.model.info
import com.sacoding.feature_exam.presentation.ExamViewModel

@Composable
fun ExamDetails(
    viewModel: ExamViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Padding.LARGE),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = viewModel.state.value.exam.name),
            fontWeight = FontWeight.Bold,
            fontSize = FontSize.LARGE
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Padding.MEDIUM),
            text = viewModel.state.value.exam.level.text,
            fontWeight = FontWeight.Bold,
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            viewModel.state.value.exam.info().forEachIndexed() { index, exam ->
                item {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.lessen_row_info_pattern, index + 1, exam)
                    )
                }
            }
        }
    }
}