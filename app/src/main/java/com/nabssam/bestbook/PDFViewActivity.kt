package com.nabssam.bestbook

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import java.io.File

class PDFViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pdfView = PDFView(this, null)
        setContentView(pdfView)

        val pdfPath = intent.getStringExtra("pdf_path")
        Log.d("PDFViewActivity", "Received PDF path: $pdfPath")

        if (pdfPath != null) {
            val pdfFile = File(pdfPath)
            Log.d("PDFViewActivity", "File exists: ${pdfFile.exists()}")

            if (pdfFile.exists()) {
                pdfView.fromFile(pdfFile).load()
            } else {
                Log.e("PDFViewActivity", "PDF file not found at: $pdfPath")
                finish() // Close the activity if the file doesn't exist
            }
        } else {
            Log.e("PDFViewActivity", "No PDF path provided")
            finish() // Close the activity if no PDF path is provided
        }
    }
}