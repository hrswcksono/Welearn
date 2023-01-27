package com.tugasakhir.welearn.core.utils

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.util.Base64
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.domain.entity.UserPaticipantMulti
import com.tugasakhir.welearn.presentation.view.UserParticipantAdapter
import darren.googlecloudtts.GoogleCloudTTSFactory
import darren.googlecloudtts.parameter.AudioConfig
import darren.googlecloudtts.parameter.AudioEncoding
import darren.googlecloudtts.parameter.VoiceSelectionParams
import java.io.ByteArrayOutputStream

object Template {
    private lateinit var dialogBox: Dialog

    fun listUser(data: List<UserPaticipantMulti>, context: Context) {
        this.dialogBox = Dialog(context)
        this.dialogBox.setContentView(R.layout.dialog_list_user_participant)
        val userAdapter = UserParticipantAdapter()
        val userRc: RecyclerView = this.dialogBox.findViewById(R.id.rv_user_participant)
        userRc.layoutManager = LinearLayoutManager(context)
        userRc.adapter = userAdapter
        userAdapter.setData(data)
        dialogBox.show()
    }

    fun encodeImage(bm: Bitmap): String {
        val imgBitmap = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, imgBitmap)
        val b = imgBitmap.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun speak(string: String) {
        var audioString: String = string
        if(string.contains("+", ignoreCase = true)){
            audioString = string.replace("+", "ditambah")
        }else if (string.contains("-", ignoreCase = true)){
            audioString = string.replace("-", "dikurangi")
        }
        // Set the ApiKey and create GoogleCloudTTS.
        val googleCloudTTS = GoogleCloudTTSFactory.create(Constants.GOOGLE_API_KEY)
        googleCloudTTS.setVoiceSelectionParams(VoiceSelectionParams( "id-ID", "id-ID-Standard-A"))
            .setAudioConfig(AudioConfig(AudioEncoding.MP3, 1f , 10f))
        // start speak
        googleCloudTTS.start(audioString)
    }

//    @Suppress("DEPRECATION")
//    fun saveMediaToStorage(bitmap: Bitmap, context: Context, name: String) {
//        //Generating a file name
//        val filename = "${name}.jpg"
//
//        //Output stream
//        var fos: OutputStream? = null
//
//        //For devices running android >= Q
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            //getting the contentResolver
//            context.contentResolver?.also { resolver ->
//
//                //Content resolver will process the contentvalues
//                val contentValues = ContentValues().apply {
//
//                    //putting file information in content values
//                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
//                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
//                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
//                }
//
//                //Inserting the contentValues to contentResolver and getting the Uri
//                val imageUri: Uri? =
//                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
//
//                //Opening an outputstream with the Uri that we got
//                fos = imageUri?.let { resolver.openOutputStream(it) }
//            }
//        } else {
//            //These for devices running on android < Q
//            //So I don't think an explanation is needed here
//            val imagesDir =
//                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//            val image = File(imagesDir, filename)
//            fos = FileOutputStream(image)
//        }
//
//        fos?.use {
//            //Finally writing the bitmap to the output stream that we opened
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
//            Toast.makeText(context, "Saved Photos", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    fun getDateTime() : String {
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm", Locale("id", "ID"))
//        return LocalDateTime.now().format(formatter)
//    }

    fun provideSharedPref(app: Application): SharedPreferences {
        return app.applicationContext.getSharedPreferences(
            "KEY_LOGIN",
            Context.MODE_PRIVATE
        )
    }
}