package github.com.worker8.archplayground.simpleMVPRx

import android.text.TextUtils
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class RxSimpleMvpPresenter(val view: View) {
    val disposables = CompositeDisposable()

    fun init() {
        view.emailTextChange
                .map { emailString -> emailString.isNotBlank() && !isValidEmail(emailString) }
                .subscribe { shouldShowError ->
                    view.showEmailError(shouldShowError)
                }
                .let { disposables.add(it) }

        view.passwordTextChange
                .map { passwordString -> !(passwordString.length > 6) }
                .subscribe { shouldShowError ->
                    view.showPasswordError(shouldShowError)
                }
                .let { disposables.add(it) }
    }

    fun isValidEmail(target: CharSequence): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    interface View {
        val emailTextChange: Observable<String>
        val passwordTextChange: Observable<String>

        fun showEmailError(shouldShow: Boolean)
        fun showPasswordError(shouldShow: Boolean)
    }
}