package github.com.worker8.archplayground.simpleMVPRx

import github.com.worker8.archplayground.common.isValidEmail
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction

class RxSimpleMvpPresenter(val view: View) {
    val disposables = CompositeDisposable()

    fun init() {
        val emailObsShared = view.emailTextChange.share()
        val passwordObsShared = view.passwordTextChange.share()

        emailObsShared
            .map { emailString -> shouldShowEmailError(emailString) }
            .subscribe { shouldShowError ->
                view.showEmailError(shouldShowError)
            }
            .let { disposables.add(it) }

        passwordObsShared
            .map { passwordString -> shouldShowPasswordError(passwordString) }
            .subscribe { shouldShowError ->
                view.showPasswordError(shouldShowError)
            }
            .let { disposables.add(it) }

        Observable
            .combineLatest(emailObsShared, passwordObsShared, BiFunction<String, String, Boolean> { emailString, passwordString ->
                shouldSetButtonEnable(emailString, passwordString)
            })
            .subscribe {
                view.setButton(it)
            }
            .let { disposables.add(it) }
    }

    fun onDestroy() {
        disposables.dispose()
    }

    private fun shouldSetButtonEnable(email: String, password: String) =
        !shouldShowEmailError(email) && !shouldShowPasswordError(password)

    private fun shouldShowPasswordError(password: String) =
        !(password.length > 6)

    private fun shouldShowEmailError(email: String) =
        !email.isBlank() && !email.isValidEmail()

    interface View {
        val emailTextChange: Observable<String>
        val passwordTextChange: Observable<String>

        fun showEmailError(shouldShow: Boolean)
        fun showPasswordError(shouldShow: Boolean)
        fun setButton(enabled: Boolean)
    }
}
