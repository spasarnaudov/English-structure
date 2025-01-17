package com.spascoding.englishstructure.feature_exam.presentation.exam_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.spascoding.englishstructure.R
import com.spascoding.englishstructure.core.constants.Colors
import com.spascoding.englishstructure.core.constants.FontSize
import com.spascoding.englishstructure.core.constants.Padding
import com.spascoding.englishstructure.core.presentation.AboutDialog
import com.spascoding.englishstructure.feature_exam.domain.model.UserInfo
import com.spascoding.englishstructure.feature_exam.presentation.Screen
import com.spascoding.englishstructure.feature_exam.presentation.components.BorderedListElement
import com.spascoding.englishstructure.feature_exam.presentation.components.SentenceInfoElement
import com.spascoding.englishstructure.feature_exam.presentation.components.TopListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamScreen(
    navController: NavController,
    viewModel: ExamViewModel = hiltViewModel()
) {
    var mDisplayMenu by remember { mutableStateOf(false) }
    val getUserInfoUseCase by viewModel.getUserInfoFlow().collectAsState(initial = UserInfo())
    val recentSentences by viewModel.getRecentSentences().collectAsState(initial = emptyList())
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val showDialog = remember { mutableStateOf(false) }
    if (showDialog.value) {
        AboutDialog {
            showDialog.value = false
        }
    }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                title = {
                    Text(
                        stringResource(R.string.app_name),
                    )
                },
                actions = {
                    IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                        Icon(Icons.Default.MoreVert, "")
                    }
                    DropdownMenu(
                        expanded = mDisplayMenu,
                        onDismissRequest = { mDisplayMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.about)) },
                            onClick = {
                                showDialog.value = true
                                mDisplayMenu = !mDisplayMenu
                            },
                            trailingIcon = { Icons.Default.Info }
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = rememberLazyListState(),
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TopListItem(
                        viewModel.state.value.tense.value,
                        viewModel.state.value.topic,
                        sentenceCount = getUserInfoUseCase.sentenceCount,
                        accuracy = getUserInfoUseCase.accuracy,
                    )
                    UnderlinedText(text = viewModel.getShuffledText())
                    InputText()
                    ButtonsLayout(navController)
                }
            }
            items(recentSentences.count()) { i ->
                val sentence = recentSentences[i]
                BorderedListElement(
                    borderColor = if (sentence.value == sentence.userValue) Colors.CORRECT_ANSWER else Colors.WRONG_ANSWER
                ) {
                    SentenceInfoElement(sentence)
                }
            }
        }
    }
}

@Composable
fun UnderlinedText(text: AnnotatedString) {
    val onBackgroundColor = MaterialTheme.colorScheme.onBackground
    Text(
        modifier = Modifier
            .wrapContentSize()
            .padding(Padding.MEDIUM)
            .drawBehind {
                val strokeWidthPx = 2.dp.toPx()
                val verticalOffset = size.height
                drawLine(
                    color = onBackgroundColor,
                    strokeWidth = strokeWidthPx,
                    start = Offset(0f, verticalOffset),
                    end = Offset(size.width, verticalOffset)
                )
            },
        fontWeight = FontWeight.Bold,
        text = text,
    )
}

@Composable
fun InputText(
    viewModel: ExamViewModel = hiltViewModel()
) {
    val focusRequester = remember { FocusRequester() }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Padding.MEDIUM)
            .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Go,
            capitalization = KeyboardCapitalization.Sentences,
        ),
        keyboardActions = KeyboardActions(
            onGo = {
                viewModel.onEvent(ExamEvent.CheckExam(viewModel.state.value.userSentence))
            }
        ),
        value = viewModel.state.value.userSentence,
        onValueChange = {
            viewModel.onEvent(ExamEvent.EnterText(it))
        },
        label = { Text(stringResource(R.string.put_the_sentence_in_the_correct_order)) },
    )
    //TODO - Implement logic that enable/disable alto select input field
//    LaunchedEffect(Unit) {
//        focusRequester.requestFocus()
//    }
}

@Composable
fun ButtonsLayout(
    navController: NavController,
    viewModel: ExamViewModel = hiltViewModel()
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Padding.MEDIUM),
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = stringResource(R.string.recent),
            fontWeight = FontWeight.Bold,
            fontSize = FontSize.LARGE
        )
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    viewModel.onEvent(ExamEvent.CheckExam(viewModel.state.value.userSentence))
                },
            ) {
                Text(text = stringResource(R.string.check))
            }
        }
        OutlinedButton(
            onClick = {
                navController.navigate(Screen.TopicDetailScreen.route + "?tense=${viewModel.state.value.tense.int}&topic=${viewModel.state.value.topic}")
            }
        ) {
            Text(text = stringResource(R.string.all))
        }
    }
}