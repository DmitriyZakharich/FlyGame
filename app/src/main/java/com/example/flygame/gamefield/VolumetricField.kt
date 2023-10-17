package com.example.flygame.gamefield

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.flygame.settings.models.SettingsData


@Composable
fun VolumetricField(
    settings: SettingsData,
    gameViewModel: GameViewModel
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    var i = 1
    val list = List(settings.tableSize) { i++ }

    val boxOffsetY = remember { mutableFloatStateOf(screenWidth.value * (list.size / 2)) }

    /**Оставить scale, но использовать его для
     * width * scale и hi * scale
     * */
    Log.d("TfffAG", "VolumetricField: ")

    Box(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    boxOffsetY.floatValue += dragAmount.y
                    if (boxOffsetY.floatValue < 0) boxOffsetY.floatValue = 1f
                    if (boxOffsetY.floatValue > ((list.size - 1) * screenWidth.value)) boxOffsetY.floatValue =
                        (list.size - 1) * screenWidth.value
                }
            },
        contentAlignment = Alignment.TopCenter
    ) {
        list.forEachIndexed { volumeIndex, item ->
            Box(
                modifier = Modifier
//                    .scale(scaleList[index])
                    .offset(
                        y = getItemOffset(
                            volumeIndex,
                            boxOffsetY,
                            list.size,
                            screenWidth.value.toInt()
                        ).dp
                    )
                    .fillMaxSize(getNewScale(volumeIndex, list.size, boxOffsetY))
//                    .height(screenWidth)
                    .zIndex(-volumeIndex.toFloat())

            ) {
                Column (modifier = Modifier.fillMaxSize()) {
                    Text(text = "Слой $item ${if (volumeIndex == list.size / 2 ) "- Центр" else ""}",
                        modifier = Modifier
                            .background(Color.Blue)
                            .fillMaxWidth(), fontSize = 25.sp
                    )
                    FlatField(settings, gameViewModel, volumeIndex)
                }
            }
        }
    }
}

fun getItemOffset(count: Int, boxOffset: MutableFloatState, size: Int, screenWidth: Int): Int {
    var offset = 40 * (size - count - 1)
    if (boxOffset.floatValue.toInt() >= screenWidth * count)
        offset += boxOffset.floatValue.toInt() - (screenWidth * count)
    return offset
}

fun getNewScale(index: Int, size: Int, boxOffset: MutableFloatState): Float {
    val defaultScale = 1f
    var result = 1f
    result = defaultScale - (0.075f * index  /(boxOffset.floatValue / 1000))
    if (result < 0.5f ) result = 0.5f
    return result
}