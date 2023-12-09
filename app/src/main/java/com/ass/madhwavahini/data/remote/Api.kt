package com.ass.madhwavahini.data.remote

object Api {

    const val BASE_URL = "https://srssvvt.in/madhvavahini/"
    const val TRANSLATION_BASE_URL = "https://aksharamukha-plugin.appspot.com/"

    private const val API = "app/apis"
    const val TRANSLATE = "api/public"

    fun String.getDocumentExtension(): String = "uploads/documents/$this"
    fun String?.getAudioUrl(): String {
        return if (this.isNullOrBlank()) ""
        else BASE_URL + "uploads/audio/$this"
    }

    const val LOGIN = "$API/login.php"
    const val USER = "$API/user.php"
    const val PAYMENT = "$API/order.php"
    const val GET_BANNER = "$API/banner.php"
    const val GET_CATEGORY = "$API/category.php"
    const val GET_SUBCATEGORIES = "$API/sub_category.php"
    const val GET_SUB_TO_SUBCATEGORIES = "$API/sub_to_sub_category.php"
    const val GET_FILES = "$API/files.php"
}