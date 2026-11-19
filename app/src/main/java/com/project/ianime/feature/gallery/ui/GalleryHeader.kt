package com.project.ianime.feature.gallery.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.ianime.R
import com.project.ianime.core.ui.theme.Accent
import com.project.ianime.core.ui.theme.AvatarLetter
import com.project.ianime.core.ui.theme.Background
import com.project.ianime.core.ui.theme.CardBackground
import com.project.ianime.core.ui.theme.Ink
import com.project.ianime.core.ui.theme.Line
import com.project.ianime.core.ui.theme.Padding
import com.project.ianime.core.ui.theme.Pop

@Composable
fun GalleryHeader(
    modifier: Modifier = Modifier,
    languageLabel: String,
    isDarkMode: Boolean,
    avatarInitial: String,
    onLanguageClick: () -> Unit,
    onThemeClick: () -> Unit,
) {
    val borderWidth = 1.5.dp
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(68.dp)
            .background(Background)
            .drawBehind {
                val y = size.height - borderWidth.toPx() / 2
                drawLine(
                    color = Line,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = borderWidth.toPx()
                )
            }
            .padding(horizontal = Padding.Large),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(42.dp),
            painter = painterResource(id = R.drawable.cat_logo),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(Padding.Small))
        Wordmark()
        Spacer(modifier = Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.spacedBy(Padding.Small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeaderButton(onClick = onLanguageClick) {
                Text(
                    text = languageLabel,
                    color = Ink,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            HeaderButton(onClick = onThemeClick) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = if (isDarkMode) R.drawable.sun else R.drawable.moon),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
            Avatar(initial = avatarInitial)
        }
    }
}

@Composable
private fun Wordmark() {
    val prefix = stringResource(id = R.string.gallery_wordmark_prefix)
    val name = stringResource(id = R.string.gallery_wordmark_name)
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(color = Accent)) { append(prefix) }
            withStyle(SpanStyle(color = Ink)) { append(name) }
        },
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.24).sp
    )
}

@Composable
private fun HeaderButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.size(42.dp),
        shape = RoundedCornerShape(16.dp),
        color = CardBackground,
        border = BorderStroke(1.5.dp, Line)
    ) {
        Box(contentAlignment = Alignment.Center) { content() }
    }
}

@Composable
private fun Avatar(initial: String) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .background(Pop, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial,
            color = AvatarLetter,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GalleryHeaderPreview() {
    GalleryHeader(
        languageLabel = stringResource(id = R.string.setting_chinese),
        isDarkMode = false,
        avatarInitial = stringResource(id = R.string.gallery_avatar_initial),
        onLanguageClick = {},
        onThemeClick = {}
    )
}
