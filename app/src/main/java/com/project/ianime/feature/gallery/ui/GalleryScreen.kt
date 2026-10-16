package com.project.ianime.feature.gallery.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.ianime.R
import com.project.ianime.core.model.GalleryItem
import com.project.ianime.core.ui.theme.Accent
import com.project.ianime.core.ui.theme.Ink
import com.project.ianime.core.ui.theme.Muted
import com.project.ianime.core.ui.theme.Padding
import com.project.ianime.core.ui.theme.Pop
import com.project.ianime.feature.gallery.GalleryUiState
import com.project.ianime.feature.gallery.GalleryViewModel

@Composable
fun GalleryScreen(
    modifier: Modifier = Modifier,
    viewModel: GalleryViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Column(modifier = modifier.fillMaxSize()) {
        when (val uiState = state) {
            GalleryUiState.Empty -> {}
            GalleryUiState.Loading -> {}
            is GalleryUiState.Error -> {}
            is GalleryUiState.Loaded -> {
                val count = uiState.animeList.size.toString()
                GalleryTitle()
                CountLine(shownCounts = count, totalCounts = count)
                GalleryList(galleryLists = uiState.animeList)
            }
        }
    }
}

@Composable
fun GalleryTitle(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = Padding.Large, top = Padding.Small, end = Padding.Large),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Padding.Small)
    ) {
        Text(
            text = stringResource(id = R.string.gallery_title_prefix),
            style = MaterialTheme.typography.headlineMedium,
            color = Ink,
            fontWeight = FontWeight.Bold
        )

        Box {
            Text(
                text = stringResource(id = R.string.gallery_title_highlight),
                style = MaterialTheme.typography.headlineMedium,
                color = Accent,
                fontWeight = FontWeight.Bold
            )

            Icon(
                modifier = Modifier.align(Alignment.TopEnd).offset(x = 10.dp, y = (-4).dp).size(16.dp),
                painter = painterResource(id = R.drawable.title_sparkle),
                contentDescription = null,
                tint = Pop
            )
        }
    }
}

@Composable
fun CountLine(
    modifier: Modifier = Modifier,
    shownCounts: String,
    totalCounts: String,
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = Padding.Large, top = Padding.Small, end = Padding.Large),
        text = "$shownCounts of $totalCounts titles",
        style = MaterialTheme.typography.bodyMedium,
        fontSize = 15.sp,
        fontWeight = FontWeight.Medium,
        color = Muted
    )
}

@Composable
fun GalleryList(
    galleryLists: List<GalleryItem>
) {
    val context = LocalContext.current
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = Padding.Large, vertical = Padding.Small),
        verticalArrangement = Arrangement.spacedBy(Padding.Medium),
        horizontalArrangement = Arrangement.spacedBy(Padding.Medium)
    ) {
        items(galleryLists, key = { it.animeId }) { item ->
            val card = remember(item, context) { item.toCardUi(context) }
            GalleryItemUi(card = card)
        }
    }
}
