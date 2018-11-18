package github.com.worker8.archplayground.simpleMVPRx

import android.text.TextUtils
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.regex.Pattern

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
        val emailTextChange: Observable<String>
        val passwordTextChange: Observable<String>

        fun showEmailError(shouldShow: Boolean)
        fun showPasswordError(shouldShow: Boolean)
    }
}