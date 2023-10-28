package com.example.flygame.gamefield.domain

import android.speech.tts.TextToSpeech
import android.util.Log
import com.example.flygame.App
import com.example.flygame.gamefield.models.DirectionMove
import kotlinx.coroutines.delay
import java.util.Locale
import javax.inject.Inject

class Announcer @Inject constructor(): TextToSpeech.OnInitListener{

    private val textToSpeech: TextToSpeech = TextToSpeech(App.appContext, this)

    suspend fun start(moves: List<DirectionMove>) {
        textToSpeech.setSpeechRate(3f)
        textToSpeech.language = Locale("ru")
        var notification = ""

        moves.forEach {
            notification = when (it) {
                DirectionMove.UP -> "Вверх"
                DirectionMove.DOWN -> "Вниз"
                DirectionMove.LEFT -> "Влево"
                DirectionMove.RIGHT -> "Вправо"
                DirectionMove.FORWARD -> "Вперед"
                DirectionMove.BACK -> "Назад"
                DirectionMove.NULL -> ""
            }
            textToSpeech.speak(notification, TextToSpeech.QUEUE_ADD, null, "")
            delay(1000L)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = textToSpeech.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTSаааааааааа", "The Language specified is not supported!")
            } else {
//                btnObjectDetection.isEnabled = true
            }
        } else {
            Log.e("TTSаааааааааа", "Initialization Failed!")
        }
    }
}