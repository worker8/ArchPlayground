package github.com.worker8.archplayground.simpleMVPRx

import android.text.TextUtils
import github.com.worker8.archplayground.common.isValidEmail
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.regex.Pattern

class RxSimpleMvpPresenter(val view: View) {
    val disposables = CompositeDisposable()

    fun init() {
        view.emailTextChange
                .map { emailString -> emailString.isNotBlank() && !emailString.isValidEmail() }
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

    interface View {
        val emailTextChange: Observable<String>
        val passwordTextChange: Observable<String>

        fun showEmailError(shouldShow: Boolean)
        fun showPasswordError(shouldShow: Boolean)
    }
}