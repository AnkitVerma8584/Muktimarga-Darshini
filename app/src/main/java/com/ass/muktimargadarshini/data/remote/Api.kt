package com.ass.muktimargadarshini.data.remote

object Api {

    const val BASE_URL = "https://srssvvt.in/madhvavahini/"

    fun String.getDocumentExtension(): String = "uploads/documents/$this"

    //fun String.getDocumentUrl(): String = BASE_URL + this.getDocumentExtension()

    const val GET_BANNER = "app/banner.php"
    const val GET_CATEGORY = "app/category.php"
    const val GET_SUBCATEGORIES = "app/sub_category.php"
    const val GET_SUB_TO_SUBCATEGORIES = "app/sub_to_sub_category.php"
    const val GET_FILES = "app/files.php"
    const val GET_AUTHOR = "app/author.php"
    const val GET_GODS = "app/god.php"

}