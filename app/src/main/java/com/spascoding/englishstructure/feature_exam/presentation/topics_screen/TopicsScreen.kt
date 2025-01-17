package com.spascoding.englishstructure.feature_exam.presentation.topics_screen

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
import com.spascoding.englishstructure.feature_exam.domain.model.getTenseInfo
import com.spascoding.englishstructure.feature_exam.presentation.Screen
import com.spascoding.englishstructure.feature_exam.presentation.components.BorderedListElement
import com.spascoding.englishstructure.feature_exam.presentation.components.ProgressInfoElement
import com.spascoding.englishstructure.feature_exam.presentation.components.TopListItem
import com.spascoding.englishstructure.feature_exam.presentation.utils.upperFirstLetter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicsScreen(
    navController: NavController,
    viewModel: TopicsViewModel = hiltViewModel(),
) {
    val tenseInfoList by viewModel.getTenseInfoFlow().collectAsState(initial = emptyList())
    val tenseInfo = tenseInfoList.getTenseInfo(viewModel.state.value.tense)
    val topicInfoList by viewModel.getTopicInfoFlow().collectAsState(initial = emptyList())
    val topics by viewModel.getTopics().collectAsState(initial = emptyList())
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                title = { Text(viewModel.state.value.tense.value.upperFirstLetter()) },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_to_tenses_list),
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
            item {
                TopListItem(
                    stringResource(R.string.topics),
                    "",
                    sentenceCount = tenseInfo.sentenceCount,
                    accuracy = tenseInfo.accuracy,
                )
            }
            items(topics.count()) { i ->
                val topic = topics[i]
                val topicInfo = topicInfoList.getTenseInfo(topic)
                BorderedListElement(
                    borderColor = viewModel.state.value.tense.color,
                    onClickItem = { navController.navigate(Screen.ExamScreen.route + "?tense=${viewModel.state.value.tense.int}&topic=$topic") }
                ) {
                    ProgressInfoElement(
                        title = topic.upperFirstLetter(),
                        sentenceCount = stringResource(R.string.sentences_count, topicInfo.sentenceCount),
                        accuracy = topicInfo.accuracy,
                        color = viewModel.state.value.tense.color,
                    )
                }
            }
        }
    }
}