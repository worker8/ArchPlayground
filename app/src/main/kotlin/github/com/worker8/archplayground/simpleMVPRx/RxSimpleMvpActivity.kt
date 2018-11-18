package github.com.worker8.archplayground.simpleMVPRx

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.jakewharton.rxbinding3.widget.textChanges
import github.com.worker8.archplayground.R
import github.com.worker8.archplayground.simpleMVPRx.RxSimpleMvpPresenter
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
        }
    }

    val presenter: RxSimpleMvpPresenter by lazy { RxSimpleMvpPresenter(view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.init()

    }
}