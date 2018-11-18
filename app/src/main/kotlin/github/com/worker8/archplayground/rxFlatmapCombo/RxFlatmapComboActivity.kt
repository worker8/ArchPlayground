package github.com.worker8.archplayground.rxFlatmapCombo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import github.com.worker8.archplayground.R
import github.com.worker8.archplayground.simpleMVPRx.RxSimpleMvpPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_rx_flatmap_combo.*
import java.lang.Exception
import kotlin.math.log
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.disposables.CompositeDisposable


class RxFlatmapComboActivity : AppCompatActivity() {
    //    val button1Subject: PublishSubject<Unit> = PublishSubject.create<Unit>()
    val button1Click by lazy {

        button1.clicks().map {
            Log.d("ddw", "button1 clicked")
            "button1 text\n"
        }
    }

    val button2Click by lazy {

        button2.clicks().map {
            Log.d("ddw", "button2 clicked")
            throw Exception("hey there, yo!")
            "button2 text\n"
        }
    }
    val disposables = CompositeDisposable()
    var relay1 = PublishRelay.create<String>()

    var relay2 = PublishRelay.create<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_flatmap_combo)

        button1Click
                .subscribe(relay1)
                .let { disposables.add(it) }

        button2Click
                .subscribe(relay2)
                .let { disposables.add(it) }

        relay1
                .flatMap { relay2 }
                .subscribe(
                        {
                            textArea.append(it)
                        },
                        { error ->
                            textArea.append(Html.fromHtml("\n<font color='#FE2C55'>${error.message}</font>"))
                        })
                .let { disposables.add(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
