package com.ass.muktimargadarshini.util.locale

import android.content.Context
import com.ass.muktimargadarshini.data.local.UserDataStore
import java.util.*

object LocaleHelper {

    internal fun onAttach(context: Context): Context {
        val lang = getLanguage()
        return setLocale(context, lang)
    }

    private fun setLocale(context: Context, language: String): Context {
        UserDataStore.getInstance().saveLanguage(language)
        return updateResources(context, language)
    }

    private fun getLanguage(): String {
        return UserDataStore.getInstance().getLanguageId()
    }

    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }
}










