package com.example.flygame.gamefield.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.flygame.gamefield.viewmodel.IGameViewModel
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
    val boxSize = remember { mutableStateOf(IntSize.Zero) }


    if (quantityItems.intValue != settings.tableSize) { //измениние размера таблицы
        quantityItems.intValue = settings.tableSize
        boxOffsetY.floatValue = itemSize.value * (quantityItems.intValue / 2)
        maxOffset.floatValue = (quantityItems.intValue - 1) * itemSize.value
    }

    var i = 1
    val list = List(quantityItems.intValue) { i++ }
    val layerHeaderHeight = 30
//        if ((boxSize.value.height - itemSize.value) / list.size < 40)
//            ((boxSize.value.height - itemSize.value) / list.size).toInt()
//        else
//            40


    Box(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->

                    change.consume()

                    boxOffsetY.floatValue += dragAmount.y
                    if (boxOffsetY.floatValue < 0) boxOffsetY.floatValue = 1f
                    if (boxOffsetY.floatValue > maxOffset.floatValue) {
                        boxOffsetY.floatValue = maxOffset.floatValue
                    }
                }
            }
            .onSizeChanged { boxSize.value = it },
        contentAlignment = Alignment.TopCenter
    ) {
        VolumetricFieldItems(
            boxOffsetY,
            maxOffset,
            list,
            layerHeaderHeight,
            itemSize,
            settings,
            gameViewModel
        )
    }
}

@Composable
fun VolumetricFieldItems(
    boxOffsetY: MutableFloatState,
    maxOffset: MutableFloatState,
    list: List<Int>,
    layerHeaderHeight: Int,
    itemSize: Dp,
    settings: SettingsData,
    gameViewModel: IGameViewModel
) {
    list.forEachIndexed { volumeIndex, item ->
        Column(
            modifier = Modifier
                .offset(
                    y = getItemOffset(
                        layerHeaderHeight,
                        volumeIndex,
                        boxOffsetY,
                        list.size,
                        itemSize.value.toInt()
                    ).dp
                )
                .fillMaxSize(getScale(volumeIndex, list.size, boxOffsetY, maxOffset))
                .zIndex(-volumeIndex.toFloat())
        ) {
            Text(
                text = "Слой $item ${if (volumeIndex == list.size / 2) "- Центр" else ""}",
                modifier = Modifier
                    .background(Color.Blue)
                    .border(width = 1.dp, color = Color.Red)
                    .fillMaxWidth()
                    .height(layerHeaderHeight.dp),
                fontSize = (layerHeaderHeight - 7).sp,
                maxLines = 1,
                color = Color.White
            )
            if (boxOffsetY.floatValue > itemSize.value * (volumeIndex - 1) &&
                boxOffsetY.floatValue < itemSize.value * volumeIndex + itemSize.value * 1.5
            ) {
                FlatField(settings, gameViewModel, volumeIndex)
            }
        }
    }
}

fun getItemOffset(
    layerHeaderHeight: Int,
    index: Int,
    boxOffset: MutableFloatState,
    size: Int,
    itemSize: Int
): Int {

    var itemOffset = (layerHeaderHeight) * (size - index - 1)
    if (boxOffset.floatValue.toInt() >= itemSize * index)
        itemOffset += boxOffset.floatValue.toInt() - (itemSize * index)
    return itemOffset
}

fun getScale(
    index: Int,
    size: Int,
    boxOffset: MutableFloatState,
    maxOffset: MutableFloatState
): Float {
    val minScale = 0.7f
    val maxScale = 1f
    var result = 1f

    val stepBetweenItems = (maxScale - minScale) / (size - 1)
    result = minScale + ((size - 1 - index) * stepBetweenItems)

    val difference = maxScale - minScale
    val steps = 30
    val oneStep = difference / steps
    val weight = maxOffset.floatValue / steps
    val additionalScale = oneStep * boxOffset.floatValue / weight
    result += additionalScale

    if (result < minScale) result = minScale
    if (result > maxScale) result = maxScale
    return result
}