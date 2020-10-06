package com.laundry.user.utils

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

interface ImageListener {
    fun getImageData(uri: Uri?, bm: Bitmap?, file: File?, type: Int,from:String)
}