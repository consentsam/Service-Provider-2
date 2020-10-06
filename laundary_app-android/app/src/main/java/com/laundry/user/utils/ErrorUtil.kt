package com.laundry.user.utils

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast


import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorUtil {
    val TAG = ErrorUtil::class.simpleName

    fun handlerGeneralError(context: Context?, throwable: Throwable) {
        Log.e(TAG, "Error: ${throwable.message}")
        throwable.printStackTrace()

        if (context == null) return

        when (throwable) {
            //For Display Toast

            is ConnectException -> Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show()
            is SocketTimeoutException -> Toast.makeText(context, "Connection Lost", Toast.LENGTH_SHORT).show()
            is UnknownHostException -> Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
            is InternalError -> Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()

           /* is HttpException -> {
                try {
                    when (throwable.code()) {
                        401 -> {
                            //Logout
                            forceLogout(context)
                        }
                        403 -> {

                            displayError(context, throwable)
                            forceLogout(context)

                        }
                        else -> {

                            displayError(context, throwable)
                        }
                    }
                } catch (exception: Exception) {

                }
            }*/
            //For Display SnackBar
            /*is HttpException -> {
                try {
                    when (throwable.code()) {
                        401 -> {
                            SnackbarUtils.displayError(view, throwable)
                            //logout(context)
                        }
                        else -> {
                            SnackbarUtils.displayError(view, throwable)
                        }
                    }
                } catch (exception: Exception) {
                    SnackbarUtils.somethingWentWrong(view)
                    exception.printStackTrace()
                }
            }
            is ConnectException -> SnackbarUtils.displayError(view, throwable)
            is SocketTimeoutException -> SnackbarUtils.displayError(view, throwable)
            else -> SnackbarUtils.somethingWentWrong(view)
*/

        }
    }


    /**
     * Perform logout for both the success and error case (force logout)
     */
 /*   private fun forceLogout(context: Context) {
        SharedPreferenceWriter.getInstance(context).clearPreferenceValues()
        val intent = Intent(context, LoginActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }*/

   /* fun displayError(context: Context, exception: HttpException) {
        Log.i(TAG, "displayError()")
        try {
            val errorBody = getGsonInstance().fromJson(
                exception.response().errorBody()?.charStream(),
                ErrorBean::class.java
            )
            Log.e("MyError", errorBody.message)
            //  SnackbarUtils.displaySnackbar(view, errorBody.message)
            Toast.makeText(context, errorBody.message, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(
                context, context.getString(R.string.something_went_wrong),
                Toast.LENGTH_SHORT
            ).show()
        }
    }*/

}