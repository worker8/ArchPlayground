package com.example.dagger_sample

interface DaggerSampleRepoInterface {
    fun getSavedString(): List<String>
    fun saveStringToPref(newString: String)
    fun removeAllPref()
}
