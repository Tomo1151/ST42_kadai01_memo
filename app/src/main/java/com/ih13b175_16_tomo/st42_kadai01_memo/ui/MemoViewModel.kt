package com.ih13b175_16_tomo.st42_kadai01_memo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ih13b175_16_tomo.st42_kadai01_memo.data.model.Memo
import com.ih13b175_16_tomo.st42_kadai01_memo.data.repository.MemoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// Viewからメモ操作を行うためのViewModel
class MemoViewModel(private val repository: MemoRepository): ViewModel() {
    // メモの状態
    val memos: Flow<List<Memo>> = repository.allMemos

    // ID指定取得
    fun getMemoById(memoId: Int?): Flow<Memo?> {
        return repository.getById(memoId)
    }

    // 追加
    fun addMemo(title: String, content: String) {
        viewModelScope.launch {
            val newMemo = Memo(title = title, content = content)
            repository.insert(newMemo)
        }
    }

    // 更新
    fun updateMemo(memo: Memo) {
        viewModelScope.launch {
            repository.update(memo)
        }
    }

    // 削除
    fun deleteMemo(memo: Memo) {
        viewModelScope.launch {
            repository.delete(memo)
        }
    }
}