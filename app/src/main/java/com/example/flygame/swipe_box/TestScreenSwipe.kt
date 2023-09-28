package com.example.flygame.swipe_box

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ForFlags() {
    var boxOffsetY by remember { mutableFloatStateOf(0f) }
    var positionY by remember { mutableFloatStateOf(0f) }

    Box(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    boxOffsetY += dragAmount.y
                }
            },
        contentAlignment = Alignment.TopCenter) {
            Button(
                onClick = {boxOffsetY = 0f}, modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .background(color = Color.Red)
                    .offset {

                        /**Двигаться без ускорения,
                         * блокировать items в верху, пока не дойдет до них очередь
                         *разделить offset на части по высоте экрана
                         * и как только предыдущий view уйдет за экрна двигать следующий
                         * */

                        IntOffset(
                            x = 0, y =

                            when {
                                boxOffsetY.toInt() < 0 -> {
                                    boxOffsetY = 0f
                                    0
                                }

                                boxOffsetY.toInt() in 0 until 400 -> boxOffsetY.toInt() * 3
                                else -> {
                                    boxOffsetY = 400F
                                    boxOffsetY.toInt() * 3
                                }
                            }


//                        if (boxOffsetY < 0) {
//                            boxOffsetY = 0f
//                            0
//                        } else {
//                        }
//                                boxOffsetY . toInt () * 3
//                        when {
//                            positionY < 0 -> 0
//                            positionY in 0.0 .. 300.0  -> boxOffsetY.roundToInt()
//                            positionY in 300.0..1000.0 -> boxOffsetY.roundToInt()
//                            positionY > 1000.0 -> 0
//                            else -> 0
//                        }
                        )
                    }
                    .onGloballyPositioned { coordinates ->
                        // global position (local also available)
                        positionY = coordinates.positionInRoot().y

                    }
            ) {
                Text(
                    text = """offsetY = $boxOffsetY
                    |positionY = $positionY 
                """.trimMargin(),
                    color = Color.Blue,
                    modifier = Modifier
                        .background(Color.Green)
                )
            }

    }
}