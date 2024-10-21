package com.testtask.project.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.testtask.project.data.ProductsItem
import com.testtask.project.data.Rating
import com.testtask.project.presentation.detail.store.DetailStore


@Composable
fun DetailContent(
    component: DetailComponent,
) {
    val state by component.state.subscribeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(16.dp),
        verticalArrangement = Arrangement.Top // Align children to the top
    ) {
        when {
            state.loading -> CircularProgressIndicator()
            state.product != null -> ProductDetails(state.product!!, component)
            else -> Text("Product not found.", color = Color.Red)
        }
    }
}

@Composable
fun ProductDetails(product: ProductsItem, component: DetailComponent) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        ProductCard(product = product, component = component)

        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            TitleText(text = product.title)
            CategoryChip(category = product.category)

            PriceRow(price = product.price)

            DetailsSection(product)
        }
    }
}

@Composable
fun ProductCard(product: ProductsItem, component: DetailComponent) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)),
        elevation = 8.dp,
        backgroundColor = Color(0xFFD4F1F4)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            IconButton(
                onClick = { component.onEvent(DetailStore.Intent.BackPressed) }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back"
                )
            }

            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(product.image)
                    .crossfade(true)
                    .build(),
                contentDescription = product.title,
                loading = {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                },
                error = {
                    Icon(Icons.Default.Error, contentDescription = "Error loading image")
                },
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .align(Alignment.CenterVertically)
                    .background(Color.White)
                    .weight(1f)
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Composable
fun TitleText(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        fontSize = 22.sp,
        color = Color(0xFF007EA7),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CategoryChip(category: String) {
    Row (  modifier = Modifier.fillMaxWidth()
        .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Category",
            style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 16.sp, color = Color.Black)
        )
        AssistChip(
            label = { Text(text = category) },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = Color(0xFFD4F1F4),
                labelColor = Color(0xFF007EA7)
            ),
            onClick = { }
        )
    }

}

@Composable
fun PriceRow(price: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Price",
            style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 16.sp, color = Color.Black)
        )
        Text(
            text = "$$price",
            style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 16.sp, color = Color.Black)
        )
    }
}

@Composable
fun DetailsSection(product: ProductsItem) {
    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
        Text(
            text = "Details",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color(0xFF007EA7)
        )
        Spacer(modifier = Modifier.height(8.dp))

        RatingAndVotesRow(product.rating)

        Text(
            text = "Description",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color(0xFF007EA7),
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = product.description,
            color = Color(0xFF4F4F4F),
            fontSize = 16.sp,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun RatingAndVotesRow(rating: Rating) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text("Rate", color = Color(0xFF4F4F4F), fontWeight = FontWeight.Medium, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Votes", fontWeight = FontWeight.Medium, fontSize = 16.sp)
        }

        Column(horizontalAlignment = Alignment.End) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                StarRating(rating = rating.rate)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = rating.rate.toString(), fontWeight = FontWeight.Medium, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "${rating.count}", fontWeight = FontWeight.Medium, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Default.ThumbUp, contentDescription = null, tint = Color.Gray)
            }
        }
    }
}

@Composable
fun StarRating(rating: Double) {
    Row {
        val fullStars = rating.toInt()
        val halfStars = if (rating % 1 >= 0.5) 1 else 0
        val emptyStars = 5 - (fullStars + halfStars)

        repeat(fullStars) {
            Icon(Icons.Default.StarRate, contentDescription = "Full Star", tint = Color.DarkGray, modifier = Modifier.size(24.dp))
        }

        if (halfStars == 1) {
            Icon(Icons.AutoMirrored.Filled.StarHalf, contentDescription = "Half Star", tint = Color.DarkGray, modifier = Modifier.size(24.dp))
        }

        repeat(emptyStars) {
            Icon(Icons.Default.StarOutline, contentDescription = "Empty Star", tint = Color.Gray, modifier = Modifier.size(24.dp))
        }
    }
}
