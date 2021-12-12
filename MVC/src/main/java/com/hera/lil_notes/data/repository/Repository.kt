package com.hera.lil_notes.data.repository

import com.hera.lil_notes.data.dao.Dao
import com.hera.lil_notes.data.model.Model
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

interface Repository {

    fun addModel(model: Model): Completable

    fun deleteModel(model: Model): Completable

    fun getAllModels(): Single<List<Model>>
}

class RepositoryImpl @Inject constructor(private val dao: Dao) : Repository {

    override fun addModel(model: Model): Completable {
        return dao.insertModel(model)
    }

    override fun deleteModel(model: Model): Completable {
        return dao.deleteModel(model)
    }

    override fun getAllModels(): Single<List<Model>> {
        return dao.getAllModels()
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindRepository(impl: RepositoryImpl): Repository
}