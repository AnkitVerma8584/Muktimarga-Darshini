package com.ass.madhwavahini.ui.presentation.navigation.screens.support

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.R
import com.ass.madhwavahini.util.print


@Composable
fun SupportPage() {
    val scrollState = rememberScrollState()
    val uriHandler = LocalUriHandler.current
    val resources = LocalContext.current.resources
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = stringResource(id = R.string.support),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.fillMaxWidth(0.9f),
            text = stringResource(id = R.string.support_sub_heading),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(50.dp))

        Details(icon = Icons.Outlined.Person, details = R.string.support_name)
        Details(icon = Icons.Outlined.Phone, details = R.string.support_phone) {

        }
        Details(icon = Icons.Outlined.MailOutline, details = R.string.support_email) {
            try {
                uriHandler.openUri("mailto:${resources.getString(R.string.support_email)}?body=I need help regarding &subject=Required help in Madhva Vahini")
            } catch (e: Exception) {
                e.print()
            }
        }
        Details(icon = Icons.Outlined.LocationOn, details = R.string.support_location) {

        }
    }
}

@Composable
private fun Details(
    icon: ImageVector,
    @StringRes details: Int,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .clickable(onClick = onClick)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = stringResource(id = details))
    }
    Spacer(modifier = Modifier.height(18.dp))
}

