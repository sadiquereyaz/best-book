package com.nabssam.bestbook.presentation.ui.book.bookList.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.book.bookList.EventBookList
import com.nabssam.bestbook.presentation.ui.book.bookList.StateBookList
import com.nabssam.bestbook.presentation.ui.components.gradientBrush

@Composable
fun SearchAndFilter(
    state: StateBookList,
    onEvent: (EventBookList) -> Unit,
    focusManager: FocusManager,
    showFilterSheet: (Boolean) -> Unit
) {
    val filterBtnModifier = if (state.isFilterApplied) Modifier.border(3.dp, gradientBrush(), CircleShape) else Modifier

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp, 0.dp, 12.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Enhanced SearchBar
        EnhancedSearchBar(
            modifier = Modifier.weight(1f),
            query = state.searchQuery,
            onQueryChange = { onEvent(EventBookList.UpdateSearchQuery(it)) },
            focusManager = focusManager
        )

        // Filter button
        IconButton(
            modifier = filterBtnModifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .size(TextFieldDefaults.MinHeight),
            onClick = {
                focusManager.clearFocus()
                showFilterSheet(true)
            }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.sort_icon),
                contentDescription = "Filter books"
            )
        }
    }

    // Selected category chips
    AnimatedVisibility(
        visible = state.selectedCategories.isNotEmpty(),
        enter = fadeIn(animationSpec = tween(500)) + slideInVertically(),
        exit = fadeOut(animationSpec = tween(500)) + slideOutVertically()
    ) {
        EnhancedElevatedChip(
            modifier = Modifier.padding(16.dp, 0.dp),
            examList = state.selectedCategories.toList(),
            onClick = { category ->
                onEvent(EventBookList.ToggleCategory(category))
            }
        )
        //FilterChip() { }
    }
}