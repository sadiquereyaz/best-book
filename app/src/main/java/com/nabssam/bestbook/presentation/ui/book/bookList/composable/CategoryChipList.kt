package com.nabssam.bestbook.presentation.ui.book.bookList.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.domain.model.Category

@Composable
fun CategoryChipList(
    examList: List<Category>,
    onClick: (String?) -> Unit,
) {
    var selectedTargetExam by rememberSaveable { mutableIntStateOf(-1) }
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(examList) { index, item ->
            FilterChip(
                selected = selectedTargetExam == index,
                label = { Text(item.name) },
                onClick = {
                    if (selectedTargetExam == index) {
                        selectedTargetExam = -1
                        onClick(null)
                    } else {
                        selectedTargetExam = index
                        onClick(item.id)
                    }
                }
            )
        }
    }
}