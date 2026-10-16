package com.project.ianime.feature.gallery.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.shadow.Shadow as DropShadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.ianime.core.model.WatchStatus
import com.project.ianime.core.model.mapToStatusColor
import com.project.ianime.core.ui.theme.BadgeText
import com.project.ianime.core.ui.theme.CardBackground
import com.project.ianime.core.ui.theme.CardShadow
import com.project.ianime.core.ui.theme.CoverInitial
import com.project.ianime.core.ui.theme.CoverInitialShadow
import com.project.ianime.core.ui.theme.Ink
import com.project.ianime.core.ui.theme.Muted
import com.project.ianime.core.ui.theme.Padding
import com.project.ianime.core.ui.theme.Pop
import com.project.ianime.core.ui.theme.coverBrush

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

@Preview(showBackground = true, widthDp = 190)
@Composable
private fun GalleryItemUiPreview() {
    GalleryItemUi(
        modifier = Modifier.padding(Padding.Large),
        card = GalleryCardUi(
            title = "Bocchi the Rock!",
            subtitle = "2022 · Legend, Comedy",
            letter = "B",
            watchStatus = WatchStatus.DONE,
            rate = "9",
            coverBrush = coverBrush("bocchi-the-rock")
        )
    )
}
