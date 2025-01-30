package com.nabssam.bestbook.presentation.ui.book.ebook

import android.content.Context
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import java.io.File

// encrypt the download pdf
object PDFEncryptionHelper {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    fun encryptPDF(context: Context, inputFile: File, outputFile: File) {
        if (!inputFile.exists()) {
            throw Exception("Input file does not exist: ${inputFile.absolutePath}")
        }

        val encryptedFile = EncryptedFile.Builder(
            outputFile,
            context,
            masterKeyAlias,
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

        val encryptedFile = EncryptedFile.Builder(
            encryptedFile,
            context,
            masterKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        encryptedFile.openFileInput().use { inputStream ->
            outputFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }
}