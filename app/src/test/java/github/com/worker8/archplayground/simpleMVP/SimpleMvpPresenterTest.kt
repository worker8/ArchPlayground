package github.com.worker8.archplayground.simpleMVP

import io.mockk.*
import org.junit.Before
import org.junit.Test

class SimpleMvpPresenterTest {
    lateinit var view: SimpleMvpPresenter.View
    lateinit var presenter: SimpleMvpPresenter

    @Before
    fun setup() {
        view = mockk()
        presenter = SimpleMvpPresenter(view)
        // setup
        every { view.showEmailError(any()) } just Runs
        every { view.showPasswordError(any()) } just Runs
        every { view.setButton(any()) } just Runs
    }

    @Test
    fun presenterError() {
        // action
        presenter.onEmailTextChanged("not email")
        presenter.onPasswordTextChanged("123")

        // assert
        verify {
            view.showEmailError(shouldShow = true)
            view.showPasswordError(shouldShow = true)
        }
    }

    @Test
    fun presenterNoError() {
        // assert
        verify {
            view wasNot called
        }

        // action
        presenter.onEmailTextChanged("testing@gmail.com")
        presenter.onPasswordTextChanged("1234567")

        // assert
        verify {
            view.showEmailError(shouldShow = false)
            view.showPasswordError(shouldShow = false)
        }
    }

    @Test
    fun presenterTestButtonEnable() {
        // assert
        verify {
            view wasNot called
        }

        // action
        presenter.onEmailTextChanged("testing") // invalid email
        presenter.onPasswordTextChanged("1234567")

        // assert
        verify {
            view.showEmailError(shouldShow = true)
            view.showPasswordError(shouldShow = false)
            view.setButton(enabled = false)
        }

        // action
        presenter.onEmailTextChanged("testing@gmail.com")
        presenter.onPasswordTextChanged("12") // invalid password

        // assert
        verify {
            view.showEmailError(shouldShow = false)
            view.showPasswordError(shouldShow = true)
            view.setButton(enabled = false)
        }

        // action
        presenter.onEmailTextChanged("testing@gmail.com")
        presenter.onPasswordTextChanged("1234567")

        // assert
        verify {
            view.showEmailError(shouldShow = false)
            view.showPasswordError(shouldShow = false)
            view.setButton(enabled = true)
        }

    }
}