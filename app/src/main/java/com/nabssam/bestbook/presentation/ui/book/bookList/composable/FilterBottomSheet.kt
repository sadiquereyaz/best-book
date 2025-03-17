package com.nabssam.bestbook.presentation.ui.book.bookList.composable

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import com.nabssam.bestbook.presentation.ui.components.GradientButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.book.bookList.EventBookList
import com.nabssam.bestbook.presentation.ui.book.bookList.StateBookList

private const val TAG = "FILTER_BOTTOM_SHEET"
// Filter bottom sheet component
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    state: StateBookList,
    onEvent: (EventBookList) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.filter),
                    tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                    contentDescription = "filter icon"
                )
                Text(
                    text = "Filter Books",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = {
                    onEvent(EventBookList.ResetFilters)
                    onDismiss()
                }) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "reset",
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text("Reset")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Categories
            Text(
                text = "Categories",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Log.d(TAG, "Categories: ${state.categories}")
                items(state.categories) { category ->
                    EnhancedFilterChip(
                        selected = category in state.selectedCategories,
                        onClick = { onEvent(EventBookList.ToggleCategory(category)) },
                        label = { Text(category) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Price Range
            Text(
                text = "Price Range: ₹${state.priceRange.start.toInt()} - ₹${state.priceRange.endInclusive.toInt()}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            var sliderPosition by remember { mutableStateOf(state.priceRange) }

            RangeSlider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                onValueChangeFinished = {
                    onEvent(EventBookList.UpdatePriceRange(sliderPosition))
                },
                valueRange = state.minPrice..state.maxPrice,
                steps = 20
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Discount filter
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Show only discounted books",
                    style = MaterialTheme.typography.bodyLarge
                )

                Switch(
                    checked = state.showOnlyDiscounted,
                    onCheckedChange = {
                        onEvent(EventBookList.ToggleDiscountedOnly)
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Apply button
            GradientButton(
                onClick = { onDismiss() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Apply Filters")
            }

            // Add extra padding at the bottom for better UX with bottom sheet
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}