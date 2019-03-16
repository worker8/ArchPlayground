package github.com.worker8.archplayground.simpleMVPRx

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding3.widget.textChanges
import github.com.worker8.archplayground.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*

class RxSimpleMvpActivity : AppCompatActivity() {

    val view: RxSimpleMvpPresenter.View by lazy {
        object : RxSimpleMvpPresenter.View {
            override val emailTextChange: Observable<String> = emailEditText.textChanges().skipInitialValue().map { it.toString() }
            override val passwordTextChange: Observable<String> = passwordEditText.textChanges().skipInitialValue().map { it.toString() }
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

            override fun setButton(enabled: Boolean) {
                if (enabled) {
                    sendButton.visibility = View.VISIBLE
                } else {
                    sendButton.visibility = View.INVISIBLE
                }
            }
        }
    }

    val presenter: RxSimpleMvpPresenter by lazy { RxSimpleMvpPresenter(view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.init()

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
