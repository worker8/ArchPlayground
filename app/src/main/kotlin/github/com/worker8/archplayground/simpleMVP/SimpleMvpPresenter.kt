package github.com.worker8.archplayground.simpleMVP

import android.text.TextUtils

class SimpleMvpPresenter(val view: View) {
    var email = ""
    var password = ""

    fun onEmailTextChanged(_email: String) {
        email = _email
        val shouldShowEmailError = !email.isBlank() && !isValidEmail(email)
        view.showEmailError(shouldShowEmailError)
    }

    fun onPasswordTextChanged(_password: String) {
        password = _password
        val shouldShowPasswordError = !(password.length > 6)
        view.showPasswordError(shouldShowPasswordError)
    }

    fun isValidEmail(target: CharSequence): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    interface View {
        fun showEmailError(shouldShow: Boolean)
        fun showPasswordError(shouldShow: Boolean)
    }
}