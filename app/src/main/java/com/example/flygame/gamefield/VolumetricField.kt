package com.example.flygame.gamefield

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    gameViewModel: IGameViewModel
) {
    val configuration = LocalConfiguration.current
    val itemSize = configuration.screenWidthDp.dp   //ширина и высота item = ширине экрана

    val quantityItems = remember { mutableIntStateOf(settings.tableSize) }
    val boxOffsetY = remember { mutableFloatStateOf(itemSize.value * (quantityItems.intValue / 2)) }
    val maxOffset = remember { mutableFloatStateOf((quantityItems.intValue - 1) * itemSize.value) }

    if (quantityItems.intValue != settings.tableSize) { //измениние размера таблицы
        quantityItems.intValue = settings.tableSize
        boxOffsetY.floatValue = itemSize.value * (quantityItems.intValue / 2)
        maxOffset.floatValue = (quantityItems.intValue - 1) * itemSize.value
    }

    var i = 1
    val list = List(quantityItems.intValue) { i++ }

    /**Оставить scale, но использовать его для
     * width * scale и hi * scale
     * */
    Box(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    boxOffsetY.floatValue += dragAmount.y
                    if (boxOffsetY.floatValue < 0) boxOffsetY.floatValue = 1f
                    if (boxOffsetY.floatValue > maxOffset.floatValue){
                        boxOffsetY.floatValue = maxOffset.floatValue

                    }
                }
            },
        contentAlignment = Alignment.TopCenter
    ) {
        list.forEachIndexed { volumeIndex, item ->
            Box(
                modifier = Modifier
                    .offset(
                        y = getItemOffset(
                            volumeIndex,
                            boxOffsetY,
                            list.size,
                            itemSize.value.toInt()
                        ).dp
                    )
                    .fillMaxSize(getScale(volumeIndex, list.size, boxOffsetY))
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

fun getItemOffset(index: Int, boxOffset: MutableFloatState, size: Int, itemSize: Int): Int {

    var itemOffset = 40 * (size - index - 1)

    if (boxOffset.floatValue.toInt() >= itemSize * index)
        itemOffset += boxOffset.floatValue.toInt() - (itemSize * index)

    return itemOffset
}

fun getScale(index: Int, size: Int, boxOffset: MutableFloatState): Float {
    val defaultScale = 1f
    var result: Float
    result = defaultScale - (0.075f * index  /(boxOffset.floatValue / 1000))
    if (result < 0.75f ) result = 0.75f
    return result
}