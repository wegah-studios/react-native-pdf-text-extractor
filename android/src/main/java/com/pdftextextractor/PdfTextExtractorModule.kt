package com.pdftextextractor


import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise


import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader


import java.io.File
import java.io.IOException


class PdfTextExtractorModule(private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {


    override fun getName(): String {
        return "PdfTextExtractor"
    }

    @ReactMethod
    fun isPasswordProtected(filePath: String, promise: Promise) {
        try {
            PDFBoxResourceLoader.init(reactContext)

            val file = File(filePath)
            if (!file.exists()) {
                promise.reject("ENOENT", "File not found: $filePath")
                return
            }

            PDDocument.load(file).use { document ->
                promise.resolve(document.isEncrypted)
            }
        } catch (e: IOException) {
            // IOException here usually means "encrypted"
            promise.resolve(true)
        } catch (e: Exception) {
            promise.reject("E_UNKNOWN", e.localizedMessage, e)
        }
    }

    @ReactMethod
    fun extractText(filePath: String, password: String?, promise: Promise) {
        try {
            PDFBoxResourceLoader.init(reactContext)

            val file = File(filePath)
            if (!file.exists()) {
                promise.reject("ENOENT", "File not found: $filePath")
                return
            }

            val document = try {
                if (password.isNullOrEmpty()) {
                    PDDocument.load(file)
                } else {
                    PDDocument.load(file, password)
                }
            } catch (e: IOException) {
                promise.reject("E_INVALID_PASSWORD", "Invalid or missing PDF password", e)
                return
            }

            document.use {
                val stripper = PDFTextStripper()
                val text = stripper.getText(it)
                promise.resolve(text ?: "")
            }

        } catch (e: Exception) {
            promise.reject("E_UNKNOWN", e.localizedMessage, e)
        }
    }
}