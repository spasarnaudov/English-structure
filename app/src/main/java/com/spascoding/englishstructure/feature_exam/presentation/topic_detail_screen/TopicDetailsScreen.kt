package com.spascoding.englishstructure.feature_exam.presentation.topic_detail_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.spascoding.englishstructure.R
import com.spascoding.englishstructure.core.constants.Colors
import com.spascoding.englishstructure.feature_exam.presentation.components.BorderedListElement
import com.spascoding.englishstructure.feature_exam.presentation.components.SentenceInfoElement
import com.spascoding.englishstructure.feature_exam.presentation.utils.upperFirstLetter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamDetails(
    navController: NavController,
    viewModel: TopicDetailViewModel = hiltViewModel()
) {
    val sentences by viewModel.getSentences().collectAsState(initial = emptyList())
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                title = { /*Text("${viewModel.state.value.topic.upperFirstLetter()} (${viewModel.state.value.tense.value.upperFirstLetter()})")*/ },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_to_exam),
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = rememberLazyListState(),
        ) {
            items(sentences.count()) { i ->
                val sentence = sentences[i]
                BorderedListElement(
                    borderColor = if (sentence.value == sentence.userValue) Colors.CORRECT_ANSWER else Colors.WRONG_ANSWER
                ) {
                    SentenceInfoElement(sentence)
                }
            }
        }
    }
}