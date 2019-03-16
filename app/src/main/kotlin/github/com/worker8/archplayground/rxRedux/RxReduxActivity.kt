package github.com.worker8.archplayground.rxRedux

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxbinding3.widget.textChanges
import github.com.worker8.archplayground.R
import github.com.worker8.archplayground.common.addTo
import github.com.worker8.archplayground.simpleMVP.RxReduxViewModel
import github.com.worker8.archplayground.simpleMVP.RxReduxViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class RxReduxActivity : AppCompatActivity() {
    private val disposableBag = CompositeDisposable()
    val userInput: RxReduxViewModel.UserInput by lazy {
        object : RxReduxViewModel.UserInput {
            override val onEmailChanged = emailEditText.textChanges().skipInitialValue().map { it.toString() }
            override val onPasswordChanged = passwordEditText.textChanges().skipInitialValue().map { it.toString() }
        }
    }

    private val viewModel: RxReduxViewModel by lazy {
        ViewModelProviders.of(this, RxReduxViewModelFactory(userInput)).get(RxReduxViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycle.addObserver(viewModel)
        Log.d("ddw", "onCreate: $this, emailEditText=$emailEditText")
        viewModel.store.states
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.apply {
                    if (showEmailError) {
                        emailErrorLabel.visibility = View.VISIBLE
                    } else {
                        emailErrorLabel.visibility = View.GONE
                    }

                    if (showPasswordError) {
                        passwordErrorLabel.visibility = View.VISIBLE
                    } else {
                        passwordErrorLabel.visibility = View.GONE
                    }
                    sendButton.isEnabled = enableButton
                }
            }
            .addTo(disposableBag)
    }
}
