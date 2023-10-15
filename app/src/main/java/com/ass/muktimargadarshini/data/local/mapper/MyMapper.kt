package com.ass.muktimargadarshini.data.local.mapper

import com.ass.muktimargadarshini.data.local.modals.*
import com.ass.muktimargadarshini.domain.modals.*

fun List<String>.mapToBannerList(): List<Banner> = this.map {
    Banner(it)
}

fun List<Banner>.mapToStringList(): List<String> = this.map { it.image }

fun List<HomeCategory>.mapToCategory(): List<Category> = this.map {
    Category(it.id, it.name, it.image)
}

fun List<Category>.mapToHomeCategoryList(): List<HomeCategory> = this.map { cat ->
    HomeCategory(cat.id, cat.name, cat.image)
}

fun List<HomeSubCategory>.mapToSubCategoryList(): List<SubCategory> = this.map {
    SubCategory(it.id, it.catId, it.name, it.description, it.image)
}

fun List<SubCategory>.mapToHomeSubCategoryList(): List<HomeSubCategory> = this.map { cat ->
    HomeSubCategory(cat.id, cat.catId, cat.name, cat.description, cat.image)
}

fun List<HomeSubToSubCategory>.mapToSubToSubCategoryList(): List<SubToSubCategory> = this.map {
    SubToSubCategory(it.id, it.catId, it.subCatId, it.name, it.description)
}

fun List<SubToSubCategory>.mapToHomeSubToSubCategoryList(): List<HomeSubToSubCategory> =
    this.map {
        HomeSubToSubCategory(it.id, it.catId, it.subCatId, it.name, it.description)
    }


fun List<HomeFiles>.mapToFilesList(): List<Files> = this.map {
    Files(
        it.id,
        it.catId,
        it.subCatId,
        it.subToSubCatId,
        it.name,
        it.description,
        it.fileUrl,
        it.authorId,
        it.godId
    )
}

fun List<Files>.mapToHomeFilesList(): List<HomeFiles> = this.map {
    HomeFiles(
        it.id,
        it.catId,
        it.subCatId,
        it.subToSubCatId,
        it.name,
        it.description,
        it.fileUrl,
        it.authorId,
        it.godId
    )
}

fun Files?.mapToHomeFiles(): HomeFiles? =
    this?.let {
        HomeFiles(
            id,
            catId,
            subCatId,
            subToSubCatId,
            name,
            description,
            fileUrl,
            authorId,
            godId
        )
    }

fun List<HomeAuthor>.mapToAuthor() = this.map {
    Author(it.id, it.name)
}

fun List<Author>.mapToHomeAuthor() = this.map {
    HomeAuthor(it.id, it.name)
}

fun List<HomeGod>.mapToGod() = this.map {
    God(it.id, it.godName)
}

fun List<God>.mapToHomeGod() = this.map {
    HomeGod(it.id, it.godName)
}