package com.nabssam.bestbook.presentation.ui.account.auth.composable.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.presentation.ui.account.auth.AuthEvent
import com.nabssam.bestbook.presentation.ui.account.auth.AuthState
import java.util.Calendar

@Composable
fun YearSelector(state: AuthState, onEvent: (AuthEvent) -> Unit) {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val years = (currentYear..currentYear + 5).toList()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("Target\nYear:")
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(years.size) { index ->
                val year = years[index]
                Surface(
                    modifier = Modifier.clickable {
                        onEvent(AuthEvent.UpdateTargetYear(year.toString()))
                    },
                    color = if (year == state.targetYear) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surface
                    },
                    shape = MaterialTheme.shapes.medium,
                ) {
                    Text(
                        text = year.toString(),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        color = if (year == state.targetYear) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                        fontWeight = if (year == state.targetYear) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
    }