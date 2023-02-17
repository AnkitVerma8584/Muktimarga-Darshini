package com.ass.muktimargadarshini.ui.presentation.navigation.screens.contact

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ass.muktimargadarshini.R

@Composable
fun ContactPage() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = stringResource(id = R.string.contact_us),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.fillMaxWidth(0.9f),
            text = stringResource(id = R.string.sub_heading),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(50.dp))
        Details(icon = R.drawable.ic_person, details = R.string.name){
            /*https://www.asihwaryasoftware.in*/
        }
        Details(icon = R.drawable.ic_contact, details = R.string.phone) {

        }
        Details(icon = R.drawable.ic_location, details = R.string.address) {

        }
    }
}

@Composable
private fun Details(
    @DrawableRes icon: Int,
    @StringRes details: Int,
    onClick: () -> Unit = {}
) {
    Row(modifier = Modifier
        .fillMaxWidth(0.8f)
        .clickable { onClick() }) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = stringResource(id = details))
    }
    Spacer(modifier = Modifier.height(18.dp))
}
