package github.com.worker8.archplayground.motionLayout

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import github.com.worker8.archplayground.R
import io.reactivex.disposables.CompositeDisposable


class MotionLayoutActivity : AppCompatActivity() {
    val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_layout)

    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
