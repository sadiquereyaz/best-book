package com.nabssam.bestbook.presentation.ui.cart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.nabssam.bestbook.data.remote.dto.ProductType
import com.nabssam.bestbook.domain.model.CartItem
import com.nabssam.bestbook.domain.model.StockType
import com.nabssam.bestbook.presentation.ui.components.BookCoverImage
import com.nabssam.bestbook.presentation.ui.components.BookTitlePrice

@Composable
fun CartItem(
    modifier: Modifier = Modifier,
    goToBookDetail: (String) -> Unit,
    cartItem: CartItem,
    updateQuantity: (Int) -> Unit
) {
    Card(
        onClick = { goToBookDetail(cartItem.productId) },
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
                        discPer = cartItem.discount,
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
                        CartCounter(
                            updateQuantity = updateQuantity,
                            quantity = cartItem.quantity ?: 0,
                            isLoading = cartItem.isModifyingQuantity
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