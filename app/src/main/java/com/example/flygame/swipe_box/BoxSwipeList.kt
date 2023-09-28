package com.example.flygame.swipe_box

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ForFlags2() {

    val list = listOf(1, 2, 3, 4, 5)

    var boxOffsetY by remember { mutableFloatStateOf(0f) }
    var positionY by remember { mutableFloatStateOf(0f) }
//    var listButtonsOffset = remember {
//        mutableStateListOf(*getStartOffset(list.size))
//    }

    Box(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    boxOffsetY += dragAmount.y
                }
            },
        contentAlignment = Alignment.TopCenter
    ) {
        list.forEachIndexed { index, item ->
            Button(
                onClick = {},
                modifier = Modifier
                    .offset(
                        y =
                        getOffset(index, boxOffsetY.toInt(), list.size).dp

//                        if (boxOffsetY >= 200 * index)
//                            listButtonsOffset[index].dp
//                        else
//                            0.dp

                    )
                    .size(250.dp, 100.dp)
            ) {
                Text(
                    text = """offsetY = $boxOffsetY
                    |item = $item 
                """.trimMargin()
                )
            }
        }
    }
}

//TODO передалать на приём state и изменение в теле
fun getOffset(count: Int, boxOffset: Int, size: Int): Int {
    var initOffset = 70 * (size - count - 1)
    if (boxOffset >= 200 * count)
        initOffset += boxOffset - (200 * count)
    return initOffset
}

//fun getStartOffset(size: Int) : Array<Int> {
//    val array = Array(size){0}
//    for (count in 0 until size) {
//        array[count] = 70 * count
//    }
//    return array
//}