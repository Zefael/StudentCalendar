package com.enterprise.baseproject.util

import android.content.Context
import android.net.Uri
import java.io.File

object FileUtil {

    fun copyToFile(context: Context, uri: Uri, filename: String, extension: String? = null) : File {
        val file = File.createTempFile(filename, extension, context.cacheDir)

        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = file.outputStream()
        inputStream?.use { `is` ->
            outputStream.use { `is`.copyTo(it) }
        }

        inputStream?.close()
        outputStream.close()

        return file
    }

}