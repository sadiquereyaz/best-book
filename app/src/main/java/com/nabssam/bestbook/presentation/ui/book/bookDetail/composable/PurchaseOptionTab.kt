package com.nabssam.bestbook.presentation.ui.book.bookDetail.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle

@Composable
fun BookPurchaseOptionsTab(
    modifier: Modifier = Modifier,
    paperbackPrice: Double,
    paperbackDiscount: Double,
    ebookPrice: Double,
    ebookDiscount: Double,
    onPurchaseTypeSelected: (Boolean) -> Unit // true for ebook, false for paperback
) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        // Tab Row
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            // Paperback Tab
            Tab(
                selected = selectedTabIndex == 0,
                onClick = {
                    selectedTabIndex = 0
                    onPurchaseTypeSelected(false)
                }
            ) {
                PurchaseOptionTabContent(
                    title = "Paperback",
                    discount = paperbackDiscount,
                    price = paperbackPrice,
                    isSelected = selectedTabIndex == 0
                )
            }

            // Ebook/Quiz Tab
            Tab(
                selected = selectedTabIndex == 1,
                onClick = {
                    selectedTabIndex = 1
                    onPurchaseTypeSelected(true)
                }
            ) {
                PurchaseOptionTabContent(
                    title = "Quiz/Ebook",
                    discount = ebookDiscount,
                    price = ebookPrice,
                    isSelected = selectedTabIndex == 1
                )
            }
        }
    }
}

@Composable
private fun PurchaseOptionTabContent(
    title: String,
    discount: Double,
    price: Double,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontStyle = FontStyle.Italic,
                color = if (isSelected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        )
        if (discount > 0) {
            Text(
                text = "${discount.toInt()}% OFF",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
        Text(
            text = "₹${price}",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = if (isSelected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

