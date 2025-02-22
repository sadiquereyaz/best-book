package com.nabssam.bestbook.presentation.ui.order.all

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nabssam.bestbook.R

data class Order(
    val id: String,
    val status: OrderStatus,
    val date: String,
    val items: List<OrderItem>
)

data class OrderItem(
    val id: String,
    val name: String,
    val description: String,
    val size: String,
    val imageUrl: String,
    val price: Double,
    val orderDate: String = "12/11/2021"
)

enum class OrderStatus {
    PENDING, CANCELLED, DELIVERED, PROCESSING, SHIPPED
}

@Composable
fun OrderListScreen(
    orders: List<Order>,
    onOrderClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(orders) { order ->
            OrderCard(
                order = order,
                onClick = { onOrderClick(order.id) },
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun OrderCard(
    order: Order,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)),
    ) {
        Column(modifier = Modifier) {
            // Order Status
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    modifier = Modifier.size(18.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.clock),
                        contentDescription = "order status",
                        tint = when (order.status) {
                            OrderStatus.CANCELLED -> Color.Red.copy(alpha = 0.7f)
                            OrderStatus.DELIVERED -> Color.Green.copy(alpha = 0.7f)
                            OrderStatus.PROCESSING -> Color.Blue.copy(alpha = 0.7f)
                            OrderStatus.SHIPPED -> Color.Yellow.copy(alpha = 0.7f)
                            OrderStatus.PENDING -> Color.Gray.copy(alpha = 0.7f)
                        },
                    )
                }
                Text(
                    text = order.status.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontStyle = FontStyle.Italic,
                    modifier =  Modifier.padding(horizontal = 4.dp)
                )
            }

            HorizontalDivider()
            // Order Items
            order.items.forEach { item ->
                OrderItemRow(
                    item = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )
            }
        }
    }
}

@Composable
private fun OrderItemRow(
    item: OrderItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
            ,
                contentScale = ContentScale.Fit
            )

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {

                Text(
                    text = item.name,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
                Text(
                    text = "Rs.${item.price}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Order date: ${ item.orderDate }",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}