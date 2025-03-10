package com.nrin31266.firebaselab6and7.storage

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import java.io.File

class UploadFileToStorage(){
    private val storage = Firebase.storage;

    suspend fun uploadImage(imageUri: Uri, context: Context): String {
        val fileName = getFileNameFromUri(imageUri, context) ?: "unknown_file"
        val fileExtension = getFileExtensionFromUri(imageUri, context) ?: "jpg"

        val storageRef = storage.reference.child("images/$fileName.$fileExtension")
        storageRef.putFile(imageUri).await()
        return storageRef.downloadUrl.await().toString()
    }

    private fun getFileNameFromUri(uri: Uri, context: Context): String? {
        return if (uri.scheme == "content") {
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    cursor.moveToFirst()
                    return cursor.getString(nameIndex)
                }
            }
            null
        } else {
            uri.path?.substringAfterLast('/')
        }
    }

    private fun getFileExtensionFromUri(uri: Uri, context: Context): String? {
        return getFileNameFromUri(uri, context)?.substringAfterLast('.', "")
    }
}