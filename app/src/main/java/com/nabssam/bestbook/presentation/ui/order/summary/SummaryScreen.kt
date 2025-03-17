package com.nabssam.bestbook.presentation.ui.order.all

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import com.nabssam.bestbook.presentation.ui.components.GradientButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.PhonePeActivity

@Composable
fun SummaryScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    GradientButton(
        onClick = {
            val intent =
                Intent(context, PhonePeActivity::class.java).apply {
                    putExtra("pdf_path", "pdfFile.absolutePath")
                }
            context.startActivity(intent)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
    ) {
        Text("Pay Now")
    }
}
