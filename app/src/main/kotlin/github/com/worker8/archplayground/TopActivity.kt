package github.com.worker8.archplayground

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.util.Log
import android.widget.Toast
import com.github.kittinunf.statik.dsl.section
import com.github.kittinunf.statik.dsl.statik
import com.github.kittinunf.statik.dsl.textRow
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_top.*

class TopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)
        val row1 = textRow {
            text = "MVP example"
            onClickListener = {
                startActivity(Intent(this@TopActivity, MainActivity::class.java))
            }
        }

        val row2 = textRow {
            text = "MVP example with RX"
            onClickListener = {
                Toast.makeText(this@TopActivity, text, Toast.LENGTH_SHORT).show()
            }
        }

        val section = section { rows(row1, row2) }

        list.adapter = statik {
            sections(section)
        }

        list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}
