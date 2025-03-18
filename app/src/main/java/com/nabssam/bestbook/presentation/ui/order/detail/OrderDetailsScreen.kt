package com.nabssam.bestbook.presentation.ui.order.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MailOutline
import com.nabssam.bestbook.presentation.ui.components.GradientButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import com.nabssam.bestbook.presentation.ui.components.TranslucentLoader
import com.nabssam.bestbook.presentation.ui.order.detail.composable.ChatBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailsScreen(
    modifier: Modifier = Modifier,
    navigateToProduct: (String) -> Unit,
    state: UiStateOrderDetail,
    onEvent: (EventOrderDetail) -> Unit
) {
    when (val currentState = state) {
        is UiStateOrderDetail.Success -> {
            val order = currentState.orderDetail
            var reviewField by rememberSaveable {
                mutableStateOf(
                    order.userReview?.comment ?: ""
                )
            }
            var rate: Int? by remember { mutableStateOf(order.userReview?.rating) }
            val scrollState = rememberScrollState(0)
            var showChat by remember { mutableStateOf(false) }
            var messageText by remember { mutableStateOf("") }
            val sheetState = rememberModalBottomSheetState()

            val messages = remember {
                mutableStateListOf(
                    ChatMessage("978", "Hello! How can I help you today?", false, "10:00 AM"),
                    ChatMessage("i9u", "I have a question about my order", true, "10:01 AM"),
                    ChatMessage("09hkl", "Sure, I'll be happy to help. What's your concern?", false, "10:02 AM")
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                // Order ID
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "Order ID - ${order.orderId}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Product Details
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = order.productName,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )


                        Text(
                            text = "Seller: ${order.seller}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            text = "₹${order.price}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    AsyncImage(
                        model = order.image,
                        contentDescription = "Product Image",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Order Timeline
                Surface(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    OrderTimeline()
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Return Policy
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "Return policy valid till ${order.returnBefore}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        shape = RoundedCornerShape(8.dp),
                        onClick = { },
                        modifier = Modifier
                            .weight(1f)
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.primaryContainer,
                                RoundedCornerShape(8.dp)
                            )
                    ) {
                        Text("Return")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    TextButton(
                        shape = RoundedCornerShape(8.dp),
                        onClick = {
                            showChat = true
                            onEvent(EventOrderDetail.ChatClicked)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.primaryContainer,
                                RoundedCornerShape(8.dp)
                            )
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.MailOutline,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Send your query")
                        }
                    }
                }
//REVIEW
                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "Rate this book",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(5) { it ->
                        IconButton(onClick = { rate = it + 1 }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    if ((rate ?: 0) > it)
                                        R.drawable.star_filled
                                    else
                                        R.drawable.star
                                ),
                                contentDescription = "Rating Star",
                                modifier = Modifier.size(32.dp),
                                tint = if (it < (rate ?: 0)
                                ) MaterialTheme.colorScheme.primary else Color.Gray
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                rate?.let {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        value = reviewField,
                        onValueChange = { reviewField = it },
                        minLines = 3,
                        label = { Text("Write a review") }
                    )

                    GradientButton(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp),
                        onClick = {},
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "Submit",
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )
                    }
                }
            }
            if (showChat) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showChat = false
                    },
                    sheetState = sheetState,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 16.dp,
                    dragHandle = {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .width(50.dp)
                                .height(6.dp)
                                .clip(RoundedCornerShape(50))
                                .background(MaterialTheme.colorScheme.primary)
                            //.align(Alignment.Center)
                        )
                    }
                ) {
                    ChatBottomSheet(
                        messages = messages
//                        currentState.chatState.messages
                        ,
                        message = messageText,
                        onMessageChange = { messageText = it },
                        onSendClick = {
                            if (messageText.isNotBlank()) {
                                onEvent(EventOrderDetail.SendMessage(messageText))
                                messageText = ""
                            }
                        },
                        onRetryClick = { messageId ->
                            onEvent(EventOrderDetail.RetryMessage(messageId))
                        },
                        error = currentState.chatState.error,
                        onDismissError = {
                            onEvent(EventOrderDetail.DismissError)
                        }
                    )
                }
            }

        }

        UiStateOrderDetail.Loading -> TranslucentLoader(
            modifier = modifier,
            "Loading Order Details"
        )

        UiStateOrderDetail.Error -> ErrorScreen(
            modifier,
            "Error Loading Order Details"
        ) { onEvent(EventOrderDetail.Initialise) }

    }

}


@Composable
private fun OrderTimeline(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        TimelineItem(
            text = "Order Confirmed",
            subText = "Wed, 1 Aug 2019, 4:49 PM",
            status = TimelineStatus.COMPLETED
        )
        TimelineItem(
            text = "Order Inprocess",
            subText = "Wed, 1 Aug 2019, 4:49 PM",
            status = TimelineStatus.COMPLETED
        )
        TimelineItem(
            text = "Order Processed",
            subText = "",
            status = TimelineStatus.PENDING
        )
        TimelineItem(
            text = "Order Shipped",
            subText = "",
            status = TimelineStatus.PENDING
        )
        TimelineItem(
            text = "Order Delivered",
            subText = "",
            status = TimelineStatus.PENDING,
            isLast = true
        )
    }
}

enum class TimelineStatus {
    COMPLETED, PENDING
}

@Composable
private fun TimelineItem(
    text: String,
    subText: String,
    status: TimelineStatus,
    isLast: Boolean = false
) {
    Row(
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(24.dp)
        ) {
            // Timeline dot
            Surface(
                modifier = Modifier.size(12.dp),
                shape = CircleShape,
                color = when (status) {
                    TimelineStatus.COMPLETED -> Color.Green
                    TimelineStatus.PENDING -> Color.Gray.copy(alpha = 0.5f)
                }
            ) {
                if (status == TimelineStatus.COMPLETED) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            modifier = Modifier.size(8.dp),
                            shape = CircleShape,
                            color = Color.Green
                        ) { }
                    }
                }
            }
            // Timeline line
            if (!isLast) {
                Spacer(
                    modifier = Modifier
                        .width(2.dp)
                        .fillMaxHeight()
                        .background(
                            when (status) {
                                TimelineStatus.COMPLETED -> Color.Green
                                TimelineStatus.PENDING -> Color.Gray.copy(alpha = 0.5f)
                            }
                        )
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.padding(bottom = if (isLast) 0.dp else 24.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = when (status) {
                    TimelineStatus.COMPLETED -> MaterialTheme.colorScheme.onSurface
                    TimelineStatus.PENDING -> Color.Gray
                }
            )
            if (subText.isNotEmpty()) {
                Text(
                    text = subText,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}

