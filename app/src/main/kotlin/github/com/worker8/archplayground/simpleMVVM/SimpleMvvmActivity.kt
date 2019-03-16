package github.com.worker8.archplayground.simpleMVP

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxbinding3.widget.textChanges
import github.com.worker8.archplayground.R
import github.com.worker8.archplayground.common.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class SimpleMvvmActivity : AppCompatActivity() {
    private val disposableBag = CompositeDisposable()
    val userInput: SimpleMvvmViewModel.UserInput by lazy {
        object : SimpleMvvmViewModel.UserInput {
            override val onEmailChanged = emailEditText.textChanges().skipInitialValue().map { it.toString() }
            override val onPasswordChanged = passwordEditText.textChanges().skipInitialValue().map { it.toString() }
        }
    }

    private lateinit var viewModel: SimpleMvvmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, SimpleMvvmViewModelFactory(userInput)).get(SimpleMvvmViewModel::class.java)
        lifecycle.addObserver(viewModel)

        viewModel.output
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
