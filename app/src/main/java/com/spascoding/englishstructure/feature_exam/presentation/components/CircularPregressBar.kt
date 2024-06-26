package com.spascoding.englishstructure.feature_exam.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.spascoding.englishstructure.core.constants.FontSize

@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier,
    percentage: Float,
    fontSize: TextUnit = FontSize.MEDIUM,
    description: String = "",
    fontSizeDescription: TextUnit = FontSize.MEDIUM,
    radius: Dp = 24.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 4.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0,
) {
    Box(
        modifier = modifier
    ) {
        var animationPlayed by remember {
            mutableStateOf(false)
        }
        val curPercentage = animateFloatAsState(
            targetValue = if (animationPlayed) percentage else 0f,
            animationSpec = tween(
                durationMillis = animDuration,
                delayMillis = animDelay
            ),
            label = ""
        )
        LaunchedEffect(key1 = true) {
            animationPlayed = true
        }
        Box(
            modifier = Modifier.size(radius * 1.7f), // TODO 1.7 is not good option
            contentAlignment = Alignment.Center,
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawArc(
                    color = color.copy(alpha = 0.2f),
                    startAngle = -90f,
                    sweepAngle = 360 * 1f,
                    useCenter = false,
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
                drawArc(
                    color = color,
                    startAngle = -90f,
                    sweepAngle = 360 * (if (curPercentage.value == 0f) 0.001f else curPercentage.value),
                    useCenter = false,
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${(curPercentage.value * 100).toInt()}%",
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold
                )
                if (description.isNotBlank()) {
                    Text(
                        text = description,
                        fontSize = fontSizeDescription,
                    )
                }
            }
        }
    }
}