package com.ih13b175_16_tomo.st42_kadai01_memo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.ih13b175_16_tomo.st42_kadai01_memo.ui.MemoViewModel

// メモ追加画面
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    viewModel: MemoViewModel,
    onNavigateBack: () -> Unit
) {
    // 入力状態
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val isValid = title.isNotBlank() && content.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("メモを追加") },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "戻る")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // タイトル入力欄
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("タイトル") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            // 内容入力欄
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("内容") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                minLines = 5,
            )

            // 保存ボタン
            Button(
                onClick = {
                    if (isValid) {
                        viewModel.addMemo(title, content)
                        onNavigateBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = isValid,
            ) {
                Icon(Icons.Default.Check, contentDescription = "保存")
                Spacer(Modifier.width(8.dp))
                Text("保存する")
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}