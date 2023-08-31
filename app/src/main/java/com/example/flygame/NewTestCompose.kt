package com.example.flygame

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun MyScreen() {
    val state = remember {
        mutableStateOf("Привет, мир!")
    }

    Log.d("newnewnewTAG", "MyScreen: ")

    Column {
//        Text(text = "OldText", modifier = Modifier
//            .background(Color.Blue)
//            .clickable {
//                state.value += "123"
//            })

        MyText(state)
    }


}

@Composable
fun MyText(state2: MutableState<String>) {
    Log.d("newnewnewTAG", "MyText: ")

    Text(text = state2.value, modifier = Modifier.background(Color.Blue).clickable {
        state2.value += "123"
    })
}