package com.ih13b175_16_tomo.st42_kadai01_memo.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview

import com.ih13b175_16_tomo.st42_kadai01_memo.data.model.Memo
import com.ih13b175_16_tomo.st42_kadai01_memo.ui.MemoViewModel

// メモ一覧画面
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MemoViewModel,
    onNavigateToAdd: () -> Unit,
    onNavigateToDetail: (Int) -> Unit,
) {
    // メモ一覧
    val memos by viewModel.memos.collectAsState(initial = emptyList())

    // 削除確認ダイアログ
    var showDeleteDialog by remember { mutableStateOf(false) }
    var deleteMemo by remember { mutableStateOf<Memo?>(null) }

    // メモ削除ダイアログ
    if (showDeleteDialog && deleteMemo != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("メモの削除") },
            text = { Text("メモを削除しますか？") },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = Color.White
                    ),
                    onClick = {
                        deleteMemo?.let { viewModel.deleteMemo(it) }
                        showDeleteDialog = false
                        deleteMemo = null
                    }
                ) {
                    Text("削除")
                }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.White
                    ),
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("キャンセル")
                }
            }
        )
    }

    // メモ一覧ビュー
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("メモ一覧") })
        },
        floatingActionButton = {
            // メモ追加画面へのボタン
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                onClick = onNavigateToAdd
            ) {
                Icon(Icons.Default.Add, contentDescription = "追加")
            }
        }
    ) { paddingValues ->
        if (memos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                    Text(
                        text = "メモはまだありません\nΣ('◉⌓◉’)",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.LightGray,
                    )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(memos.size) { index ->
                    MemoItem(
                        memo = memos[index],
                        onClick = { onNavigateToDetail(memos[index].id) },
                        onDeleteClick = {
                            showDeleteDialog = true
                            deleteMemo = memos[index]
                        }
                    )
                }
            }
        }
    }
}

// メモカード
@Composable
fun MemoItem(
    memo: Memo,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .fillMaxWidth()
            ) {
                // タイトル
                Text(
                    text = memo.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                // 本文
                Text(
                    text = memo.content,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            // 削除ボタン
            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier
                    .size(48.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "削除",
                    tint = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}

// メモカードのプレビュー
@Preview
@Composable
fun MemoItemPreview() {
    MemoItem(
        memo = Memo(
            id = 1,
            title = "メモのタイトル",
            content = "メモの内容,メモの内容,メモの内容,メモの内容,メモの内容,メモの内容,\nメモの内容,メモの内容,メモの内容",
        ),
        onClick = { },
        onDeleteClick = { }
    )
}