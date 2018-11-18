package github.com.worker8.archplayground.simpleMVP

import android.text.TextUtils
import java.util.regex.Pattern

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
        return if (target.isEmpty()) {
            false
        } else {
            Pattern.compile(
                    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                            "\\@" +
                            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                            "(" +
                            "\\." +
                            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                            ")+").matcher(target).matches()
        }
    }

    interface View {
        fun showEmailError(shouldShow: Boolean)
        fun showPasswordError(shouldShow: Boolean)
    }
}