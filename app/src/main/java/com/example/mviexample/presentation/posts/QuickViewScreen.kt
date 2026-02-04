package com.example.mviexample.presentation.posts

import android.health.connect.datatypes.units.Length
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mviexample.domain.model.Post
import com.example.mviexample.presentation.components.ImageCarouselMultiBrowse
import kotlinx.coroutines.launch

@Composable
fun QuickViewScreen(
    //item: Post,//TODO:not able to pass custom object,check
    item: String,
    sizeGridList: List<String>
) {
    val context = LocalContext.current

    //TODO:Use lazycolumn
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val imageList = mutableListOf<String>()
            for (i in 1..10) {
                imageList.add("https://picsum.photos/seed/${i}/200/200")
            }
        //HorizontalImageSlider(item)
//        ImageCarousel(imageList)
        ImageCarouselMultiBrowse(imageList)

        Spacer(modifier = Modifier.padding(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Item description",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "$123",
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "Selected Color")

        Spacer(modifier = Modifier.height(24.dp))

        //Radio button type selector for color

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "SELECT SIZE",
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "SIZE GUIDE +",
                textDecoration = TextDecoration.Underline
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        SelectableGrid(sizeGridList) { selectedItem ->
            Toast.makeText(context, "Selected size :$selectedItem", Toast.LENGTH_SHORT).show()
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            shape = RectangleShape
        ) {
            Text(
                text = "ADD TO BAG",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun HorizontalImageSlider(post: String) {
    LazyRow(modifier = Modifier.height(500.dp)) {
        items(10) { item ->
            AsyncImage(
                model = "https://picsum.photos/seed/${item}/200/200",
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { },
                contentScale = ContentScale.Crop
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarousel(
    images: List<String>, // URLs or drawable names
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { images.size })
    val scope = rememberCoroutineScope()

    Box(modifier = modifier) {

        // IMAGE PAGER
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            AsyncImage(
                model = images[page],
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )
        }

        // LEFT ARROW
        IconButton(
            onClick = {
                scope.launch {
                    val prev = (pagerState.currentPage - 1).coerceAtLeast(0)
                    pagerState.animateScrollToPage(prev)
                }
            },
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(8.dp)
                .background(Color.Black.copy(alpha = 0.4f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Previous",
                tint = Color.White
            )
        }

        // RIGHT ARROW
        IconButton(
            onClick = {
                scope.launch {
                    val next =
                        (pagerState.currentPage + 1).coerceAtMost(images.size - 1)
                    pagerState.animateScrollToPage(next)
                }
            },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(8.dp)
                .background(Color.Black.copy(alpha = 0.4f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Next",
                tint = Color.White
            )
        }

        // INDICATOR BAR (VIDEO-STYLE)
        CarouselProgressIndicator(
            pageCount = images.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp)
        )
    }
}

@Composable
fun CarouselProgressIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .height(4.dp)
                    .width(if (index == currentPage) 24.dp else 12.dp)
                    .background(
                        color = if (index == currentPage) Color.Black
                        else Color.Black.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(2.dp)
                    )
            )
        }
    }
}

@Composable
fun SelectableGrid(
    items: List<String>,
    modifier: Modifier = Modifier,
    columns: Int = 5,
    itemHeight: Int? = null,
    textSize: Int = 12,
    onItemSelected: (String) -> Unit
) {
    var selectedItem by remember { mutableStateOf("") }

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),//height always required for scrolling
        contentPadding = PaddingValues(8.dp)
    ) {
        items(items) { item ->
            val isSelected = item == selectedItem

            Box(
                modifier = Modifier
                    .then(
                        if (itemHeight == null) Modifier.aspectRatio(1f) else Modifier.height(
                            itemHeight.dp
                        )
                    )
                    .border(
                        width = if (isSelected) 1.dp else 0.5.dp,
                        color = if (isSelected) Color.Black else Color.LightGray
                    )
                    .clickable {
                        selectedItem = item
                        onItemSelected(item)
                    }
                    .padding(12.dp)) {
                Text(
                    text = item,
                    fontSize = textSize.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun ColorSelector(
    color: Color,
    selected: Boolean = false
) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = Color.Black
            )
            .background(color)
    )
}
