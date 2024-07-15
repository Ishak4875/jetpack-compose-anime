package com.example.jetpackcomposeanime.core.di

import android.content.Context
import androidx.room.Room
import com.example.jetpackcomposeanime.core.data.source.local.room.AnimeDao
import com.example.jetpackcomposeanime.core.data.source.local.room.AnimeDatabase
import net.sqlcipher.database.SQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
//    @Singleton
//    val passphrase: ByteArray = SQLiteDatabase.getBytes("AnimAPI".toCharArray())
//
//    @Singleton
//    val factory = SupportFactory(passphrase)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AnimeDatabase = Room.databaseBuilder(
        context,
        AnimeDatabase::class.java, "Anime.db"
    ).fallbackToDestructiveMigration()
//        .openHelperFactory(factory)
        .build()

    @Provides
    fun provideAnimeDao(database: AnimeDatabase): AnimeDao = database.animeDao()

}