package com.ass.madhwavahini.data.local


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ass.madhwavahini.domain.modals.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.myDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "com.ass.madhwavahini.datastore"
)
private val USER_ID = intPreferencesKey("user_id")
private val USER_MOBILE = stringPreferencesKey("user_mobile")
private val USER_NAME = stringPreferencesKey("user_name")
private val USER_IS_PAID_CUSTOMER = booleanPreferencesKey("user_is_paid")
private val USER_TOKEN = stringPreferencesKey("user_login_token")

@Singleton
class UserDataStore @Inject constructor(
    @ApplicationContext context: Context
) {
    private val dataStore = context.myDataStore
    suspend fun saveUser(user: User) {
        dataStore.edit {
            it[USER_ID] = user.userId
            it[USER_MOBILE] = user.userPhone
            it[USER_NAME] = user.userName
            it[USER_IS_PAID_CUSTOMER] = user.isPaidCustomer
            it[USER_TOKEN] = user.token
        }
    }

    suspend fun logout() {
        dataStore.edit {
            it.clear()
        }
    }

    suspend fun getPhone(): String {
        val preferences: Preferences = dataStore.data.first()
        return preferences[USER_MOBILE] ?: ""
    }

    suspend fun getToken(): String? {
        val preferences: Preferences = dataStore.data.first()
        return preferences[USER_TOKEN]
    }

    suspend fun getId(): Int {
        val preferences: Preferences = dataStore.data.first()
        return preferences[USER_ID] ?: 0
    }

    suspend fun shouldGetNotifications(): Boolean {
        val preferences: Preferences = dataStore.data.first()
        return (preferences[USER_IS_PAID_CUSTOMER] ?: false)
    }

    val userData: Flow<User> = dataStore.data
        .catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
        .map { preferences ->
            val userId = preferences[USER_ID] ?: 0
            val userName = preferences[USER_NAME] ?: ""
            val userPhone = preferences[USER_MOBILE] ?: ""
            val isPaid = preferences[USER_IS_PAID_CUSTOMER] ?: false
            val token = preferences[USER_TOKEN] ?: ""
            User(
                userId = userId,
                userName = userName,
                userPhone = userPhone,
                isPaidCustomer = isPaid,
                token = token
            )
        }.distinctUntilChanged()
}