package github.com.worker8.archplayground

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.widget.Toast
import com.github.kittinunf.statik.dsl.section
import com.github.kittinunf.statik.dsl.statik
import com.github.kittinunf.statik.dsl.textRow
import github.com.worker8.archplayground.simpleMVP.SimpleMvpActivity
import github.com.worker8.archplayground.simpleMVPRx.RxSimpleMvpActivity
import kotlinx.android.synthetic.main.activity_top.*

class TopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)
        val row1 = textRow {
            text = "MVP example"
            onClickListener = {
                startActivity(Intent(this@TopActivity, SimpleMvpActivity::class.java))
            }
        }

        val row2 = textRow {
            text = "MVP example with RX"
            onClickListener = {
                startActivity(Intent(this@TopActivity, RxSimpleMvpActivity::class.java))
            }
        }

        val section = section { rows(row1, row2) }

        list.adapter = statik {
            sections(section)
        }

        list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}
