package com.ass.madhwavahini.ui.presentation.ui_new.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ass.madhwavahini.ui.presentation.navigation.modal.ProfileNavigationFragments
import com.ass.madhwavahini.ui.presentation.ui_new.profile.components.MyRow
import com.ass.madhwavahini.ui.presentation.ui_new.profile.components.SettingOptions
import com.ass.madhwavahini.ui.theme.ShowPreview
import com.ass.madhwavahini.ui.theme.UiModePreviews
import com.ass.madhwavahini.ui.theme.sw16

@Composable
fun ProfilePage(
    onLogout: () -> Unit,
    onNavigate: (route: String) -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            sw16()
            AsyncImage(
                model = "https://source.unsplash.com/1200x1200/?travel",
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
            TextButton(onClick = onLogout) {
                Text(text = "Logout")
            }
        }
        Divider()
        MyRow(title = "Full Name", body = "This is a full name")
        Divider()
        MyRow(title = "Phone number", body = "XXXXXXXXXX")
        Divider()
        SettingOptions(text = "About Us") {
            onNavigate(ProfileNavigationFragments.AboutUs.route)
        }
        Divider()
        SettingOptions(text = "Support") {
            onNavigate(ProfileNavigationFragments.Support.route)
        }
        Divider()
        SettingOptions(text = "Contact Us") {
            onNavigate(ProfileNavigationFragments.ContactUs.route)
        }
    }
}

@UiModePreviews
@Composable
private fun ProfilePreview() {
    ShowPreview {
        ProfilePage({}) {

        }
    }
}