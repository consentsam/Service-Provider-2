package com.laundry.user.utils

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Window
import com.laundry.user.R


import java.lang.Exception


class ProgressDialogUtils {

    private var mDialog: Dialog? = null
    private var dialog: ProgressDialog ?=null

    companion object {
        var progressDialog: ProgressDialogUtils? = null
    }


    fun getInstance(): ProgressDialogUtils {
        if (progressDialog == null) {
            progressDialog = ProgressDialogUtils()
        }
        return progressDialog as ProgressDialogUtils
    }

    fun showProgress(context: Context) {
        try {
            if(mDialog == null) {
                mDialog = Dialog(context)
                mDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                mDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                mDialog?.setContentView(R.layout.dialog_custom_progress_bar)
                mDialog?.setCancelable(false)
                mDialog?.show()
            } else {
                mDialog?.show()
            }
        } catch (e: Exception){
            Log.e("MyExceptionsProgress", e.message)
        }
    }

    fun hideProgress() {
        if (mDialog != null) {
            mDialog?.dismiss()
            mDialog = null
        }
    }


}