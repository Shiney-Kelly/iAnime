package com.project.ianime.feature.gallery.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.shadow.Shadow as DropShadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.ianime.R
import com.project.ianime.core.model.GalleryItem
import com.project.ianime.core.model.WatchStatus
import com.project.ianime.core.model.mapToStatusColor
import com.project.ianime.core.ui.theme.Accent
import com.project.ianime.core.ui.theme.BadgeText
import com.project.ianime.core.ui.theme.CardBackground
import com.project.ianime.core.ui.theme.CardShadow
import com.project.ianime.core.ui.theme.CoverInitial
import com.project.ianime.core.ui.theme.CoverInitialShadow
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

//            Icon(
//                modifier = Modifier.align(Alignment.TopEnd).offset(x = 10.dp, y = (-4).dp).size(16.dp),
//                painter = painterResource(id = ),
//                contentDescription = null,
//                tint = Pop
//            )
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

@Composable
fun GalleryItemUi(
    modifier: Modifier = Modifier,
    card: GalleryCardUi,
) {
    val cardShape = RoundedCornerShape(24.dp)
    Card(
        modifier = modifier
            .fillMaxWidth()
            .dropShadow(
                shape = cardShape,
                shadow = DropShadow(
                    radius = 22.dp,
                    color = CardShadow,
                    offset = DpOffset(x = 0.dp, y = 8.dp)
                )
            ),
        shape = cardShape,
        colors = CardDefaults.cardColors(
            containerColor = CardBackground,
        ),
    ) {
        Column {
            GalleryItemCoverUi(
                watchStatus = card.watchStatus,
                rate = card.rate,
                coverBrush = card.coverBrush,
                centerText = card.letter
            )
            Text(
                modifier = Modifier.padding(
                    start = Padding.Medium,
                    top = Padding.CardTitleTop,
                    end = Padding.Medium
                ),
                text = card.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    color = Ink,
                    fontSize = 16.sp,
                    lineHeight = 22.4.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                modifier = Modifier.padding(
                    start = Padding.Medium,
                    top = Padding.XSmall,
                    end = Padding.Medium,
                    bottom = Padding.Medium
                ),
                text = card.subtitle,
                style = TextStyle(
                    color = Muted,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
fun GalleryItemCoverUi(
    modifier: Modifier = Modifier,
    watchStatus: WatchStatus,
    rate: String,
    // random generated card background later to use anime image url
    coverBrush: Brush,
    centerText: String,
) {
    val isRattingBadgeHidden = watchStatus == WatchStatus.PLAN
    Box(
        modifier = modifier
            .padding(start = Padding.Small, top = Padding.Small, end = Padding.Small)
            .fillMaxWidth()
            .aspectRatio(3f / 4f)
            .background(brush = coverBrush, RoundedCornerShape(18.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Padding.Small)
                .align(Alignment.TopStart),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatusBadge(watchStatus = watchStatus)
            RateBadge(
                rate = rate,
                isHidden = isRattingBadgeHidden
            )
        }
        CoverLetter(
            modifier = Modifier.align(Alignment.Center),
            letter = centerText
        )
    }
}

@Composable
fun CoverLetter(
    modifier: Modifier = Modifier,
    letter: String,
) {
    val density = LocalDensity.current
    val letterShadow = remember(density) {
        with(density) {
            Shadow(
                color = CoverInitialShadow,
                offset = Offset(0f, 6.dp.toPx()),
                blurRadius = 16.dp.toPx()
            )
        }
    }

    Text(
        modifier = modifier.clearAndSetSemantics { },
        text = letter,
        style = TextStyle(
            color = CoverInitial,
            fontSize = 84.sp,
            lineHeight = 84.sp,
            fontWeight = FontWeight.Bold,
            shadow = letterShadow,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.Both
            )
        )
    )
}

@Composable
fun StatusBadge(
    modifier: Modifier = Modifier,
    watchStatus: WatchStatus,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        colors = CardDefaults.cardColors(
            containerColor = watchStatus.mapToStatusColor(),
        ),
    ) {
        Text(
            modifier = Modifier.padding(horizontal = Padding.Small, vertical = Padding.XSmall),
            text = stringResource(watchStatus.label),
            style = TextStyle(
                color = BadgeText,
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.22.sp
            )
        )
    }
}

@Composable
fun RateBadge(
    modifier: Modifier = Modifier,
    rate: String,
    isHidden: Boolean
) {
    if (!isHidden) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(50),
            colors = CardDefaults.cardColors(
                containerColor = Pop,
            ),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = Padding.Small, vertical = Padding.XSmall),
                horizontalArrangement = Arrangement.spacedBy(Padding.XXSmall),
                verticalAlignment = Alignment.CenterVertically
            ) {
//            Icon(
//                modifier = Modifier.size(8.dp),
//                painter = painterResource(id = ),
//                contentDescription = null,
//                tint = Ink
//            )
                Text(
                    text = rate,
                    style = TextStyle(
                        color = BadgeText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
        }
    }
}