package com.sample.anywheredemoapp.datamanagement

import androidx.lifecycle.LiveData
import com.sample.anywheredemoapp.datamanagement.database.CharacterDao
import com.sample.anywheredemoapp.models.ScreenPlayCharacter
import com.sample.anywheredemoapp.network.CharacterApiInterface
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val retrofitInterface: CharacterApiInterface, private val characterDao: CharacterDao) {

    fun getCharacters(): LiveData<List<ScreenPlayCharacter>> {
        return characterDao.getCharacters()
    }

    private fun insertCharacter(characterData: ScreenPlayCharacter) {
        characterDao.insertCharacter(characterData)
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }

    suspend fun makeApiCall() {


        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
            val characters = retrofitInterface.getData()
            if (characters.isSuccessful){
                characterDao.deleteAll()
                characters.body()?.RelatedTopics?.forEach { character ->
                    insertCharacter(character)
                }
            } else {
                Timber.e("======= API ERROR : "+characters.message())
            }
        }






    }

}