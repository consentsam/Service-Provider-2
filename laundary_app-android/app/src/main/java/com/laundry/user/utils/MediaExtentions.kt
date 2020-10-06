package com.laundry.user.utils


import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.*
import java.nio.charset.Charset

fun AppCompatActivity.getImageUri(inImage: Bitmap): Uri {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(
        this.contentResolver, inImage,
        "Title", null
    )
    return Uri.parse(path)
}

fun compressImageFile(context: Context?, pathUri: Uri): File {
    var b: Bitmap? = null

    val realPath: String? = getRealPathFromURI(context!!, pathUri)//FileUtils().getUriRealPath(context!!, pathUri)
    val f: File = File(realPath!!)

    //Decode image size
    val o: BitmapFactory.Options = BitmapFactory.Options();
    o.inJustDecodeBounds = true;

    var fis: FileInputStream
    try {
        fis = FileInputStream(f);
        BitmapFactory.decodeStream(fis, null, o);
        fis.close();
    } catch (e: FileNotFoundException) {
        e.printStackTrace();
    } catch (e: IOException) {
        e.printStackTrace();
    }

    val IMAGE_MAX_SIZE = 1024;
    var scale = 1;
    if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
        scale = Math.pow(
            2.0,
            Math.ceil(
                Math.log(
                    IMAGE_MAX_SIZE / Math.max(
                        o.outHeight,
                        o.outWidth
                    ).toDouble()
                ) / Math.log(0.5)
            )
        ).toInt();
    }

    //Decode with inSampleSize
    val o2: BitmapFactory.Options = BitmapFactory.Options();
    o2.inSampleSize = scale;
    try {
        fis = FileInputStream(f);
        b = BitmapFactory.decodeStream(fis, null, o2);
        fis.close();
    } catch (e: FileNotFoundException) {
        e.printStackTrace();
    } catch (e: IOException) {
        e.printStackTrace();
    }

    val destFile = File(getImageFilePath());
    try {
        var out: FileOutputStream = FileOutputStream(destFile);
        b?.compress(Bitmap.CompressFormat.PNG, 90, out);
        out.flush();
        out.close();

    } catch (e: Exception) {
        e.printStackTrace();
    }
    return destFile;
}
fun getImageFilePath(): String {
    val file =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/CareerPortalApp");
    if (!file.exists()) {
        file.mkdirs()
    }
    return file.getAbsolutePath() + "/IMG_" + System.currentTimeMillis() + ".jpg"
}
fun getRealPathFromURI(context: Context, contentUri: Uri?): String? {
    var contentUri = contentUri
    var cursor: Cursor?
    var filePath: String? = ""
    if (contentUri == null)
        return filePath

    val file = File(contentUri.path!!)
    if (file.exists())
        filePath = file.getPath()
    if (!TextUtils.isEmpty(filePath))
        return filePath
    val proj = arrayOf(MediaStore.Images.Media.DATA)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        try {
            val wholeID = DocumentsContract.getDocumentId(contentUri)
            // Split at colon, use second item in the array
            //                String[] split = wholeID.split(":");
            val id: String
            if (wholeID.contains(":"))
                id =
                    wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
            else
                id = wholeID
            //                if (split.length > 1)
            //                    id = split[1];
            //                else id = wholeID;
            // where id is equal to
            cursor = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                proj,
                MediaStore.Images.Media._ID + "='" + id + "'",
                null,
                null
            )
            if (cursor != null) {
                val columnIndex = cursor.getColumnIndex(proj[0])
                if (cursor.moveToFirst())
                    filePath = cursor.getString(columnIndex)
                if (!TextUtils.isEmpty(filePath))
                    contentUri = Uri.parse(filePath)
            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }

    }
    if (!TextUtils.isEmpty(filePath))
        return filePath
    try {
        cursor = context.contentResolver.query(contentUri!!, proj, null, null, null)
        if (cursor == null)
            return contentUri.path
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        if (cursor.moveToFirst())
            filePath = cursor.getString(column_index)
        if (!cursor.isClosed)
            cursor.close()
    } catch (e: Exception) {
        e.printStackTrace()
        filePath = contentUri!!.path
    }

    if (filePath == null)
        filePath = ""
    return filePath
}

fun String.toRequestBody(contentType: MediaType? = null): RequestBody {
    var charset: Charset = Charsets.UTF_8
    var finalContentType: MediaType? = contentType
    if (contentType != null) {
        val resolvedCharset = contentType.charset()
        if (resolvedCharset == null) {
            charset = Charsets.UTF_8
            finalContentType = "$contentType; charset=utf-8".toMediaTypeOrNull()
        } else {
            charset = resolvedCharset
        }
    }
    val bytes = toByteArray(charset)
    return bytes.toRequestBody(finalContentType, 0, bytes.size)
}


fun getVideoThumbnails(context: Context, videoUri: Uri): java.util.ArrayList<Bitmap> {

    val thumbnailList = java.util.ArrayList<Bitmap>()

    try {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(context, videoUri)

        // Retrieve media data
        val videoLengthInMs = (Integer.parseInt(
            mediaMetadataRetriever.extractMetadata(
                MediaMetadataRetriever.METADATA_KEY_DURATION
            )
        ) * 1000).toLong()

        // Set thumbnail properties (Thumbs are squares)
        val thumbWidth = 512
        val thumbHeight = 512

        val numThumbs = 10

        val interval = videoLengthInMs / numThumbs

        for (i in 0 until numThumbs) {
            var bitmap = mediaMetadataRetriever.getFrameAtTime(i * interval, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
            try {
                bitmap = Bitmap.createScaledBitmap(bitmap, thumbWidth, thumbHeight, false)
            } catch (e: Exception) {
                e.printStackTrace()
                return thumbnailList
            }
            thumbnailList.add(bitmap)
        }

        mediaMetadataRetriever.release()
    }catch (e: Exception) {
        e.printStackTrace()
    }

    return thumbnailList
}

@Throws(Throwable::class)
fun retriveVideoFrameFromVideo(videoPath: String): Bitmap? {
    var bitmap: Bitmap? = null
    var mediaMetadataRetriever: MediaMetadataRetriever? = null
    try {
        mediaMetadataRetriever = MediaMetadataRetriever()
        if (Build.VERSION.SDK_INT >= 14)
            mediaMetadataRetriever.setDataSource(videoPath, java.util.HashMap())
        else
            mediaMetadataRetriever.setDataSource(videoPath)
        //   mediaMetadataRetriever.setDataSource(videoPath);
        bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST)
    } catch (e: Exception) {
        e.printStackTrace()
        throw Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.message)
    } finally {
        mediaMetadataRetriever?.release()
    }
    return bitmap
}


/*
fun ImageView.loadImage(imageUrl: Any?,
                        errorResId: Int?) {
    val imageView = this

    if (imageUrl == null ) return
    if (errorResId == null) return

    GlideApp.with(context)
        .asBitmap()
        .placeholder(errorResId)
        .error(errorResId)
        .load(imageUrl)
        .into(imageView)
}*/
