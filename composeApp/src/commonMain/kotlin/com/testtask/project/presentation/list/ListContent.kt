package com.testtask.project.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.testtask.project.presentation.list.store.ListStore

@Composable
fun ListContent(
    component: ListComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.state.subscribeAsState()

        LazyColumn(
            state = rememberLazyListState(),
            modifier = modifier
                .background(Color(0xFFF0F0F0))
                .padding(WindowInsets.statusBars.asPaddingValues())
                .padding(8.dp)
        ) {
            if (state.loading) {
                item {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
            }

            if (state.error != null) {
                item {
                    Text(
                        text = state.error!!,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp).fillMaxSize(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            items(state.products) { product ->
                ProductItemRow(
                    product = product,
                    onPostClicked = { component.onEvent(ListStore.Intent.PostClicked(product)) }
                )
            }
        }
}

@Composable
fun ProductItemRow(
    product: ProductsItem,
    onPostClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onPostClicked() },
        elevation = 4.dp,
        backgroundColor = Color(0xFFD4F1F4)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalPlatformContext.current)
                            .data(product.image)
                            .crossfade(true)
                            .build(),
                        contentDescription = product.title,
                        loading = {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        },
                        error = { errorState ->
                            Icon(Icons.Default.Error, contentDescription = "Error loading image")
                        },
                        modifier = Modifier
                            .size(132.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.body1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        maxLines = 3,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                }

                Row(
                    modifier = Modifier.align(Alignment.End)
                        .padding(8.dp)
                    ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$ ${product.price}",
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.h6.copy(color = Color(0xFF1E88E5))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onPostClicked,
                        modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF009688))
                    ) {
                        Text("Purchase", color = Color.White)
                    }
                }
            }
        }
    }
}

