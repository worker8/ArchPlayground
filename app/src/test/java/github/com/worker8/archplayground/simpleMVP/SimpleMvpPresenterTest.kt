package github.com.worker8.archplayground.simpleMVP

import io.mockk.*
import org.junit.Assert.*
import org.junit.Test

class SimpleMvpPresenterTest {
    @Test
    fun presenterError() {
        // setup
        val view = mockk<SimpleMvpPresenter.View>()
        val presenter = SimpleMvpPresenter(view)
        every { view.showEmailError(any()) } just Runs
        every { view.showPasswordError(any()) } just Runs

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
        // setup
        val view = mockk<SimpleMvpPresenter.View>()
        val presenter = SimpleMvpPresenter(view)
        every { view.showEmailError(any()) } just Runs
        every { view.showPasswordError(any()) } just Runs

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
}