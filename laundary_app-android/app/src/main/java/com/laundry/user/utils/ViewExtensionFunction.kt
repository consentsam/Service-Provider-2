package com.laundry.user.utils


import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData

import com.google.gson.Gson
import com.laundry.user.R
import com.laundry.user.constants.Constants

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.regex.Matcher
import java.util.regex.Pattern


fun getRequestBodyOfString(strVal: String): RequestBody {

    return strVal.toRequestBody("text/plain".toMediaTypeOrNull())
}

fun getMultipartOfFileImage(file: File?, multipartKey: String): MultipartBody.Part? {
    if (file != null) {
        val requestFile = file!!.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(multipartKey, file.name, requestFile)
    }
    return null
}


var toast: Toast? = null

fun AppCompatActivity.showShortToast(msg: String) {
    if (toast != null) toast!!.cancel()
    (Toast.makeText(this, msg, Toast.LENGTH_SHORT) as Toast).show()
}

fun View.showVisibility() {
    this.visibility = View.VISIBLE
}

fun View.hideVisibility() {
    this.visibility = View.GONE
}

fun AppCompatActivity.showHidePassword(mEditext: EditText, mImage: ImageView) {
    if (mImage.drawable.constantState == ResourcesCompat.getDrawable(
            resources,
            R.drawable.hide,
            null
        )?.constantState
    ) {
        mEditext.transformationMethod =
            HideReturnsTransformationMethod.getInstance()
        mImage.setImageResource(R.drawable.show)
    } else {
        mEditext.transformationMethod = PasswordTransformationMethod.getInstance()
        mImage.setImageResource(R.drawable.hide)
    }
}


fun showHideToggle(mImage: ImageView,mContext: Context) {
    if (mImage.drawable.constantState == ResourcesCompat.getDrawable(
            mContext.resources,
            R.drawable.toggle_btn_off,
            null
        )?.constantState
    ) {

            HideReturnsTransformationMethod.getInstance()
        mImage.setImageResource(R.drawable.toggle_btn_on)
    } else {
        PasswordTransformationMethod.getInstance()
        mImage.setImageResource(R.drawable.toggle_btn_off)
    }
}

fun EditText.getString(): String {
    return this.text.toString().trim()
}

fun MutableLiveData<String>.getValueFromMutableString():String
{
    return this.value!!.trim()
}
fun EditText.isValueEmpty(msg: String): Boolean {
    if (this.getString().isEmpty()) {
        (Toast.makeText(
            this.context,
            context.getString(R.string.please_enter) + " " + msg,
            Toast.LENGTH_SHORT
        ) as Toast).show()
        return false
    }

    return true
}
fun MutableLiveData<String>.isValueEmpty1(msg: String,mContext: Context): Boolean {
//    if ((this.value as String).isEmpty()) {
        if (this.value.orEmpty().isEmpty()) {
        (Toast.makeText(
            mContext,
            mContext.getString(R.string.please_enter) + " " + msg,
            Toast.LENGTH_SHORT
        ) as Toast).show()
        return false
    }

    return true
}

fun strongCheckPassword(password: String): Boolean {
    // one capital , one small, one special character,and number
    val pattern: Pattern
    val matcher: Matcher
    val passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
    pattern = Pattern.compile(passwordPattern)
    matcher = pattern.matcher(password)
    return matcher.matches()

}

fun AppCompatActivity.checkPassword(password: String, msg: String): Boolean {
    // we can also put the minimum length 8 on password
    if (password.length < 8) {
        (Toast.makeText(
            this,
            getString(R.string.please_enter) + " valid" + " " + msg,
            Toast.LENGTH_SHORT
        ) as Toast).show()
        return false
    }
    return true

}
fun checkPassword1(password: String, msg: String,mContext: Context): Boolean {
    // we can also put the minimum length 8 on password
    if (password.length < 8) {
        showToast(mContext.getString(R.string.please_enter) + " valid" + " " + msg,mContext)
        return false
    }
    return true

}
val String.isValidMobile: Boolean
    get() = Patterns.PHONE.matcher(this).matches() && this.length in 8..14

val String.isValidPassword: Boolean
    get() = this.length in 8..16


val String.isValidEmail: Boolean
    get() = this.matches(Constants.EMAIL_PATTERN.toRegex())
fun AppCompatActivity.checkEmail(email: String): Boolean {
    val emailPattern =
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    if (email.matches(emailPattern.toRegex())) {
        return true
    }
    (Toast.makeText(
        this,
        getString(R.string.please_enter) + " valid email",
        Toast.LENGTH_SHORT
    ) as Toast).show()

    return false
}
fun checkEmail1(email: String,mContext: Context): Boolean {
    val emailPattern =
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    if (email.matches(emailPattern.toRegex())) {
        return true
    }
showToast( mContext.getString(R.string.please_enter) + " valid email",mContext)

    return false
}

fun AppCompatActivity.isValidMobile(mobile_number: String): Boolean {
    if (mobile_number.length in 9..14) {
        return true
    }
    (Toast.makeText(
        this,
        getString(R.string.please_enter) + " valid mobile number",
        Toast.LENGTH_SHORT
    ) as Toast).show()

    return false
}
fun isValidMobile1(mobile_number: String,mContext: Context): Boolean {
    if (mobile_number.length in 9..14) {
        return true
    }
    showToast(mContext.getString(R.string.please_enter) + " valid mobile number",mContext)

    return false
}

fun CheckBox.checkedOrNot(msg: String): Boolean {
    if (this.isChecked) {
        return true
    }
    Toast.makeText(
        this.context,
        context.getString(R.string.please_select) + " " + msg,
        Toast.LENGTH_SHORT
    ).show()
    return false
}
fun MutableLiveData<Boolean>.checkedOrNot1(msg: String,mContext: Context): Boolean {
    if (this.value!!) {
        return true
    }
    Toast.makeText(
        mContext,
        mContext.getString(R.string.please_select) + " " + msg,
        Toast.LENGTH_SHORT
    ).show()
    return false
}

fun AppCompatActivity.hideKeyboard() {
    val focusedView = this.currentFocus
    val inputMethodManager =
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(
        focusedView?.windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
}

fun AppCompatActivity.matchTwoPassword(password1: String, password2: String, msg: String): Boolean {
    if (password1 == password2)
        return true
    else
        (Toast.makeText(
            this,
            msg,
            Toast.LENGTH_SHORT
        ) as Toast).show()
    return false
}

fun matchTwoPassword1(password1: String, password2: String, msg: String,mContext:Context): Boolean {
    if (password1 == password2)
        return true
    else
    showToast( msg,mContext)

    return false
}


fun getGsonInstance(): Gson {
    return Gson()
}

fun showToast(msg:String,mContext: Context)
{
    (Toast.makeText(mContext, msg, Toast.LENGTH_SHORT) as Toast).show()
}


@RequiresApi(Build.VERSION_CODES.M)
fun getSpannableString(msg:String, mTextView:TextView, startPoint:Int, endPoint:Int, mContext:Context)
{
    val spannable = SpannableString(msg)
    spannable.setSpan(
        ForegroundColorSpan(mContext.getColor(R.color.colorMain)),
        startPoint, endPoint,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    mTextView.text = spannable
}


