package github.com.worker8.archplayground

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import com.github.kittinunf.statik.dsl.section
import com.github.kittinunf.statik.dsl.statik
import com.github.kittinunf.statik.dsl.textRow
import github.com.worker8.archplayground.rxFlatmapCombo.RxFlatmapComboActivity
import github.com.worker8.archplayground.rxRedux.RxReduxActivity
import github.com.worker8.archplayground.simpleMVP.SimpleMvvmActivity
import github.com.worker8.archplayground.simpleMVPRx.RxSimpleMvpActivity
import kotlinx.android.synthetic.main.activity_top.*

class TopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)
        val row1 = textRow {
            text = "MVP example"
            onClickListener = {
                startActivity(Intent(this@TopActivity, SimpleMvvmActivity::class.java))
            }
        }

        val row2 = textRow {
            text = "MVP example with Rx"
            onClickListener = {
                startActivity(Intent(this@TopActivity, RxSimpleMvpActivity::class.java))
            }
        }

        val row3 = textRow {
            text = "Rx Flatmap Combo"
            onClickListener = {
                startActivity(Intent(this@TopActivity, RxFlatmapComboActivity::class.java))
            }
        }

        val row4 = textRow {
            text = "Simple Mvvm"
            onClickListener = {
                startActivity(Intent(this@TopActivity, SimpleMvvmActivity::class.java))
            }
        }

        val row5 = textRow {
            text = "RxRedux"
            onClickListener = {
                startActivity(Intent(this@TopActivity, RxReduxActivity::class.java))
            }
        }

        val section = section { rows(row1, row2, row3, row4, row5) }

        list.adapter = statik {
            sections(section)
        }

        list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}
