package github.com.worker8.archplayground.simpleMVP

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import github.com.worker8.archplayground.R
import kotlinx.android.synthetic.main.activity_main.*

class SimpleMvpActivity : AppCompatActivity() {

    val view: SimpleMvpPresenter.View by lazy {
        object : SimpleMvpPresenter.View {
            override fun setButton(enabled: Boolean) {
                if (enabled) {
                    sendButton.visibility = View.VISIBLE
                } else {
                    sendButton.visibility = View.INVISIBLE
                }
            }

            override fun showEmailError(shouldShow: Boolean) {
                if (shouldShow) {
                    emailErrorLabel.visibility = View.VISIBLE
                } else {
                    emailErrorLabel.visibility = View.INVISIBLE
                }
            }

            override fun showPasswordError(shouldShow: Boolean) {
                if (shouldShow) {
                    passwordErrorLabel.visibility = View.VISIBLE
                } else {
                    passwordErrorLabel.visibility = View.INVISIBLE
                }
            }
        }
    }

    val presenter: SimpleMvpPresenter by lazy { SimpleMvpPresenter(view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                presenter.onEmailTextChanged(s.toString())
            }
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                presenter.onPasswordTextChanged(s.toString())
            }
        })
    }
}
