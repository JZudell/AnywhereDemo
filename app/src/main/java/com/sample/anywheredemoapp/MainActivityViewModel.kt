package com.sample.anywheredemoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.anywheredemoapp.datamanagement.CharacterRepository
import com.sample.anywheredemoapp.models.ScreenPlayCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: CharacterRepository) : ViewModel() {

    fun getCharacters(): LiveData<List<ScreenPlayCharacter>> {
        return repository.getCharacters()
    }

    fun makeApiCall() {
        viewModelScope.launch {
            repository.makeApiCall()
        }
    }









}