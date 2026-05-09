package com.ih13b175_16_tomo.st42_kadai01_memo.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ih13b175_16_tomo.st42_kadai01_memo.ui.MemoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    memoId: Int?,
    viewModel: MemoViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToEdit: (Int) -> Unit,
) {
    // 当該メモを取得
    val memo = viewModel.getMemoById(memoId).collectAsState(initial = null).value

    if (memo != null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("メモの詳細") },
                    navigationIcon = {
                        IconButton(onClick = { onNavigateBack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "戻る")
                        }
                    },
                    actions = {
                        // 編集画面
                        IconButton(onClick = {
                            onNavigateToEdit(memo.id)
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "編集")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // タイトル
                Text(
                    text = memo.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                // 本文
                Text(
                    text = memo.content,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 24.sp,
                )
            }
        }
    }
}