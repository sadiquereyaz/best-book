package com.nabssam.bestbook.presentation.ui.book.ebookList
import android.content.Context
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import java.io.File

object PDFEncryptionHelper {

    private fun getMasterKey(context: Context): MasterKey {
        return MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    fun encryptPDF(context: Context, inputFile: File, outputFile: File) {
        if (!inputFile.exists()) {
            throw Exception("Input file does not exist: ${inputFile.absolutePath}")
        }

        val encryptedFile = EncryptedFile.Builder(
            context,
            outputFile,
            getMasterKey(context),
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        inputFile.inputStream().use { inputStream ->
            encryptedFile.openFileOutput().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }

    fun decryptPDF(context: Context, encryptedFile: File, outputFile: File) {
        if (!encryptedFile.exists()) {
            throw Exception("Encrypted file does not exist: ${encryptedFile.absolutePath}")
        }
        val encrypted = EncryptedFile.Builder(
            context,
            encryptedFile,
            getMasterKey(context),
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        encrypted.openFileInput().use { inputStream ->
            outputFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }
}
