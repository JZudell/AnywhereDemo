package com.sample.anywheredemoapp.di

import android.content.Context
import com.sample.anywheredemoapp.datamanagement.database.CharacterDao
import com.sample.anywheredemoapp.datamanagement.database.CharacterDatabase
import com.sample.anywheredemoapp.network.ApiClient
import com.sample.anywheredemoapp.network.CharacterApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun getDatabase(@ApplicationContext context: Context): CharacterDatabase {
        return CharacterDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun getDao( database: CharacterDatabase): CharacterDao {
        return database.getDao()
    }

    @Singleton
    @Provides
    fun getRetrofitInterface(retrofit: Retrofit): CharacterApiInterface {
        return retrofit.create(CharacterApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitInstance(): Retrofit {
        return ApiClient().apiService
    }

}