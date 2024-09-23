package com.ass.madhwavahini.ui.presentation.main.components.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ass.madhwavahini.R

@Composable
fun OfferDialog(
    onDismiss: () -> Unit,
    onOkClick: () -> Unit
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = "Later",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = onOkClick
            ) {
                Text(
                    text = "OK",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        },
        title = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    modifier = Modifier
                        .width(200.dp)
                        .align(Alignment.Center),
                    painter = rememberAsyncImagePainter(model = R.drawable.offer),
                    contentDescription = stringResource(id = R.string.app_name),
                    contentScale = ContentScale.Crop,
                )
            }
        },
        text = {
            Text(
                text = "Purchase the pack and enjoy unlimited access to all the files.",
                fontWeight = FontWeight.Bold
            )
        }
    )
}
