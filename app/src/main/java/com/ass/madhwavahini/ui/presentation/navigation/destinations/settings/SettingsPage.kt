package com.ass.madhwavahini.ui.presentation.navigation.destinations.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContactSupport
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.R
import com.ass.madhwavahini.data.Constants
import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.ui.presentation.navigation.modal.SettingsNavHostFragments
import com.ass.madhwavahini.ui.theme.ShowPreview
import com.ass.madhwavahini.ui.theme.UiModePreviews
import com.ass.madhwavahini.ui.theme.sh8

@Composable
fun SettingsPage(
    user: User,
    onLogout: () -> Unit,
    onNavigate: (route: String) -> Unit = {}
) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        columns = StaggeredGridCells.Adaptive(Constants.SETTINGS_GRID_SIZE),
        verticalItemSpacing = 16.dp,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            ElevatedCard {
                sh8.invoke()
                MyRow(text = user.userName, icon = Icons.Outlined.Person)
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                )
                MyRow(text = user.userPhone, icon = Icons.Outlined.Phone)
                sh8()
            }
        }
        item {
            ElevatedCard {
                sh8.invoke()
                SettingOptions(
                    text = stringResource(id = R.string.about),
                    icon = Icons.Outlined.Info
                ) {
                    onNavigate(SettingsNavHostFragments.AboutUs.route)
                }
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                )
                SettingOptions(
                    text = stringResource(id = R.string.contact_us),
                    icon = Icons.Outlined.ContactSupport
                ) {
                    onNavigate(SettingsNavHostFragments.ContactUs.route)
                }
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                )
                SettingOptions(
                    text = stringResource(id = R.string.support),
                    icon = Icons.Outlined.SupportAgent
                ) {
                    onNavigate(SettingsNavHostFragments.Support.route)
                }
                sh8.invoke()
            }
        }
        item {
            ElevatedCard {
                sh8.invoke()
                SettingOptions(
                    text = stringResource(id = R.string.logout),
                    icon = Icons.Outlined.Logout,
                    onClick = onLogout
                )
                sh8.invoke()
            }
        }
    }
}

@UiModePreviews
@Composable
private fun ProfilePreview() {
    ShowPreview {
        SettingsPage(user = User(userName = "Warklord saf", userPhone = "86451312"), {}) {

        }
    }
}