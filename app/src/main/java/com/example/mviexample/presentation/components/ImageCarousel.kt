package com.example.mviexample.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCarouselMultiBrowse(
    images: List<String>,
    modifier: Modifier = Modifier
) {
    val carouselState = rememberCarouselState { images.count() }

    val scope = rememberCoroutineScope()

    Box(modifier = modifier) {

        HorizontalMultiBrowseCarousel(
            state = carouselState,
            preferredItemWidth = 320.dp,
            itemSpacing = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) { index ->
            AsyncImage(
                model = images[index],
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
            )
        }

        // LEFT ARROW
        ArrowButton(
            icon = Icons.Default.ArrowBack,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            scope.launch {
                carouselState.animateScrollToItem(
                    (carouselState.currentItem - 1).coerceAtLeast(0)
                )
            }
        }

        // RIGHT ARROW
        ArrowButton(
            icon = Icons.Default.ArrowForward,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            scope.launch {
                carouselState.animateScrollToItem(
                    (carouselState.currentItem + 1)
                        .coerceAtMost(images.lastIndex)
                )
            }
        }

        // INDICATOR BAR
        CarouselBarIndicator(
            total = images.size,
            activeIndex = carouselState.currentItem,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp)
        )
    }
}

@Composable
private fun ArrowButton(
    icon: ImageVector,
    modifier: Modifier,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .padding(8.dp)
            .background(
                Color.Black.copy(alpha = 0.4f),
                CircleShape
            )
            .size(36.dp)
    ) {
        Icon(icon, contentDescription = null, tint = Color.White)
    }
}

@Composable
fun CarouselBarIndicator(
    total: Int,
    activeIndex: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(total) { index ->
            Box(
                modifier = Modifier
                    .height(4.dp)
                    .width(if (index == activeIndex) 24.dp else 12.dp)
                    .background(
                        if (index == activeIndex)
                            Color.Black
                        else
                            Color.Black.copy(alpha = 0.3f),
                        RoundedCornerShape(2.dp)
                    )
            )
        }
    }
}


