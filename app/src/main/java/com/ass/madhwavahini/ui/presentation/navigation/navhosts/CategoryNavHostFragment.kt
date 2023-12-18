package com.ass.madhwavahini.ui.presentation.navigation.navhosts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.modals.FileType
import com.ass.madhwavahini.domain.modals.HomeFile
import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.domain.wrapper.StringUtil
import com.ass.madhwavahini.ui.presentation.common.SnackBarType
import com.ass.madhwavahini.ui.presentation.main.components.MyTopAppBar
import com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.category.CategoryPage
import com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.document.pdf.PdfScreen
import com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.document.text.TextDocumentScreen
import com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.files.FilePage
import com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.sub_category.SubCategoryPage
import com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.sub_to_sub_category.SubToSubCategoryPage
import com.ass.madhwavahini.ui.presentation.navigation.modal.CategoryNavigationFragments
import com.ass.madhwavahini.ui.presentation.navigation.modal.categoryFragmentsList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryNavHostFragment(
    user: User,
    onBottomBarStateChange: (state: Boolean) -> Unit,
    onErrorTriggered: (message: String, type: SnackBarType) -> Unit,
    onNavigateBack: () -> Unit
) {

    val categoryNavController = rememberNavController()
    val navBackStackEntry by categoryNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val currentFragment by remember(currentDestination) {
        derivedStateOf {
            categoryFragmentsList.find { it.route == currentDestination?.route }
        }
    }

    val shouldShowBottomBar by remember {
        derivedStateOf {
            navBackStackEntry?.destination?.route !in arrayOf(
                CategoryNavigationFragments.Pdf.route,
                CategoryNavigationFragments.FileDetails.route
            )
        }
    }
    LaunchedEffect(key1 = shouldShowBottomBar) {
        onBottomBarStateChange(shouldShowBottomBar)
    }
    Scaffold(
        topBar = {
            MyTopAppBar(
                title = currentFragment?.title?.asString()
                    ?: stringResource(id = R.string.category)
            ) {
                if (CategoryNavigationFragments.Category.route == currentFragment?.route)
                    onNavigateBack.invoke()
                else
                    categoryNavController.navigateUp()
            }
        }
    ) { padding ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            navController = categoryNavController,
            startDestination = CategoryNavigationFragments.Category.route
        ) {
            composable(route = CategoryNavigationFragments.Category.route) {
                CategoryPage {
                    CategoryNavigationFragments.SubCategory.title = StringUtil.DynamicText(it.name)
                    categoryNavController.navigate("sub_category/${it.id}") {
                        popUpTo(categoryNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
            composable(
                route = CategoryNavigationFragments.SubCategory.route,
                arguments = listOf(navArgument("cat_id") { type = NavType.IntType })
            ) {
                SubCategoryPage(
                    onSubCategoryClicked = {
                        CategoryNavigationFragments.SubToSubCategory.title =
                            StringUtil.DynamicText(it.name)
                        categoryNavController.navigate("sub_to_sub_category/${it.catId}/${it.id}") {
                            launchSingleTop = true
                            restoreState = true
                        }
                    })
            }
            composable(
                route = CategoryNavigationFragments.SubToSubCategory.route,
                arguments = listOf(navArgument("cat_id") { type = NavType.IntType },
                    navArgument("sub_cat_id") { type = NavType.IntType })
            ) {
                SubToSubCategoryPage(
                    onSubToSubCategoryClick = { subToSubCat ->
                        if (!user.isPaidCustomer) {
                            onErrorTriggered(
                                "Please purchase the pack to view",
                                SnackBarType.WARNING
                            )
                            return@SubToSubCategoryPage
                        }

                        CategoryNavigationFragments.Files.title =
                            StringUtil.DynamicText(subToSubCat.name)

                        categoryNavController.navigate("files/${subToSubCat.catId}/${subToSubCat.subCatId}/${subToSubCat.id}") {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }, onFileClicked = { homeFile, query, index ->
                        if (!user.isPaidCustomer) {
                            onErrorTriggered(
                                "Please purchase the pack to view",
                                SnackBarType.WARNING
                            )
                            return@SubToSubCategoryPage
                        }
                        categoryNavController.onFileClicked(homeFile, query, index)
                    })
            }
            composable(
                route = CategoryNavigationFragments.Files.route,
                arguments = listOf(navArgument("cat_id") { type = NavType.IntType },
                    navArgument("sub_cat_id") { type = NavType.IntType },
                    navArgument("sub_to_sub_cat_id") { type = NavType.IntType })
            ) {
                FilePage(
                    onFileClicked = { homeFile, query, index ->
                        if (!user.isPaidCustomer) {
                            onErrorTriggered(
                                "Please purchase the pack to view",
                                SnackBarType.WARNING
                            )
                            return@FilePage
                        }
                        categoryNavController.onFileClicked(homeFile, query, index)
                    })
            }

            composable(
                route = CategoryNavigationFragments.FileDetails.route,
                arguments = listOf(
                    navArgument("file_id") { type = NavType.IntType },
                    navArgument("file_url") { type = NavType.StringType },
                    navArgument("audio_url") {
                        nullable = true
                        type = NavType.StringType
                        defaultValue = null
                    },
                    navArgument("audio_image") {
                        nullable = true
                        type = NavType.StringType
                        defaultValue = null
                    },
                    navArgument("query") {
                        nullable = true
                        type = NavType.StringType
                        defaultValue = null
                    }, navArgument("index") {
                        type = NavType.IntType
                        defaultValue = -1
                    })
            ) {
                TextDocumentScreen()
            }
            composable(
                route = CategoryNavigationFragments.Pdf.route,
                arguments = listOf(
                    navArgument("file_id") { type = NavType.IntType },
                    navArgument("file_url") { type = NavType.StringType },
                    navArgument("audio_url") {
                        nullable = true
                        type = NavType.StringType
                        defaultValue = null
                    },
                    navArgument("audio_image") {
                        nullable = true
                        type = NavType.StringType
                        defaultValue = null
                    }
                )
            ) {
                PdfScreen()
            }
        }
    }
}

private fun NavController.onFileClicked(
    homeFile: HomeFile,
    query: String,
    index: Int
) {

    when (homeFile.type) {
        FileType.TYPE_TEXT -> {
            CategoryNavigationFragments.FileDetails.title = StringUtil.DynamicText(homeFile.name)
            navigate("file_details?file_id=${homeFile.id}&file_url=${homeFile.fileUrl}&query=$query&index=$index") {
                launchSingleTop = true
                restoreState = true
            }
        }

        FileType.TYPE_PDF -> {
            CategoryNavigationFragments.Pdf.title = StringUtil.DynamicText(homeFile.name)
            navigate("pdf?file_id=${homeFile.id}&file_url=${homeFile.fileUrl}&audio_url=${homeFile.audioUrl}&audio_image=${homeFile.audioImage}") {
                launchSingleTop = true
                restoreState = true
            }
        }

        FileType.TYPE_AUDIO -> {
            CategoryNavigationFragments.FileDetails.title = StringUtil.DynamicText(homeFile.name)
            navigate("file_details?file_id=${homeFile.id}&file_url=${homeFile.fileUrl}&audio_url=${homeFile.audioUrl}&audio_image=${homeFile.audioImage}&query=$query&index=$index") {
                launchSingleTop = true
                restoreState = true
            }
        }

        FileType.TYPE_UNKNOWN -> {}
    }
}