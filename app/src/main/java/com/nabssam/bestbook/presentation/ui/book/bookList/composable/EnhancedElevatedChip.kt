package com.nabssam.bestbook.presentation.ui.book.bookList.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EnhancedElevatedChip(
    modifier: Modifier,
    examList: List<String>,
    onClick: (String) -> Unit,
) {
    //var selectedTargetExam by rememberSaveable { mutableIntStateOf(-1) }
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(examList) { index, exam ->
            ElevatedFilterChip(
                selected = /*selectedTargetExam == index*/false,
                label = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(exam)
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "delete category filter", modifier = Modifier.size(18.dp))
                    }
                },
                onClick = {
                    onClick(exam)
                    /*if (selectedTargetExam == index) {
                        selectedTargetExam = -1
                        onClick(null)
                    } else {
                        selectedTargetExam = index
                        onClick(exam)
                    }*/
                }
            )
        }
    }
}