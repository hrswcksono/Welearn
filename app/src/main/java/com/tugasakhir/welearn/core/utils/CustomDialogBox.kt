package com.tugasakhir.welearn.core.utils

import android.app.Activity
import android.content.Context
import android.view.Gravity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.awesomedialog.*
import com.example.awesomedialog.R
import com.thecode.aestheticdialogs.*

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

    fun dialogPredict(context: Context, function: ()->Unit, result: Int, duration: Int) {
        var dialogType = DialogType.SUCCESS
        var title = "Benar"
        var message = "Jawaban anda benar"
        if (result == 0) {
            dialogType = DialogType.ERROR
            message = "Jawaban anda salah"
            title = "salah"
        }
        AestheticDialog.Builder(context as Activity, DialogStyle.TOASTER, dialogType)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setDarkMode(false)
            .setDuration(duration)
            .setGravity(Gravity.TOP)
            .setAnimation(DialogAnimation.SHRINK)
            .setOnClickListener(object : OnDialogClickListener {
                override fun onClick(dialog: AestheticDialog.Builder) {
                    dialog.dismiss()
                    function()
                }
            })
            .show()
    }
}