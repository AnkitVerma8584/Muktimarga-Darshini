package com.ass.madhwavahini.domain.modals.filter

import com.ass.madhwavahini.domain.modals.HomeAuthor
import com.ass.madhwavahini.domain.modals.HomeGod

data class FileFilter(
    val selectedAuthors: List<HomeAuthor>,
    val selectedGods: List<HomeGod>,
) {
    val selectedAuthorIds get() = selectedAuthors.filter { it.idSelected }.map { it.id }.toSet()
    val selectedGodIds get() = selectedGods.filter { it.idSelected }.map { it.id }.toSet()
}


