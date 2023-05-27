package com.sample.anywheredemoapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Entity(tableName = "characterTable")
data class ScreenPlayCharacter(
    val FirstURL: String? = null,
    val Icon: CharacterIcon? = null,
    val Result: String? = null,
    @PrimaryKey
    val Text: String = ""
){
    fun getName(): String {
        return this.Text.split(" - ").first()
    }
    fun getDescription(): String {
        return this.Text.split(" - ").last()
    }
}

@Entity
data class CharacterIcon(
    val Height: String? = null,
    val URL: String? = null,
    val Width: String? = null
)




class CharacterIconConverter {
    val gson: Gson = Gson()
    @TypeConverter
    fun stringToCharacterIcon(string:String?) : CharacterIcon? {
        if (string == null) return null
        val type: Type = object : TypeToken<CharacterIcon?>() {}.type
        return gson.fromJson<CharacterIcon?>(string, type)
    }

    @TypeConverter
    fun characterIconToString(characterIcon: CharacterIcon?): String? {
        return gson.toJson(characterIcon)
    }
}
