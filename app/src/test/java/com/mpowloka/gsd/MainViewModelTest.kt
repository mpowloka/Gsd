package com.mpowloka.gsd

import org.awaitility.Awaitility.await
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class MainViewModelTest {

    private lateinit var SUT: MainViewModel

    @Before
    fun setup() {
        SUT = MainViewModel()
    }

    @Test
    fun setCurrentPhrase_newPhraseEmitted() {
        SUT.setCurrentPhrase(PHRASE)

        SUT.currentPhrase.test().assertValue {
            it == PHRASE
        }
    }

    @Test
    fun searchPressed_newSearchPressedEventEmitted() {

        var result: String? = null

        SUT.searchClicks
            .subscribe {
                result = it
            }

        SUT.searchPressed(PHRASE)

        await().atMost(1, TimeUnit.SECONDS).until {
            result == PHRASE
        }


    }

    companion object {
        private const val PHRASE = "PHRASE"
    }
}