package github.com.worker8.archplayground.simpleMVP

import io.mockk.*
import org.junit.Assert.*
import org.junit.Test

class SimpleMvpPresenterTest {
    @Test
    fun presenterError() {
        val view = mockk<SimpleMvpPresenter.View>()
        val presenter = SimpleMvpPresenter(view)

        every { view.showEmailError(any()) } just Runs
        every { view.showPasswordError(any()) } just Runs

        presenter.onEmailTextChanged("not email")
        presenter.onPasswordTextChanged("123")

        verify {
            view.showEmailError(shouldShow = true)
            view.showPasswordError(shouldShow = true)
        }
    }

    @Test
    fun presenterNoError() {
        val view = mockk<SimpleMvpPresenter.View>()
        val presenter = SimpleMvpPresenter(view)

        every { view.showEmailError(any()) } just Runs
        every { view.showPasswordError(any()) } just Runs


        verify {
            view wasNot called
        }

        presenter.onEmailTextChanged("testing@gmail.com")
        presenter.onPasswordTextChanged("1234567")

        verify {
            view.showEmailError(shouldShow = false)
            view.showPasswordError(shouldShow = false)
        }
    }
}