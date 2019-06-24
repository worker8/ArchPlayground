package com.example.dagger_sample

import android.content.Context
import android.content.SharedPreferences
import com.example.dagger_sample.util.defaultPrefs
import com.example.dagger_sample.util.save
import javax.inject.Inject

class DaggerSampleRepo2 @Inject constructor(context: Context) : DaggerSampleRepoInterface {
    private val prefs: SharedPreferences = context.defaultPrefs()

    private fun getSavedStringFromPref(): String = prefs.getString(CONSTANT_KEY, "")!!

    override fun getSavedString(): List<String> {
        val oldString = getSavedStringFromPref()
        return oldString.split(DELIMITER).map { "^$it^" }
    }

    override fun saveStringToPref(newString: String) {
        val oldString = getSavedStringFromPref()
        prefs.save(CONSTANT_KEY, "$oldString$DELIMITER$newString")
    }

    override fun removeAllPref() {
        prefs.edit().clear().commit()
    }

    companion object {
        const val CONSTANT_KEY = "CONSTANT_KEY"
        const val DELIMITER = "DELIMITER"
    }
}
