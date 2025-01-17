package com.spascoding.englishstructure.feature_exam.presentation.tense_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.spascoding.englishstructure.R
import com.spascoding.englishstructure.core.presentation.AboutDialog
import com.spascoding.englishstructure.feature_exam.domain.enums.Tense
import com.spascoding.englishstructure.feature_exam.domain.model.UserInfo
import com.spascoding.englishstructure.feature_exam.domain.model.getTenseInfo
import com.spascoding.englishstructure.feature_exam.presentation.Screen
import com.spascoding.englishstructure.feature_exam.presentation.components.BorderedListElement
import com.spascoding.englishstructure.feature_exam.presentation.components.ProgressInfoElement
import com.spascoding.englishstructure.feature_exam.presentation.components.TopListItem
import com.spascoding.englishstructure.feature_exam.presentation.utils.upperFirstLetter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TenseScreen(
    navController: NavController,
    viewModel: TenseScreenViewModel = hiltViewModel(),
) {
    val getUserInfoUseCase by viewModel.getUserInfoFlow().collectAsState(initial = UserInfo())
    val tenseInfoList by viewModel.getTenseInfoFlow().collectAsState(initial = emptyList())

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
                    IconButton(onClick = { showDialog.value = true }) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = stringResource(R.string.about)
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
                TopListItem(
                    stringResource(R.string.tenses),
                    "",
                    sentenceCount = getUserInfoUseCase.sentenceCount,
                    accuracy = getUserInfoUseCase.accuracy,
                )
            }
            items(Tense.entries.count()) { i ->
                val tense = Tense.entries[i]
                val tenseInfo = tenseInfoList.getTenseInfo(tense)
                BorderedListElement(
                    borderColor = tense.color,
                    onClickItem = { navController.navigate(Screen.TopicsScreen.route + "?tense=${tense.int}") }
                ) {
                    ProgressInfoElement(
                        title = tense.value.upperFirstLetter(),
                        sentenceCount = stringResource(R.string.sentences_count, tenseInfo.sentenceCount),
                        accuracy = tenseInfo.accuracy,
                        color = tense.color,
                    )
                }
            }
        }
    }
}