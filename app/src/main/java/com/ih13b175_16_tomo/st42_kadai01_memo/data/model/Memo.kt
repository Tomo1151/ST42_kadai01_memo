package com.ih13b175_16_tomo.st42_kadai01_memo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memos")
data class Memo (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val isDone: Boolean = false,
)