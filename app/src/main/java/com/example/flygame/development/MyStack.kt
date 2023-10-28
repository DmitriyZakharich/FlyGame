package com.example.flygame.development

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Preview
@Composable
fun MyStack() {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    val list = listOf(1, 2, 3, 4, 5)
//    val listOffset = remember { mutableStateListOf<Int>() }
    val listSettingsItem = remember {
        mutableStateListOf<SettingsItem>()
    }


    for (count in list.indices) {
        listSettingsItem.add(SettingsItem())
    }
//    var offsetY by remember { mutableFloatStateOf(0f) }
    Log.d("TAGqqqqqqqqqq", "MyStack: ")

    val focusItemState = remember {
        mutableIntStateOf(list.size / 2)
    }


//    val focusItem = list.size / 2
    val defaultOffsetUp = -10
    val defaultZIndex = 10



    for (count in list.indices) {
        when {
            count == focusItemState.intValue -> {
                listSettingsItem[count] = SettingsItem(offsetY = 0, zIndex = defaultZIndex - 0)
//                listOffset[count] = 0
            }
            count < focusItemState.intValue -> {
                listSettingsItem[count] = SettingsItem(offsetY = count * defaultOffsetUp, zIndex = count - defaultZIndex)
//                listOffset[count] = count * -10
            }
            count > focusItemState.intValue -> {
                listSettingsItem[count] =
                    SettingsItem(offsetY = screenHeight / 2 - 20, zIndex = defaultZIndex - count)
//                listOffset[count] = screenHeight / 2 - 20
            }
        }
    }




    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        for (count in list.indices) {

//            val offset = if (count == 0) -100 else -20
//            val i = listOffset[count]

            Button(
                onClick = {
                    focusItemState.intValue = count
//                    listOffset[count] = 10
                }, modifier = Modifier
                    .size(300.dp, 150.dp)
                    .offset(y = (listSettingsItem[count].offsetY).dp)
                    .border(2.dp, Color.Red)
                    .zIndex((listSettingsItem[count].zIndex).toFloat())
                    .draggable(
                        orientation = Orientation.Vertical,
                        state = rememberDraggableState { delta ->

                            Log.d("ddddTAG", "delta: $delta")

                            if (delta > 70) {
                                focusItemState.intValue--
                            } else if (delta < -70)
                                focusItemState.intValue++

//                            listSettingsItem[count].offsetY = if (delta > 0) {
//                                200
//                            } else {
//                                -200
//                            }
//                        offsetY += delta
                        }
                    )


            ) {
                Text(text = "Button ${list[count]}")


            }
//        Text("Center", Modifier.align(Alignment.Center))


        }
    }
}

data class SettingsItem(
    val offsetY: Int = 0,
    val zIndex: Int = 0
)