package com.ass.madhwavahini.ui.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ass.madhwavahini.domain.modals.FileType
import com.ass.madhwavahini.domain.modals.HomeFile
import com.ass.madhwavahini.domain.utils.StringUtil
import com.ass.madhwavahini.ui.presentation.navigation.modal.NavigationFragment
import com.ass.madhwavahini.ui.presentation.navigation.screens.about.AboutPage
import com.ass.madhwavahini.ui.presentation.navigation.screens.category.CategoryPage
import com.ass.madhwavahini.ui.presentation.navigation.screens.contact.ContactPage
import com.ass.madhwavahini.ui.presentation.navigation.screens.file_details.FileDetailsPage
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.FilePage
import com.ass.madhwavahini.ui.presentation.navigation.screens.music.MusicScreen
import com.ass.madhwavahini.ui.presentation.navigation.screens.pdf.PdfScreen
import com.ass.madhwavahini.ui.presentation.navigation.screens.sub_category.SubCategoryPage
import com.ass.madhwavahini.ui.presentation.navigation.screens.sub_to_sub_category.SubToSubCategoryPage
import com.ass.madhwavahini.ui.presentation.navigation.screens.support.SupportPage

@Composable
fun NavHostFragments(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    isPaidCustomer: Boolean,
    onNavigationTriggered: () -> Unit
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = NavigationFragment.Home.route
    ) {
        composable(route = NavigationFragment.Home.route) {
            CategoryPage(windowSizeClass) {
                if (!isPaidCustomer) {
                    onNavigationTriggered.invoke()
                    return@CategoryPage
                }
                NavigationFragment.SubCategory.title = StringUtil.DynamicText(it.name)
                navController.navigate("sub_category/${it.id}") {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
        composable(route = NavigationFragment.About.route) {
            AboutPage()
        }
        composable(route = NavigationFragment.Contact.route) {
            ContactPage()
        }
        composable(route = NavigationFragment.Support.route) {
            SupportPage()
        }
        composable(
            route = NavigationFragment.SubCategory.route,
            arguments = listOf(navArgument("cat_id") { type = NavType.IntType })
        ) {
            SubCategoryPage(onSubCategoryClicked = {
                if (!isPaidCustomer) {
                    onNavigationTriggered.invoke()
                    return@SubCategoryPage
                }
                NavigationFragment.SubToSubCategory.title = StringUtil.DynamicText(it.name)
                navController.navigate("sub_to_sub_category/${it.catId}/${it.id}") {
                    launchSingleTop = true
                    restoreState = true
                }
            })
        }
        composable(
            route = NavigationFragment.SubToSubCategory.route,
            arguments = listOf(navArgument("cat_id") { type = NavType.IntType },
                navArgument("sub_cat_id") { type = NavType.IntType })
        ) {
            SubToSubCategoryPage(onSubToSubCategoryClick = { subToSubCat ->
                if (!isPaidCustomer) {
                    onNavigationTriggered.invoke()
                    return@SubToSubCategoryPage
                }

                NavigationFragment.Files.title = StringUtil.DynamicText(subToSubCat.name)

                navController.navigate("files/${subToSubCat.catId}/${subToSubCat.subCatId}/${subToSubCat.id}") {
                    launchSingleTop = true
                    restoreState = true
                }
            }, onFileClicked = { homeFile, query, index ->
                if (!isPaidCustomer) {
                    onNavigationTriggered.invoke()
                    return@SubToSubCategoryPage
                }

                navController.onFileClicked(homeFile, query, index)

            })
        }
        composable(
            route = NavigationFragment.Files.route,
            arguments = listOf(navArgument("cat_id") { type = NavType.IntType },
                navArgument("sub_cat_id") { type = NavType.IntType },
                navArgument("sub_to_sub_cat_id") { type = NavType.IntType })
        ) {
            FilePage(onFileClicked = { homeFile, query, index ->
                if (!isPaidCustomer) {
                    onNavigationTriggered.invoke()
                    return@FilePage
                }
                navController.onFileClicked(homeFile, query, index)
            })
        }
        composable(
            route = NavigationFragment.FileDetails.route,
            arguments = listOf(navArgument("file_id") {
                type = NavType.IntType
            }, navArgument("query") {
                type = NavType.StringType
                defaultValue = ""
            }, navArgument("index") {
                type = NavType.IntType
                defaultValue = -1
            })
        ) {
            FileDetailsPage()
        }
        composable(
            route = NavigationFragment.Pdf.route,
            arguments = listOf(navArgument("file_id") { type = NavType.IntType },
                navArgument("file_url") { type = NavType.StringType })
        ) {
            PdfScreen()
        }
        composable(
            route = NavigationFragment.Music.route,
            arguments = listOf(navArgument("file_id") { type = NavType.IntType },
                navArgument("file_name") { type = NavType.StringType },
                navArgument("file_author") { type = NavType.StringType },
                navArgument("file_url") { type = NavType.StringType })
        ) {
            MusicScreen(
                windowSizeClass = windowSizeClass
            )
        }
    }
}

private fun NavController.onFileClicked(homeFile: HomeFile, query: String, index: Int) {
    when (homeFile.type) {
        FileType.TYPE_TEXT -> {
            NavigationFragment.FileDetails.title = StringUtil.DynamicText(homeFile.name)
            navigate("file_details?file_id=${homeFile.id}&file_url=${homeFile.fileUrl}&query=$query&index=$index") {
                launchSingleTop = true
                restoreState = true
            }
        }


        FileType.TYPE_PDF -> {
            NavigationFragment.Pdf.title = StringUtil.DynamicText(homeFile.name)
            navigate("pdf?file_id=${homeFile.id}&file_url=${homeFile.fileUrl}") {
                launchSingleTop = true
                restoreState = true
            }
        }

        FileType.TYPE_AUDIO -> {
            NavigationFragment.Music.title = StringUtil.DynamicText(homeFile.name)
            navigate("music?file_id=${homeFile.id}&file_name=${homeFile.name}&file_author=${homeFile.authorName}&file_url=${homeFile.fileUrl}") {
                launchSingleTop = true
                restoreState = true
            }
        }

        FileType.TYPE_UNKNOWN -> {}
    }
}