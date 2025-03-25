import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.ParcelFileDescriptor
import android.util.Log

import java.io.File
import java.io.FileOutputStream
import androidx.core.graphics.createBitmap

object PDFWatermarkHelper {
    /**
     * Adds a watermark to an existing PDF file **without using any external library**.
     * @param context Application context
     * @param inputFile Source PDF file
     * @param outputFile Destination PDF file with watermark
     * @param watermarkText The watermark text
     * @param fontSize Font size for the watermark text
     */
    fun addWatermark(
        context: Context,
        inputFile: File,
        outputFile: File,
        watermarkText: String = "Watermark",
        fontSize: Float = 70f
    ) {
        try {
            // Ensure output directory exists
            outputFile.parentFile?.mkdirs()

            // Open the input PDF file
            val fileDescriptor = ParcelFileDescriptor.open(inputFile, ParcelFileDescriptor.MODE_READ_ONLY)
            val pdfRenderer = android.graphics.pdf.PdfRenderer(fileDescriptor)

            // Create a new PDF document for output
            val pdfDocument = android.graphics.pdf.PdfDocument()
            val paint = Paint()
            paint.color = Color.RED
            paint.alpha = 50 // Adjust transparency
            paint.textSize = fontSize
            paint.textAlign = Paint.Align.CENTER
            paint.isAntiAlias = true

            val pageCount = pdfRenderer.pageCount
            for (i in 0 until pageCount) {
                Log.d("PDFWatermarkHelper", "Processing page $i of $pageCount")
                val page = pdfRenderer.openPage(i)
                val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                canvas.drawColor(Color.WHITE) // Background (optional)

                // Render the existing PDF page onto the bitmap
                page.render(bitmap, null, null, android.graphics.pdf.PdfRenderer.Page.RENDER_MODE_FOR_PRINT)
                page.close()

                // Draw the watermark in the center
                val x = bitmap.width / 2f
                val y = bitmap.height / 2f
                canvas.rotate(-45f, x, y) // Rotate watermark
                canvas.drawText(watermarkText, x, y, paint)

                // Create a new page in the output PDF document
                val pdfPage = pdfDocument.startPage(PdfDocument.PageInfo.Builder(page.width, page.height, i + 1).create())
                val outputCanvas = pdfPage.canvas
                outputCanvas.drawBitmap(bitmap, 0f, 0f, null)
                pdfDocument.finishPage(pdfPage)

                bitmap.recycle()
            }

            // Save the final output PDF
            FileOutputStream(outputFile).use { outputStream ->
                pdfDocument.writeTo(outputStream)
            }

            // Close resources
            pdfDocument.close()
            pdfRenderer.close()
            fileDescriptor.close()

        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("Failed to add watermark to PDF", e)
        }
    }
}
