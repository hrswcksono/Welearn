package com.tugasakhir.welearn.core.utils

import android.content.Context
import cn.pedant.SweetAlert.SweetAlertDialog

object CustomDialogBox {

    fun onlyTitle(context: Context, type: Int, title: String, function: ()->Unit){
        SweetAlertDialog(context, type)
            .setTitleText(title)
            .setConfirmClickListener {
                function()
            }
            .show()
    }

    fun withoutConfirm(context: Context, type: Int, title: String, body: String){
        SweetAlertDialog(context, type)
            .setTitleText(title)
            .setContentText(body)
            .show()
    }

    fun withCancel(context: Context, type: Int, title: String, body: String, confirm: String, function: ()->Unit){
        SweetAlertDialog(context, type)
            .setTitleText(title)
            .setContentText(body)
            .setConfirmText(confirm)
            .setConfirmClickListener {
                function()
            }
            .setCancelButton(
                "Batal"
            ) { sDialog -> sDialog.dismissWithAnimation() }
            .show()
    }
}