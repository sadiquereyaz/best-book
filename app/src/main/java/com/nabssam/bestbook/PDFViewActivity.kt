package com.nabssam.bestbook

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import com.nabssam.bestbook.presentation.ui.book.ebook.PDFEncryptionHelper
import java.io.File

class PDFViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pdfView = PDFView(this, null)
        setContentView(pdfView)

        val pdfPath = intent.getStringExtra("pdf_path")
        Log.d("PDFViewActivity", "Received PDF path: $pdfPath")

        if (pdfPath != null) {
            val encryptedFile = File(pdfPath)
            Log.d("PDFViewActivity", "Encrypted file exists: ${encryptedFile.exists()}")

            if (encryptedFile.exists()) {
                // Decrypt the PDF to a temporary file
                val tempFile = File(cacheDir, "temp_decrypted_${System.currentTimeMillis()}.pdf")
                try {
                    PDFEncryptionHelper.decryptPDF(this, encryptedFile, tempFile)
                    Log.d("PDFViewActivity", "Decrypted PDF saved at: ${tempFile.absolutePath}")

                    // Load the decrypted PDF into the PDFView
                    pdfView.fromFile(tempFile)
                        .enableSwipe(true)
                        .swipeHorizontal(true)
                        .enableDoubletap(true)
                        .defaultPage(0)
                        .fitEachPage(true)
                        .nightMode(true)
                        .load()

                    // Delete the temporary decrypted file after loading
                    tempFile.deleteOnExit()
                } catch (e: Exception) {
                    Log.e("PDFViewActivity", "Decryption failed: ${e.message}")
                    finish() // Close the activity if decryption fails
                }
            } else {
                Log.e("PDFViewActivity", "Encrypted PDF file not found at: $pdfPath")
                finish() // Close the activity if the file doesn't exist
            }
        } else {
            Log.e("PDFViewActivity", "No PDF path provided")
            finish() // Close the activity if no PDF path is provided
        }
    }
}