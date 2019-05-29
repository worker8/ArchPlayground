package com.example.dagger_sample

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    lateinit var repo: DaggerSampleRepoInterface
    @Before
    fun setup() {

    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, (2 + 2).toLong())
    }
}
