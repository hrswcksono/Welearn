package com.tugasakhir.welearn.core.utils

import android.app.Activity
import android.content.Context
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.awesomedialog.*
import kotlinx.coroutines.CoroutineScope

object CustomDialogBox {

    fun notifOnly(context: Activity, body: String){
        AwesomeDialog.build(context)
            .title("Sukses")
            .body(body)
            .icon(R.drawable.ic_congrts)
    }

    fun withConfirm(context: Context, type: Int, title: String, body: String, function: ()->Unit){
        SweetAlertDialog(context, type)
            .setTitleText(title)
            .setContentText(body)
            .setConfirmClickListener {
                function()
            }
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