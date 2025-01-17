package com.spascoding.englishstructure.feature_exam.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.spascoding.englishstructure.R
import com.spascoding.englishstructure.core.constants.FontSize
import com.spascoding.englishstructure.core.constants.Padding

@Composable
fun TopListItem(
    tense: String,
    topic: String,
    sentenceCount: Int,
    accuracy: Float,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Padding.MEDIUM),
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = tense,
                fontWeight = FontWeight.Bold,
            )
            Text(text = topic)
            Text(text = stringResource(R.string.sentences_count, sentenceCount))
        }
        CircularProgressBar(
            modifier = Modifier.padding(Padding.SMALL),
            percentage = accuracy,
            fontSize = FontSize.EXTRA_LARGE,
            radius = 72.dp,
            strokeWidth = 8.dp,
            description = stringResource(R.string.accuracy)
        )
    }
}