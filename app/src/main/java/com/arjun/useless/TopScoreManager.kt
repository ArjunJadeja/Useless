package com.arjun.useless

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(
    name = "top_score"
)

class TopScoreManager(context: Context) {

    private var appContext = context.applicationContext

    private object PreferenceKeys {
        val name = intPreferencesKey("score_key")
    }

    suspend fun saveTextToDataStore(topScore: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[PreferenceKeys.name] = topScore
        }
    }

    val readTextFromDataStore: Flow<Int> = appContext.dataStore.data.map { preferences ->
        preferences[PreferenceKeys.name] ?: 0
    }
}