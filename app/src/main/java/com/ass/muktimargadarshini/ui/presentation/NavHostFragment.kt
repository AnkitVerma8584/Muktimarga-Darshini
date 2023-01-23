package com.ass.muktimargadarshini.ui.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ass.muktimargadarshini.domain.utils.StringUtil
import com.ass.muktimargadarshini.ui.presentation.navigation.modal.NavigationFragment
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.about.AboutPage
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.CategoryPage
import com.ass.muktimargadarshini.presentation.ui.navigation.screens.contact.ContactPage
import com.ass.muktimargadarshini.presentation.ui.navigation.screens.file_details.FileDetailsPage
import com.ass.muktimargadarshini.presentation.ui.navigation.screens.files.FilePage
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.pdf.PdfScreen
import com.ass.muktimargadarshini.presentation.ui.navigation.screens.sub_category.SubCategoryPage
import com.ass.muktimargadarshini.presentation.ui.navigation.screens.sub_to_sub_category.SubToSubCategoryPage
import com.ass.muktimargadarshini.presentation.ui.navigation.screens.support.SupportPage

@Composable
fun NavHostFragments(
    navController: NavHostController, paddingValues: PaddingValues
) {
    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        navController = navController,
        startDestination = NavigationFragment.Home.route
    ) {
        composable(route = NavigationFragment.Home.route) {
            CategoryPage {
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
                NavigationFragment.SubToSubCategory.title = StringUtil.DynamicText(it.name)
                navController.navigate("sub_to_sub_category/${it.cat_id}/${it.id}") {
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
            SubToSubCategoryPage(onSubToSubCategoryClick = {
                NavigationFragment.Files.title = StringUtil.DynamicText(it.name)
                navController.navigate("files/${it.cat_id}/${it.sub_cat_id}/${it.id}") {
                    launchSingleTop = true
                    restoreState = true
                }
            }, onFileClicked = { item, query, index ->
                NavigationFragment.FileDetails.title = StringUtil.DynamicText(item.name)
                navController.navigate("file_details?file_id=$id&file_name=${item.name}&file_url=${item.file_url}&query=$query&index=$index") {
                    launchSingleTop = true
                    restoreState = true
                }
            }, onPdfClicked = {
                NavigationFragment.Pdf.title = StringUtil.DynamicText(it.name)
                navController.navigate("pdf?url=${it.file_url}") {
                    launchSingleTop = true
                    restoreState = true
                }
            })
        }
        composable(
            route = NavigationFragment.Files.route,
            arguments = listOf(navArgument("cat_id") { type = NavType.IntType },
                navArgument("sub_cat_id") { type = NavType.IntType },
                navArgument("sub_to_sub_cat_id") { type = NavType.IntType })
        ) {
            FilePage(onFileClicked = { item, query, index ->
                NavigationFragment.FileDetails.title = StringUtil.DynamicText(item.name)
                navController.navigate("file_details?file_id=$id&file_name=${item.name}&file_url=${item.file_url}&query=$query&index=$index") {
                    launchSingleTop = true
                    restoreState = true
                }
            }, onPdfClicked = {
                NavigationFragment.Pdf.title = StringUtil.DynamicText(it.name)
                navController.navigate("pdf?url=${it.file_url}") {
                    launchSingleTop = true
                    restoreState = true
                }
            })
        }
        composable(
            route = NavigationFragment.FileDetails.route,
            arguments = listOf(navArgument("file_id") { type = NavType.IntType },
                navArgument("query") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("index") {
                    type = NavType.IntType
                    defaultValue = -1
                })
        ) {
            FileDetailsPage()
        }
        composable(
            route = NavigationFragment.Pdf.route,
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) {
            PdfScreen()
        }
    }
}