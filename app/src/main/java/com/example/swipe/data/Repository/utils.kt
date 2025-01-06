package com.example.swipe.data.Repository

import android.content.Context
import android.graphics.Bitmap
import java.io.File

 fun createSquareBitmap(bitmap: Bitmap): Bitmap {
    val size = minOf(bitmap.width, bitmap.height)
    return Bitmap.createBitmap(
        bitmap,
        (bitmap.width - size) / 2,
        (bitmap.height - size) / 2,
        size,
        size
    )
}

 fun createTempFile(context: Context, bitmap: Bitmap): File {
    val file = File(context.cacheDir, "image_${System.currentTimeMillis()}.jpg")
    file.outputStream().use { out ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
    }
    return file
}