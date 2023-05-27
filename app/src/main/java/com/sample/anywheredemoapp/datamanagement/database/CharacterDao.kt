package com.sample.anywheredemoapp.datamanagement.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sample.anywheredemoapp.models.ScreenPlayCharacter

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characterTable")
    fun getCharacters(): LiveData<List<ScreenPlayCharacter>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(characterData: ScreenPlayCharacter)

    @Query("DELETE FROM characterTable")
    fun deleteAll()



}