package com.example.flygame.about_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flygame.App.Companion.appContext
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class AboutAppViewModel @Inject constructor() : ViewModel() {

    private val _text: MutableStateFlow<String> = MutableStateFlow("")
    val text: StateFlow<String> = _text

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val inputStream: InputStream = appContext.assets.open("about_app.txt")
                val size: Int = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                val string = String(buffer)
                _text.emit(string)

            } catch (e: IOException) {
                e.printStackTrace()
                _text.emit("error")
            }
        }
    }
}