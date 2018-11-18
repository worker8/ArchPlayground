package github.com.worker8.archplayground.simpleMVPRx

import github.com.worker8.archplayground.simpleMVP.SimpleMvpPresenter
import io.mockk.*
import io.reactivex.subjects.PublishSubject
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RxSimpleMvpPresenterTest {
    lateinit var view: RxSimpleMvpPresenter.View
    lateinit var presenter: RxSimpleMvpPresenter

    lateinit var emailSubject: PublishSubject<String>
    lateinit var passwordSubject: PublishSubject<String>

    @Before
    fun setup() {
        view = mockk()
        presenter = RxSimpleMvpPresenter(view)
        emailSubject = PublishSubject.create()
        passwordSubject = PublishSubject.create()
        every { view.emailTextChange } returns emailSubject
        every { view.passwordTextChange } returns passwordSubject
        every { view.showEmailError(any()) } just Runs
        every { view.showPasswordError(any()) } just Runs
    }

    @Test
    fun presenterError() {
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