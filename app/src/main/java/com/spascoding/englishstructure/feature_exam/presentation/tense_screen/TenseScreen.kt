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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.spascoding.englishstructure.R
import com.spascoding.englishstructure.core.presentation.MinimalDialog
import com.spascoding.englishstructure.feature_exam.domain.enums.Tense
import com.spascoding.englishstructure.feature_exam.domain.model.getTenseInfo
import com.spascoding.englishstructure.feature_exam.presentation.Screen
import com.spascoding.englishstructure.feature_exam.presentation.components.CircularProgressListElement
import com.spascoding.englishstructure.feature_exam.presentation.components.TopListItem
import com.spascoding.englishstructure.feature_exam.presentation.utils.getAppVersion
import com.spascoding.englishstructure.feature_exam.presentation.utils.upperFirstLetter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TenseScreen(
    navController: NavController,
    viewModel: TenseScreenViewModel = hiltViewModel(),
) {
    val tenseInfoList by viewModel.getTenseInfoFlow().collectAsState(initial = emptyList())

    var accuracySum = 0f
    var usedSentenceCount = 0;
    var sentenceCountSum = 0
    Tense.entries.forEach { tense ->
        val tenseInfo = tenseInfoList.getTenseInfo(tense)
        val accuracy = tenseInfo.accuracy
        accuracySum += accuracy
        if (accuracy > 0) {
            usedSentenceCount += 1
        }
        sentenceCountSum += tenseInfo.sentenceCount
    }
    if (usedSentenceCount > 0) {
        accuracySum /= usedSentenceCount
    }

    val appVersion = stringResource(R.string.app_version, getAppVersion(LocalContext.current))
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val dialogMassage = remember { mutableStateOf("") }
    if (dialogMassage.value.isNotBlank()) {
        MinimalDialog(
            dialogMassage.value
        ) {
            dialogMassage.value = ""
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
                    IconButton(onClick = { dialogMassage.value = appVersion }) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = stringResource(R.string.app_description)
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
                    sentenceCount = sentenceCountSum,
                    accuracy = accuracySum,
                )
            }
            items(Tense.entries.count()) { i ->
                val tense = Tense.entries[i]
                val tenseInfo = tenseInfoList.getTenseInfo(tense)
                CircularProgressListElement(
                    mainText = tense.value.upperFirstLetter(),
                    progressPercentage = tenseInfo.accuracy,
                    additionalText = stringResource(R.string.sentences_count, tenseInfo.sentenceCount)
                ) {
                    navController.navigate(Screen.TopicsScreen.route + "?tense=${tense.int}")
                }
            }
        }
    }
}