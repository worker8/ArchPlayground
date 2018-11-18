package github.com.worker8.archplayground.simpleMVP

import github.com.worker8.archplayground.common.isValidEmail

class SimpleMvpPresenter(val view: View) {
    var email = ""
    var password = ""

    fun onEmailTextChanged(_email: String) {
        email = _email
        view.showEmailError(shouldShowEmailError(email))
        view.setButton(enabled = shouldSetButtonEnable(email, password))
    }

    fun onPasswordTextChanged(_password: String) {
        password = _password
        view.showPasswordError(shouldShowPasswordError(password))
        view.setButton(enabled = shouldSetButtonEnable(email, password))
    }

    private fun shouldSetButtonEnable(email: String, password: String) =
            shouldShowEmailError(email) && shouldShowPasswordError(password)

    private fun shouldShowPasswordError(password: String) =
            !(password.length > 6)

    private fun shouldShowEmailError(email: String) =
            !email.isBlank() && !email.isValidEmail()


    interface View {
        fun showEmailError(shouldShow: Boolean)
        fun showPasswordError(shouldShow: Boolean)
        fun setButton(enabled: Boolean)
    }
}