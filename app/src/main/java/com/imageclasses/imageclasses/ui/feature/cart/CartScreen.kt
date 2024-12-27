package com.imageclasses.imageclasses.ui.feature.cart

import androidx.collection.mutableIntSetOf
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key.Companion.I
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.imageclasses.imageclasses.R
import com.imageclasses.imageclasses.data.model.BookModel
import com.imageclasses.imageclasses.ui.components.BookTitlePrice
import com.imageclasses.imageclasses.ui.feature.bookList.BookCoverImage
import com.imageclasses.imageclasses.ui.theme.backgroundDark
import com.imageclasses.imageclasses.ui.theme.scrimDark
import com.imageclasses.imageclasses.ui.theme.surfaceDark

@Composable
fun CartScreen(
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { CartItem() }
    }
}

@Composable
fun CartItem(
    modifier: Modifier = Modifier
        .fillMaxSize()

) {
    var count by remember { mutableStateOf(1) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            // .defaultMinSize(minHeight = 300.dp)
            .padding(16.dp)
    ) {
        Box() {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BookCoverImage(Modifier.height(140.dp))
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BookTitlePrice(
                        //modifier = modifier,
                        book = BookModel(),
                        addToFontSize = 4,
                        padTop = 0.dp,
                        maxLine = 4
                    )
                    Row(
                        modifier = Modifier,
                        // .height(16.dp)
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(
                            onClick = { count-- },
                            modifier = Modifier
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(50)
                                    ),

                                ) {
                                Icon(
                                    ImageVector.vectorResource(R.drawable.remove),
                                    contentDescription = "remove",
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                        Text("$count")
                        IconButton(
                            onClick = { count++ },
                            modifier = Modifier
                                //.align(Alignment.End)
                                //.padding(10.dp)
                        ) {
                            Box(
                                modifier = Modifier.background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(50)
                                ),

                                ) {
                                Icon(
                                    Icons.Filled.Add,
                                    "add",
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }

                }
            }
            IconButton(
                onClick = { /*TODO*/ },
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
            /*Text(
                text = "Remove",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(10.dp)
            )*/
        }
    }
}