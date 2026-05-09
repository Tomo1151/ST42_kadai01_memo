package com.ih13b175_16_tomo.st42_kadai01_memo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ih13b175_16_tomo.st42_kadai01_memo.data.model.Memo
import com.ih13b175_16_tomo.st42_kadai01_memo.data.model.MemoDao

@Database(
    // このDBが管理するテーブル一覧
    entities = [Memo::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    // DAOの取得
    abstract fun memoDao(): MemoDao

    // シングルトンオブジェクト
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "memo_database",
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}