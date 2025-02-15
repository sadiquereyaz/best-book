package com.nabssam.bestbook.presentation.phonepe

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.nabssam.bestbook.PhonePeActivity

@Composable
fun PhonePeScreen1(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Button(
            onClick = {
                    val intent = Intent(context, PhonePeActivity::class.java).apply {
                        putExtra("pdf_path", "pdfFile.absolutePath")
                    }
                    context.startActivity(intent)
            }

        ) {
            Text(text = "Pay")
        }
    }
}