package com.nabssam.bestbook.data.repository

import android.content.Context
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import java.io.File
import java.security.MessageDigest
import javax.inject.Inject

class PdfEncryptionManager @Inject constructor(
    private val context: Context
) {
    private val masterKey by lazy {
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    fun encryptAndSave(fileName: String, pdfData: ByteArray): File {
        val file = File(context.filesDir, "pdfs/${generateSecureFileName(fileName)}")
        file.parentFile?.mkdirs()

        EncryptedFile.Builder(
            context,
            file,
            masterKey,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build().apply {
            openFileOutput().use { output ->
                output.write(pdfData)
            }
        }
        return file
    }

    fun readEncryptedFile(file: File): ByteArray {
        return EncryptedFile.Builder(
            context,
            file,
            masterKey,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build().openFileInput().use { it.readBytes() }
    }

    private fun generateSecureFileName(originalName: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        return digest.digest("${originalName}_${System.currentTimeMillis()}".toByteArray())
            .joinToString("") { "%02x".format(it) } + ".pdf.enc"
    }
}