package com.example.flygame.swipe_box

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.flygame.gamefield.FlatField
import com.example.flygame.gamefield.GameViewModel
import com.example.flygame.settings.models.SettingsData

//@Preview
@Composable
fun VolumetricField(
    settings: SettingsData,
    gameViewModel: GameViewModel
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    var i = 1;
    val list = List(settings.tableSize) { i++ }

    var boxOffsetY by remember { mutableFloatStateOf(screenWidth.value * (list.size / 2)) }
//    val scaleList = remember { mutableStateListOf(*getScale(list.size, boxOffsetY)) }


    val scaleList = remember { mutableStateListOf<Float>() }
    getScale(scaleList, list.size, boxOffsetY)

    /**Оставить scale, но использовать его для
     * width * scale и hi * scale
     * */

    Box(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    boxOffsetY += dragAmount.y
                    if (boxOffsetY < 0) boxOffsetY = 1f
                    if (boxOffsetY > ((list.size - 1) * screenWidth.value)) boxOffsetY =
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
                            boxOffsetY.toInt(),
                            list.size,
                            screenWidth.value.toInt()
                        ).dp
                    )
                    .fillMaxSize(scaleList[volumeIndex])
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

//TODO передалать на приём state и изменение в теле
fun getItemOffset(count: Int, boxOffset: Int, size: Int, screenWidth: Int): Int {
    var offset = 40 * (size - count - 1)
    if (boxOffset >= screenWidth * count)
        offset += boxOffset - (screenWidth * count)
    return offset
}

fun getScale(scaleList: SnapshotStateList<Float>, size: Int, boxOffset: Float) {
    val defaultScale = 1f
    val array = Array(size){1f}

    Log.d("TfffAG", "getNewScale: ")

    for (count in 0 until size) {
        array[count] = defaultScale - (0.075f * count  /(boxOffset / 1000))
    }

    for (count in 0 until size) {
         if (array[count] < 0.5f ) array[count] = 0.5f
    }

    scaleList.clear()
    scaleList.addAll(array)

}