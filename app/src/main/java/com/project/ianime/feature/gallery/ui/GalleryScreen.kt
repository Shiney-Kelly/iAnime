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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.ianime.R
import com.project.ianime.core.model.GalleryItem
import com.project.ianime.core.ui.theme.Accent
import com.project.ianime.core.ui.theme.CoverInitial
import com.project.ianime.core.ui.theme.CoverInitialShadow
import com.project.ianime.core.ui.theme.Ink
import com.project.ianime.core.ui.theme.Muted
import com.project.ianime.core.ui.theme.coverBrush
import com.project.ianime.feature.gallery.GalleryViewModel

@Composable
fun GalleryScreen(
    modifier: Modifier = Modifier,
    viewModel: GalleryViewModel = viewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    Column(modifier = modifier
        .padding(horizontal = 8.dp)
        .fillMaxSize()) {

    }
}

@Composable
fun GalleryTitle(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
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
    Box(
        modifier = modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = "$shownCounts of $totalCounts titles",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = Muted
        )
    }
}

@Composable
fun GalleryList(
    modifier: Modifier = Modifier,
    galleryLists: List<GalleryItem>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(galleryLists, key = {it.animeId}) { item ->
            val colorBrush = remember(item.animeId) { coverBrush(item.animeId) }
            GalleryItemUi(
                animeTitle = item.displayName(),
                animeSubTitle = item.displaySubtitle(),
                status = stringResource(item.watchStatus.label),
                // TODO map the status with the color
                statusBackgroundColor = Color.White,
                rate = item.rate,
                coverBrush = colorBrush,
            )
        }
    }
}

@Composable
fun GalleryItemUi(
    modifier: Modifier = Modifier,
    animeTitle: String,
    animeSubTitle: String,
    status: String,
    statusBackgroundColor: Color,
    rate: String,
    // random generated card background later to use anime image url
    coverBrush: Brush,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            GalleryItemCoverUi(
                status = status,
                statusBackgroundColor = statusBackgroundColor,
                rate = rate,
                coverBrush = coverBrush,
                centerText = animeTitle.trim().firstOrNull()?.uppercase().orEmpty()
            )
            Text(
                text = animeTitle,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = animeSubTitle,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun GalleryItemCoverUi(
    modifier: Modifier = Modifier,
    status: String,
    statusBackgroundColor: Color,
    rate: String,
    // random generated card background later to use anime image url
    coverBrush: Brush,
    centerText: String,
) {

    Box(
        modifier = modifier
            .padding(8.dp)
            .background(brush = coverBrush, RoundedCornerShape(18.dp))
            .fillMaxWidth()
            .aspectRatio(3f / 4f)
    ) {
        StatusBadge(
            modifier = Modifier.align(Alignment.TopStart),
            status = status,
            backgroundColor = statusBackgroundColor,
        )
        RateBadge(
            modifier = Modifier.align(Alignment.TopEnd),
            rate = rate
        )
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
    backgroundColor: Color,
    status: String,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
        ),
    ) {
        Text(
            text = status,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun RateBadge(
    modifier: Modifier = Modifier,
    rate: String,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFD66B),
        ),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
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
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}