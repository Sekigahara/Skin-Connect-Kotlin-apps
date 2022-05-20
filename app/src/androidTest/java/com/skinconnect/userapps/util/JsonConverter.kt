package com.skinconnect.userapps.util

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import java.io.IOException
import java.io.InputStreamReader

object JsonConverter {
    fun readStringFromFile(fileName: String): String {
        try {
            val context = ApplicationProvider.getApplicationContext<Context>()
            val inputStream = context.assets.open(fileName)
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream, "UTF-8")
            reader.readLines().forEach { builder.append(it) }
            return "$builder"
        } catch (exception: IOException) {
            throw exception
        }
    }
}