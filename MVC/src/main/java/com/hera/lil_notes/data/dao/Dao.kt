package com.hera.lil_notes.data.dao

import androidx.room.*
import androidx.room.Dao
import com.hera.lil_notes.data.model.Model
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertModel(vararg models: Model): Completable

    @Delete
    fun deleteModel(vararg models: Model): Completable

    @Query("SELECT * FROM model_table")
    fun getAllModels(): Single<List<Model>>
}