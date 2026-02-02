package com.example.mviexample.presentation.posts

import android.health.connect.datatypes.units.Length
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mviexample.domain.model.Post

@Composable
fun QuickViewScreen(
    //item: Post,//TODO:not able to pass custom object,check
    item: String,
    sizeGridList: List<String>
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        HorizontalImageSlider(item)

        Spacer(modifier = Modifier.padding(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Item description",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "$123",
            )
        }


        Spacer(modifier = Modifier.padding(8.dp))

        SelectableGrid(sizeGridList) { selectedItem ->
            Toast.makeText(context, "Selected size :$selectedItem", Toast.LENGTH_SHORT).show()
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
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { },
                contentScale = ContentScale.Crop
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
        modifier = modifier.fillMaxSize(),
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
