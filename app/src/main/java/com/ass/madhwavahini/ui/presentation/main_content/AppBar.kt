package com.ass.madhwavahini.ui.presentation.main_content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.ui.presentation.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(
    title: String,
    hamburgerIconClicked: () -> Unit,
    navigationBackClicked: () -> Unit,
    isNavigationFragment: Boolean,
    mainViewModel: MainViewModel,
    user: User
) {

    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ), title = {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }, navigationIcon = {
        if (isNavigationFragment) {
            Icon(imageVector = Icons.Filled.Menu,
                contentDescription = null,
                modifier = Modifier
                    .clickable { hamburgerIconClicked() }
                    .padding(8.dp))
        } else {
            Icon(imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .clickable { navigationBackClicked() }
                    .padding(8.dp))
        }
    }, actions = {
        if (!user.isPaidCustomer) IconButton(onClick = {
            mainViewModel.getOrder()
        }) {
            Text(text = "BUY")
        }

    })
}