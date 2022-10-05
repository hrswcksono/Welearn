package com.tugasakhir.welearn.core.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tugasakhir.welearn.R
import com.tugasakhir.welearn.domain.entity.UserPaticipantEntity
import com.tugasakhir.welearn.presentation.ui.UserParticipantAdapter
import java.io.ByteArrayOutputStream

object Template {
    private lateinit var dialogBox: Dialog

    fun listUser(data: List<UserPaticipantEntity>, context: Context) {
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
}