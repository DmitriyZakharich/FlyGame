package com.example.flygame.development

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun MyScreen() {
    val state = remember {
        mutableStateListOf(MyData("Первый"), MyData("Второй"), MyData("Третий"))
    }

//    state[0].innerClass.m = "123"
//    Column {
//
//
        MyText(state)
//    }
}

@Composable
fun MyText(state2: SnapshotStateList<MyData>) {

//    val secondState = remember {
//        mutableStateOf("Клик")
//    }

    Column(modifier = Modifier.fillMaxSize()) {

        state2.forEach {
            Text(text = "Класс = ${it.string}, x = ${it.x}, y = ${it.y}, innerClass = ${it.innerClass.m},",

                modifier = Modifier
                    .background(Color.Blue)
                    .clickable {

                    }, fontSize = 30.sp)
        }

        Text(text = state2[0].string, Modifier.clickable{

            state2[0] = state2[0].copy(string = "!!!!!!", innerClass = InnerClass("!!!!!!"))
        /*secondState.value = "!!!!!!!"*/}, fontSize = 20.sp)




    }
}

data class MyData(
    var string: String = "",
    val x: Int = 1,
    val y: Int = 2,
    val innerClass: InnerClass = InnerClass()
)

data class InnerClass(
    var m: String = "Изменяемая строка"
)