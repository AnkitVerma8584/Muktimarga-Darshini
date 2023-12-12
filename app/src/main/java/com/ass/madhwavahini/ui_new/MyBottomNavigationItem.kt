package com.ass.madhwavahini.ui_new

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.BookOnline
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PersonSearch
import androidx.compose.material.icons.outlined.TempleHindu
import androidx.compose.ui.graphics.vector.ImageVector

data class MyBottomNavigationItem(
    val title: String,
    val route: String,
    val icon: ImageVector
)

val bottomNavigationItems = listOf(
    MyBottomNavigationItem("Home", "home", Icons.Outlined.Home),
    MyBottomNavigationItem("Category", "category", Icons.Outlined.Category),
    MyBottomNavigationItem("Aradhna", "pray", Icons.Outlined.BookOnline),
    MyBottomNavigationItem("Profile", "profile", Icons.Outlined.Person)
)
