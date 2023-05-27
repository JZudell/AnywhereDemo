package com.sample.anywheredemoapp.datamanagement.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sample.anywheredemoapp.models.CharacterIconConverter
import com.sample.anywheredemoapp.models.ScreenPlayCharacter

@Database(
    entities = [ScreenPlayCharacter::class],
    version = 1
)
@TypeConverters(CharacterIconConverter::class)
abstract class CharacterDatabase: RoomDatabase() {

    abstract fun getDao(): CharacterDao

    companion object {
        private var INSTANCE: CharacterDatabase? = null

        fun getInstance(context: Context): CharacterDatabase {
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    CharacterDatabase::class.java,
                    "characters.db"
                ).build()

            }

            return INSTANCE!!

        }

    }

}