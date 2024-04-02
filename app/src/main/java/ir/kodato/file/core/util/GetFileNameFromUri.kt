package ir.kodato.file.core.util

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

fun getFileNameFromUri(context: Context, uri: Uri): String? {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (cursor.moveToFirst()) {
            val displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            return if (displayNameIndex >= 0) {
                cursor.getString(displayNameIndex)
            } else {
                uri.lastPathSegment
            }
        }
    }
    return null
}