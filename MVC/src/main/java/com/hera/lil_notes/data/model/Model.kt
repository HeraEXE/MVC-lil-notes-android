package com.hera.lil_notes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "model_table")
data class Model(
    val name: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null
)
