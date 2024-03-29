package com.ass.madhwavahini.data.local.mapper

import com.ass.madhwavahini.data.local.modals.Banner
import com.ass.madhwavahini.data.local.modals.Category
import com.ass.madhwavahini.data.local.modals.Files
import com.ass.madhwavahini.data.local.modals.SubCategory
import com.ass.madhwavahini.data.local.modals.SubToSubCategory
import com.ass.madhwavahini.domain.modals.HomeCategory
import com.ass.madhwavahini.domain.modals.HomeFile
import com.ass.madhwavahini.domain.modals.HomeSubCategory
import com.ass.madhwavahini.domain.modals.HomeSubToSubCategory

fun List<String>.mapToBannerList(): List<Banner> = this.map { Banner(it) }

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

fun List<SubToSubCategory>.mapToHomeSubToSubCategoryList(): List<HomeSubToSubCategory> = this.map {
    HomeSubToSubCategory(it.id, it.catId, it.subCatId, it.name, it.description)
}


fun List<HomeFile>.mapToFilesList(): List<Files> = this.map {
    Files(
        it.id,
        it.catId,
        it.subCatId,
        it.subToSubCatId,
        it.name,
        it.description,
        it.fileUrl,
        it.audioUrl,
        it.audioImage,
        it.authorId,
        it.godId,
        it.authorName,
        it.godName
    )
}

fun List<Files>.mapToHomeFilesList(): List<HomeFile> = this.map {
    HomeFile(
        it.id,
        it.catId,
        it.subCatId,
        it.subToSubCatId,
        it.name,
        it.description,
        it.fileUrl,
        it.audioUrl,
        it.audioImage,
        it.authorId,
        it.godId,
        it.authorName,
        it.godName
    )
}