package com.arjun.useless

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    // Top Score DataStore
    private val scoreDataStoreManager = TopScoreManager(application)
    val getTopScore = scoreDataStoreManager.readTextFromDataStore.asLiveData()

    fun setTopScore(topScore: Int) = viewModelScope.launch {
        scoreDataStoreManager.saveTextToDataStore(topScore)
    }

    // Click Count
    private val _clickCount = MutableLiveData(0)
    val clickCount: LiveData<Int> = _clickCount

    fun setCount(count: Int) {
        _clickCount.value = count
    }

}