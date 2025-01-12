package com.nabssam.bestbook.presentation.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nabssam.bestbook.domain.model.Exam

@Composable
fun QuizCard(exam: Exam, color:CardColors, onQuizSelect: () -> Unit) {
    Box(

        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clickable { onQuizSelect() }
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(6.dp),
                color = color.borderColor
            )
            .background(color = color.backgroundColor, shape = RoundedCornerShape(6.dp))
            .aspectRatio(2f)
    ) {
        Text(
            text = exam.name,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(4.dp),
            color = color.onBackgroundColor
        )
    }
}