package com.blueamber.studentcalendar.tools

import android.content.Context
import android.util.Log
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream

object FileUtil {

    fun writeResponseBodyToDisk(
        context: Context,
        body: ResponseBody?,
        filename: String,
        extension: String? = null
    ): File {

        val file = File.createTempFile(filename, extension, context.cacheDir)
        val fileReader = ByteArray(4096)
        val fileSize = body?.contentLength()
        var fileSizeDownloaded: Long = 0
        val inputStream = body?.byteStream()
        val outputStream = FileOutputStream(file)

        while (true) {
            val read = inputStream?.read(fileReader)
            if (read == -1) {
                break
            }
            outputStream.write(fileReader, 0, read ?: 0)
            fileSizeDownloaded += read?.toLong() ?: 0
            Log.d("SAVE_FILE", "file download: $fileSizeDownloaded of $fileSize")
        }
        outputStream.flush()

        inputStream?.close()
        outputStream.close()

        return file
    }
}