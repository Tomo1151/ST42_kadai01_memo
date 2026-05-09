package com.ih13b175_16_tomo.st42_kadai01_memo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.ih13b175_16_tomo.st42_kadai01_memo.ui.MemoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    memoId: Int?,
    viewModel: MemoViewModel,
    onNavigateBack: () -> Unit,
) {
    // 指定されたIDをViewModelにセットしてデータを監視
    LaunchedEffect(memoId) {
        memoId?.let { viewModel.setMemoId(it) }
    }

    val memo = viewModel.getMemoById(memoId).collectAsState(initial = null)

    // 入力データ
    var title by rememberSaveable { mutableStateOf("") }
    var content by rememberSaveable { mutableStateOf("") }

    // データの初期ロード
    LaunchedEffect(memo.value) {
        memo.value?.let {
            if (title.isEmpty() && content.isEmpty()) {
                title = it.title
                content = it.content
            }
        }
    }

    val isValid = title.isNotBlank() && content.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("メモを編集") },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "戻る")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // タイトル
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("タイトル") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )

            // 本文
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("内容") },
                minLines = 5,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )

            Button(
                onClick = {
                    if (isValid) {
                        val updated = memo.value?.copy(title = title, content = content)
                        if (updated != null) {
                            viewModel.updateMemo(updated)
                            onNavigateBack()
                        }
                    }
                },
                enabled = isValid,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(Icons.Default.Check, contentDescription = "更新内容を保存")
                Spacer(Modifier.width(8.dp))
                Text("変更を保存")
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}