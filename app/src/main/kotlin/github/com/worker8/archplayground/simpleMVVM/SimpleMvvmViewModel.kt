package github.com.worker8.archplayground.simpleMVP

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import github.com.worker8.archplayground.common.isValidEmail
import github.com.worker8.archplayground.common.realValue
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject

class SimpleMvvmViewModel(userInput: SimpleMvvmViewModel.UserInput) : ViewModel(), LifecycleObserver {
    private val disposableBag = CompositeDisposable()
    private val outputSubject = BehaviorSubject.createDefault<Output>(Output())
    val output = outputSubject.hide()

    val emailChangedShared = userInput.onEmailChanged.share()
    val passwordChangedShared = userInput.onPasswordChanged.share()

    init {
        userInput.apply {
            emailChangedShared
                .map { emailString -> shouldShowEmailError(emailString) }
                .subscribe {
                    outputSubject.onNext(outputSubject.realValue.copy(showEmailError = it))
                }

            passwordChangedShared
                .map { passwordString -> shouldShowPasswordError(passwordString) }
                .subscribe {
                    outputSubject.onNext(outputSubject.realValue.copy(showPasswordError = it))
                }

            Observable.combineLatest(emailChangedShared, passwordChangedShared, BiFunction<String, String, Boolean> { emailString, passwordString ->
                shouldSetButtonEnable(emailString, passwordString)
            })
                .subscribe { outputSubject.onNext(outputSubject.realValue.copy(enableButton = it)) }
        }
    }

    private fun shouldSetButtonEnable(email: String, password: String) =
        !shouldShowEmailError(email) && !shouldShowPasswordError(password)

    private fun shouldShowPasswordError(password: String) =
        !(password.length > 6)

    private fun shouldShowEmailError(email: String) =
        !email.isBlank() && !email.isValidEmail()


    interface UserInput {
        val onEmailChanged: Observable<String>
        val onPasswordChanged: Observable<String>
    }

    override fun onCleared() {
        super.onCleared()
        disposableBag.dispose()
    }

    data class Output(val showEmailError: Boolean = false, val showPasswordError: Boolean = false, val enableButton: Boolean = false)

//        fun showEmailError(shouldShow: Boolean)
//        fun showPasswordError(shouldShow: Boolean)
//        fun setButton(enabled: Boolean)

}

@Suppress("UNCHECKED_CAST")
class SimpleMvvmViewModelFactory(val input: SimpleMvvmViewModel.UserInput) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SimpleMvvmViewModel(input) as T
    }
}
