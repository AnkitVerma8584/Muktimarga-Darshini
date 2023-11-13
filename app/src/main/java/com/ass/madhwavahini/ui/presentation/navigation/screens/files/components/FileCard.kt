package com.ass.madhwavahini.ui.presentation.navigation.screens.files.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.modals.FileType
import com.ass.madhwavahini.domain.modals.HomeFile

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.FileCard(
    item: HomeFile,
    onFileClicked: (homeFile: HomeFile, query: String, index: Int) -> Unit,
) {
    ElevatedCard(modifier = Modifier
        .padding(8.dp)
        .animateItemPlacement()
        .fillMaxWidth()
        .clickable {
            onFileClicked(item, "", -1)
        }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                if (item.description.isNotBlank())
                    Text(
                        text = item.description,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    )
            }
            Image(
                painter = painterResource(
                    id =
                    when (item.type) {
                        FileType.TYPE_TEXT -> R.drawable.ic_txt
                        FileType.TYPE_PDF -> R.drawable.ic_pdf
                        FileType.TYPE_AUDIO -> R.drawable.ic_music
                        else -> R.drawable.ic_default_file
                    }
                ),
                contentDescription = null
            )
        }


    }

}