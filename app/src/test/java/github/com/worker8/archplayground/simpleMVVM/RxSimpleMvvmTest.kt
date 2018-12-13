package github.com.worker8.archplayground.simpleMVVM

import github.com.worker8.archplayground.simpleMVP.SimpleMvvmViewModel
import io.mockk.every
import io.mockk.mockk
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test

class RxSimpleMvvmTest {
    lateinit var userInput: SimpleMvvmViewModel.UserInput
    lateinit var viewModel: SimpleMvvmViewModel

    lateinit var emailSubject: PublishSubject<String>
    lateinit var passwordSubject: PublishSubject<String>

    @Before
    fun setup() {
        userInput = mockk()
        emailSubject = PublishSubject.create()
        passwordSubject = PublishSubject.create()
        every { userInput.onEmailChanged } returns emailSubject
        every { userInput.onPasswordChanged } returns passwordSubject

        viewModel = SimpleMvvmViewModel(userInput)

    }

    @Test
    fun viewModelError() {
        // action
        emailSubject.onNext("not email")
        passwordSubject.onNext("123")

        val testObservable = viewModel.output.test()

        // assert
        testObservable.assertNoErrors()
        testObservable.assertValueAt(0) {
            it.showPasswordError && it.showEmailError
        }
    }

    @Test
    fun viewModelNoError() {
        // action
        emailSubject.onNext("test@gmail.com")
        passwordSubject.onNext("1234567")

        val testObservable = viewModel.output.test()

        // assert
        testObservable.assertValueAt(0) {
            !it.showEmailError && !it.showPasswordError
        }
    }

    @Test
    fun viewModelTestButtonEnable_invalidEmail() {
        // action
        emailSubject.onNext("testlalala") // invalid email
        passwordSubject.onNext("12345678")

        val testObservable = viewModel.output.test()

        // assert
        testObservable.assertValueAt(0) {
            it.showEmailError && !it.showPasswordError && !it.enableButton
        }
    }

    @Test
    fun viewModelTestButtonEnable_invalidPassword() {
        // action
        emailSubject.onNext("johndoe@gmail.com")
        passwordSubject.onNext("123")// invalid password

        val testObservable = viewModel.output.test()

        // assert
        testObservable.assertValueAt(0) {
            !it.showEmailError && it.showPasswordError && !it.enableButton
        }
    }

    @Test
    fun viewModelTestButtonEnable_happyPath() {
        // action
        emailSubject.onNext("johndoe@gmail.com") // valid email
        passwordSubject.onNext("12345678")// valid password

        val testObservable = viewModel.output.test()

        // assert
        testObservable.assertValueAt(0) {
            !it.showEmailError && !it.showPasswordError && it.enableButton
        }
    }
}
