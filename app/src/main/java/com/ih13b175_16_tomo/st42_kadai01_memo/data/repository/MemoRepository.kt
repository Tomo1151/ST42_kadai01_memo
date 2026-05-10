package com.ih13b175_16_tomo.st42_kadai01_memo.data.repository

import kotlinx.coroutines.flow.Flow

import com.ih13b175_16_tomo.st42_kadai01_memo.data.model.Memo
import com.ih13b175_16_tomo.st42_kadai01_memo.data.model.MemoDao

class MemoRepository(private val memoDao: MemoDao) {
    // 全件取得
    val allMemos = memoDao.getAll()

    // 追加
    fun getById(memoId: Int?): Flow<Memo?> {
        return memoDao.getById(memoId)
    }

    // 追加
    suspend fun insert(memo: Memo) {
        memoDao.insert(memo)
    }

    // 更新
    suspend fun update(memo: Memo) {
        memoDao.update(memo)
    }

    // 削除
    suspend fun delete(memo: Memo) {
        memoDao.delete(memo)
    }
}