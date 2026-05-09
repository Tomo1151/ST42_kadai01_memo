package com.ih13b175_16_tomo.st42_kadai01_memo.data.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {
    // 全件取得
    @Query("SELECT * FROM memos ORDER BY id DESC")
    fun getAll(): Flow<List<Memo>>

    // ID指定取得
    @Query("SELECT * FROM memos WHERE id = :memoId")
    fun getById(memoId: Int?): Flow<Memo?>

    // 追加
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(memo: Memo)

    // 更新
    @Update
    suspend fun update(memo: Memo)

    // 削除
    @Delete
    suspend fun delete(memo: Memo)
}