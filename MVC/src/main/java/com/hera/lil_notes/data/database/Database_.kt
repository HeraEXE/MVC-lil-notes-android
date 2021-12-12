package com.hera.lil_notes.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hera.lil_notes.data.dao.Dao
import com.hera.lil_notes.data.model.Model
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Database(entities = [Model::class], version = 1)
abstract class Database_ : RoomDatabase() {

    abstract fun getDao(): Dao
}

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDao(
        @ApplicationContext context: Context
    ): Dao {
        val db: Database_ =
            Room.databaseBuilder(context, Database_::class.java, "db").build()
        return db.getDao()
    }
}