package com.ass.madhwavahini.ui_new.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ass.madhwavahini.ui.presentation.navigation.modal.NavigationFragment
import com.ass.madhwavahini.ui.theme.ShowPreview
import com.ass.madhwavahini.ui.theme.UiModePreviews

@Composable
fun ProfilePage(
    onNavigate: (route: String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "https://source.unsplash.com/1200x1200/?travel",
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
        Divider()
        MyRow(title = "Full Name", body = "This is a full name")
        Divider()
        MyRow(title = "Phone number", body = "XXXXXXXXXX")
        Divider()
        SettingOptions(text = "About Us") {
            onNavigate(NavigationFragment.About.route)
        }
        Divider()
        SettingOptions(text = "Support") {
            onNavigate(NavigationFragment.Support.route)
        }
        Divider()
        SettingOptions(text = "Contact Us") {
            onNavigate(NavigationFragment.Contact.route)
        }
    }
}

@Composable
private fun SettingOptions(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable(onClick = onClick)
    ) {
        Text(text = text, modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null
        )
    }
}

@Composable
private fun MyRow(title: String, body: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Text(text = title, modifier = Modifier.weight(1f))
        Text(text = body, modifier = Modifier.weight(1.5f))
    }
}

@UiModePreviews
@Composable
private fun ProfilePreview() {
    ShowPreview {
        ProfilePage() {

        }
    }
}