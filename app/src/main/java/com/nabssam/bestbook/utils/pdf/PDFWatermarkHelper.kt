package com.nabssam.bestbook.utils.pdf

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.ParcelFileDescriptor
import java.io.File
import java.io.FileOutputStream

object PDFWatermarkHelper {

    fun addWatermark(
        //context: Context,
        inputFile: File,
        outputFile: File,
        watermarkText: String = "CONFIDENTIAL",
        fontSize: Float = 50f,
        scale: Float = 4f
    ) {
        try {
            // Ensure output directory exists
            outputFile.parentFile?.mkdirs()

            // Open the input PDF file
            val fileDescriptor = ParcelFileDescriptor.open(inputFile, ParcelFileDescriptor.MODE_READ_ONLY)
            val pdfRenderer = android.graphics.pdf.PdfRenderer(fileDescriptor)

            // Create a new PDF document for output
            val pdfDocument = PdfDocument()

            // Configure the watermark paint (vector-based drawing on the bitmap)
            val paint = Paint().apply {
                color = Color.RED  // You may adjust color as needed
                alpha = 50           // Adjust transparency (0-255)
                textSize = fontSize * scale // Increase text size proportionally to scale
                textAlign = Paint.Align.CENTER
                isAntiAlias = true
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }

            val totalPages = pdfRenderer.pageCount
            for (i in 0 until totalPages) {
                val page = pdfRenderer.openPage(i)

                // Original dimensions
                val origWidth = page.width
                val origHeight = page.height

                // High-resolution dimensions
                val highResWidth = (origWidth * scale).toInt()
                val highResHeight = (origHeight * scale).toInt()

                // Create a high-res bitmap
                val bitmap = Bitmap.createBitmap(highResWidth, highResHeight, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                canvas.drawColor(Color.WHITE)

                // Render the PDF page at high resolution using a scale matrix
                val matrix = Matrix().apply { postScale(scale, scale) }
                page.render(bitmap, null, matrix, android.graphics.pdf.PdfRenderer.Page.RENDER_MODE_FOR_PRINT)
                page.close()

                // Draw watermark on the high-res bitmap
                val centerX = highResWidth / 2f
                val centerY = highResHeight / 2f
                canvas.save()
                canvas.rotate(-45f, centerX, centerY)
                canvas.drawText(watermarkText, centerX, centerY, paint)
                canvas.restore()

                // Create a PDF page with the original dimensions
                val pageInfo = PdfDocument.PageInfo.Builder(origWidth, origHeight, i + 1).create()
                val pdfPage = pdfDocument.startPage(pageInfo)
                val pdfCanvas = pdfPage.canvas

                // Draw the high-res bitmap scaled down to the original page size
                val destRect = Rect(0, 0, origWidth, origHeight)
                pdfCanvas.drawBitmap(bitmap, null, destRect, null)
                pdfDocument.finishPage(pdfPage)

                bitmap.recycle()
            }

            // Write the output PDF to file
            FileOutputStream(outputFile).use { pdfDocument.writeTo(it) }

            // Clean up resources
            pdfDocument.close()
            pdfRenderer.close()
            fileDescriptor.close()

        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("Failed to add watermark to PDF", e)
        }
    }
}
