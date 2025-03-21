package com.nabssam.bestbook.presentation.ui.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import com.nabssam.bestbook.presentation.ui.components.GradientButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.nabssam.bestbook.R
import com.nabssam.bestbook.data.remote.dto.ProductType
import com.nabssam.bestbook.domain.model.CartItem
import com.nabssam.bestbook.domain.model.StockType
import com.nabssam.bestbook.presentation.ui.components.BookCoverImage
import com.nabssam.bestbook.presentation.ui.components.BookTitlePrice
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import com.nabssam.bestbook.presentation.ui.components.TranslucentLoader
import kotlinx.coroutines.launch

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    goToBookDetail: (String) -> Unit,
    goToAddressScreen: () -> Unit,
    vm: VMCart
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val uiState by vm.uiState.collectAsState()

    when (uiState) {
        is CartUiState.Loading -> {
            TranslucentLoader(modifier = modifier, message = "Loading...")
        }

        is CartUiState.Error -> {
            val error = (uiState as CartUiState.Error).message
            ErrorScreen(
                message = error,
                modifier = modifier,
                onRetry = { vm.fetchAllCartItems() }
            )
        }

        is CartUiState.Idle -> {
            val idleState = (uiState as CartUiState.Idle)
            val cartItems = idleState.allCartItem

            Box(
                modifier = modifier.fillMaxSize()
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 12.dp)
                ) {
                    items(cartItems) {
                        if (it.quantity > 0)
                            CartItem(
                                goToBookDetail = goToBookDetail,
                                cartItem = it,
                                updateQuantity = { quantity ->
                                    vm.updateQuantity(
                                        productId = it.productId,
                                        quantity = quantity
                                    )
                                },
                            )
                    }

                    // price receipt
                    if (idleState.totalItems > 0)
                        item {
                            HorizontalDivider()
                            Text(
                                "Price Breakup",
                                modifier = Modifier.padding(12.dp),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            PriceRow(
                                priceTitle = "Price (${idleState.totalItems} items)",
                                price = "₹${idleState.totalLabelAmount}"
                            )

                            PriceRow(
                                priceTitle = "Discount (${idleState.totalDiscountPer}%)",
                                price = "-₹${idleState.totalDiscountAmount}"
                            )
                            /* PriceRow(
                                 priceTitle = "Delivery Charges",
                                 price = "₹${idleState.totalAmount}",
                                 color = MaterialTheme.colorScheme.secondary,
                                 textDecoration = TextDecoration.LineThrough
                             )*/
                            PriceRow(
                                modifier = Modifier.padding(top = 8.dp),
                                priceTitle = "Total Amount",
                                price = "₹${idleState.totalAmount}",
                                color = MaterialTheme.colorScheme.primary,
                            )

                            Spacer(Modifier.height(/*56.dp*/72.dp))
                        }
                }

                // bottom price card
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        // .height(72.dp)
                        .padding(horizontal = 16.dp)
                        .align(Alignment.BottomCenter),
                    elevation = CardDefaults.elevatedCardElevation(),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
//                Spacer(Modifier.weight(1f))

                        Column {
                            Row(
                                modifier = Modifier
                                    .clickable {
                                        scope.launch {
                                            listState.animateScrollToItem(listState.layoutInfo.totalItemsCount - 4)
                                        }
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Total: ", style = MaterialTheme.typography.bodyLarge)
                                Text(
                                    "₹${idleState.totalAmount}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Icon(Icons.Outlined.Info, "price info", Modifier.size(14.dp))
                            }
                        }
                        GradientButton(
                            onClick = { goToAddressScreen() },
                            modifier = Modifier
                        ) {
                            Text("Proceed")
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun PriceRow(
    modifier: Modifier = Modifier,
    priceTitle: String,
    price: String,
    color: Color = Color.Unspecified,
    textDecoration: TextDecoration = TextDecoration.None
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(priceTitle)
        Text(
            text = price,
            color = color,
            textDecoration = textDecoration,
        )
    }
}


@Composable
fun CartItem(
    modifier: Modifier = Modifier
        .fillMaxSize(),
    goToBookDetail: (String) -> Unit,
    cartItem: CartItem,
    updateQuantity: (Int) -> Unit
) {
    Card(
        onClick = {goToBookDetail(cartItem.productId)},
        modifier = Modifier
            .fillMaxWidth()
            // .defaultMinSize(minHeight = 300.dp)
            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
        enabled = cartItem.stockType != StockType.OUT_OF_STOCK
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .background(color = cartItem.productType.color.copy(alpha = 0.6f))
                    .align(Alignment.TopEnd)
            ) {
                Text(
                    text = cartItem.productType.type,
                    modifier = Modifier.padding(horizontal = 4.dp), color = Color.White,
                    fontSize = 12.sp,
                    lineHeight = 6.sp
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BookCoverImage(
                    modifier = Modifier
                        .zIndex(5f)
                        .height(140.dp)
                        .clickable { goToBookDetail(cartItem.productId) },
                    coverImageUrl = cartItem.coverImage,
                    onClick = { goToBookDetail(cartItem.productId) }
                )
                Column(
                    modifier = Modifier
                        .fillMaxHeight(1f)
                    //.background(Color.Cyan)
                ) {

                    BookTitlePrice(
                        //modifier = modifier,
                        addToFontSize = 4,
                        padTop = 0.dp,
                        maxLine = 4,
                        discPer = cartItem.hardCopyDis,
                        originalPrice = cartItem.price,
                        title = cartItem.name,
                        // rating = bookObj.rate.points
                    )
//                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = cartItem.stockType.text,
                        color = cartItem.stockType.color.copy(alpha = 0.5f),
                        fontStyle = FontStyle.Italic
                    )

                    if (cartItem.productType == ProductType.Book || cartItem.stockType != StockType.OUT_OF_STOCK)
                        CountChanger(
                            updateQuantity = updateQuantity,
                            quantity = cartItem.quantity ?: 0
                        )
                }

            }

            // delete button
            IconButton(
                onClick = {
                    updateQuantity(0)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                //.padding(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .border(
                            width = 1.dp, color = MaterialTheme.colorScheme.error,
                            shape = RoundedCornerShape(50)
                        )
                        .padding(4.dp)
                ) {
                    Icon(
                        Icons.Outlined.Delete, "delete icon",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

        }
    }
}

@Composable
fun CountChanger(
    modifier: Modifier = Modifier,
    updateQuantity: (Int) -> Unit,
    quantity: Int
) {
    var count by remember { mutableIntStateOf(quantity) }
    Row(
        modifier = Modifier
            .width(64.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // decrease btn
        Icon(
            modifier = Modifier
                .clickable {
                    updateQuantity(--count)
                }
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(50)
                )
                .size(18.dp),
            imageVector = ImageVector.vectorResource(R.drawable.remove),
            contentDescription = "remove",
            tint = MaterialTheme.colorScheme.onPrimary
        )

        Text("$count")
        Icon(
            modifier = Modifier
                .clickable {
                    updateQuantity(++count)
                }
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(50)
                )
                .size(18.dp),
            imageVector = Icons.Filled.Add,
            contentDescription = "remove",
            tint = MaterialTheme.colorScheme.onPrimary
        )

    }
}