package github.com.worker8.archplayground.simpleMVPRx

import github.com.worker8.archplayground.simpleMVP.SimpleMvpPresenter
import io.mockk.*
import io.reactivex.subjects.PublishSubject
import org.junit.Assert.*
import org.junit.Test

class RxSimpleMvpPresenterTest {
    @Test
    fun presenterError() {
        // setup
        val view = mockk<RxSimpleMvpPresenter.View>()
        val presenter = RxSimpleMvpPresenter(view)
        val emailSubject: PublishSubject<String> = PublishSubject.create()
        val passwordSubject: PublishSubject<String> = PublishSubject.create()

        every { view.emailTextChange } returns emailSubject
        every { view.passwordTextChange } returns passwordSubject
        every { view.showEmailError(any()) } just Runs
        every { view.showPasswordError(any()) } just Runs

        // action
        presenter.init()
        emailSubject.onNext("not email")
        passwordSubject.onNext("123")

        // assert
        verify {
            view.showEmailError(shouldShow = true)
            view.showPasswordError(shouldShow = true)
        }
    }

    @Test
    fun presenterNoError() {
        // setup
        val view = mockk<RxSimpleMvpPresenter.View>()
        val presenter = RxSimpleMvpPresenter(view)
        val emailSubject: PublishSubject<String> = PublishSubject.create()
        val passwordSubject: PublishSubject<String> = PublishSubject.create()

        every { view.emailTextChange } returns emailSubject
        every { view.passwordTextChange } returns passwordSubject
        every { view.showEmailError(any()) } just Runs
        every { view.showPasswordError(any()) } just Runs

        // assert
        verify {
            view wasNot called
        }

        // action
        presenter.init()
        emailSubject.onNext("test@gmail.com")
        passwordSubject.onNext("1234567")

        // assert
        verify {
            view.showEmailError(shouldShow = false)
            view.showPasswordError(shouldShow = false)
        }
    }
}