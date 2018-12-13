package github.com.worker8.archplayground.simpleMVP

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.util.Log
import github.com.worker8.archplayground.common.isValidEmail
import github.com.worker8.archplayground.common.ofType
import github.com.worker8.archplayground.rxRedux.*
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import com.github.kittinunf.redux.devTools.core

sealed class LoginFormAction : Action
class EmailInput(val emailString: String) : LoginFormAction()
class EmailError(val isError: Boolean) : LoginFormAction()
class PasswordInput(val passwordString: String) : LoginFormAction()
class PasswordError(val isError: Boolean) : LoginFormAction()
class EnableButton(val isEnable: Boolean) : LoginFormAction()

class RxReduxViewModel(userInput: RxReduxViewModel.UserInput) : ViewModel(), LifecycleObserver {
    private val disposableBag = CompositeDisposable()
    //private val outputSubject = BehaviorSubject.createDefault<Output>(Output())
    //val output = outputSubject.hide()

    data class ScreenState(val emailString: String = "",
                           val passwordString: String = "",
                           val showEmailError: Boolean = false,
                           val showPasswordError: Boolean = false,
                           val enableButton: Boolean = false) : State

    val loginFormReducer = object : Reducer<ScreenState> {
        override fun reduce(currentState: ScreenState, action: Action): ScreenState {
            lateinit var newState: ScreenState
            when (action) {
                is EmailInput -> {
                    action.ofType<EmailInput> { _action ->
                        newState = currentState.copy(emailString = _action.emailString)
                    }
                }
                is EmailError -> {
                    action.ofType<EmailError> { _action ->
                        newState = currentState.copy(showEmailError = _action.isError)
                    }
                }
                is PasswordInput -> {
                    action.ofType<PasswordInput> {
                        newState = currentState.copy(passwordString = it.passwordString)
                    }
                }
                is PasswordError -> {
                    action.ofType<PasswordError> { _action ->
                        newState = currentState.copy(showPasswordError = _action.isError)
                    }
                }
                is EnableButton -> {
                    action.ofType<EnableButton> { _action ->
                        newState = currentState.copy(enableButton = _action.isEnable)
                    }
                }
                else -> newState = currentState
            }
            Log.d("ddw","$newState")
            return newState
        }
    }

    val emailChangedShared = userInput.onEmailChanged.share()
    val passwordChangedShared = userInput.onPasswordChanged.share()
    class DevToolsStore<S : State>(private val store: StoreType<S>) : StoreType<S> by store {

        object DevToolsStateChangeAction : Action

        private val instrument =
            Instrument(emulatorDefaultOption(), store.initialState).apply {
                // configure
                onError = {
                    Log.e("[Devtools]", "Instrument", it)
                }
                onMessageReceived = {
                    store.dispatch(DevToolsStateChangeAction)
                }

                start()
                connectBlocking()
                // send the first state
                handleStateChangeFromAction(store.initialState, Store.INIT)
            }

        init {
            //modify replaceReducer so we can inject state from the Devtools
            store.replaceReducer = { reducedState, action ->
                if (action is DevToolsStateChangeAction) {
                    instrument.state
                } else {
                    instrument.handleStateChangeFromAction(reducedState, action)
                    reducedState
                }
            }
        }
    }

    val store = DevToolsStore(Store(ScreenState(), loginFormReducer))

    init {
        userInput.apply {
            emailChangedShared
                .map { emailString -> shouldShowEmailError(emailString) }
                .subscribe {
                    store.dispatch(EmailError(it))
                }

            passwordChangedShared
                .map { passwordString -> shouldShowPasswordError(passwordString) }
                .subscribe {
                    store.dispatch(PasswordError(it))
                }

            Observable.combineLatest(emailChangedShared, passwordChangedShared, BiFunction<String, String, Boolean> { emailString, passwordString ->
                shouldSetButtonEnable(emailString, passwordString)
            })
                .subscribe {
                    store.dispatch(EnableButton(it))
                }
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
}

@Suppress("UNCHECKED_CAST")
class RxReduxViewModelFactory(val input: RxReduxViewModel.UserInput) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RxReduxViewModel(input) as T
    }
}
