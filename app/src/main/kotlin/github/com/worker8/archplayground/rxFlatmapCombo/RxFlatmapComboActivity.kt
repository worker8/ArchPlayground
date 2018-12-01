package github.com.worker8.archplayground.rxFlatmapCombo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.util.Log
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxrelay2.PublishRelay
import github.com.worker8.archplayground.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_rx_flatmap_combo.*


class RxFlatmapComboActivity : AppCompatActivity() {
    //    val button1Subject: PublishSubject<Unit> = PublishSubject.create<Unit>()
    val disposables = CompositeDisposable()
    var relay1 = PublishRelay.create<String>()
    var relay2 = PublishRelay.create<String>()
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
