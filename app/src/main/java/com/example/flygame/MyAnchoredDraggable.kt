package com.example.flygame

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt

enum class DragAnchors {
    Top,
    Bottom
}

data class CardsSettings @OptIn(ExperimentalFoundationApi::class) constructor(
    val modifier: Modifier,
    val state: AnchoredDraggableState<DragAnchors>,
    var toBackground: Int
)

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun HorizontalDraggableSample() {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.toFloat()
    val density = LocalDensity.current



    val list = listOf(1, 2, 3, 4, 5)
    val focusItemState = remember { mutableIntStateOf(list.size / 2) }


    val listCardsSettings = remember {
        mutableStateListOf(
            *getStateArrayOfModifiers(
                size = list.size,
                screenHeight = screenHeight,
                density = density,
                focusItem = focusItemState
            )
        )
    }

//    Log.d("focusItemState.intValue", "focusItemState.intValue: ${focusItemState.intValue}")

    StackField(list, listCardsSettings, focusItemState)

    listCardsSettings.forEach{
        UpdateState(it, focusItemState)
    }

}


@Composable
fun StackField(
    list: List<Int>,
    listCardsSettings: SnapshotStateList<CardsSettings>,
    focusItem: MutableIntState
) {
    Log.d("newne5555555TAG", "StackField: ")

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        for (count in list.indices) {
            Button(
                onClick = {
//                    focusItemState.intValue = count
                },
                modifier = listCardsSettings[count].modifier.zIndex(count.toFloat() * if (count > focusItem.intValue) -1 else 1)
            ){
                Text(text = "Button ${list[count]}")
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
//@Composable
fun getStateArrayOfModifiers(size: Int, screenHeight: Float, density: Density, focusItem: MutableIntState): Array<CardsSettings> {

    Log.d("newne5555555TAG", "getStateArrayOfModifiers: ")


    val listStates = mutableListOf<CardsSettings>()

    for (count in 0 until size) {
        val state = getState(
            screenHeight = screenHeight,
            density = density,
            count,
            if (count > focusItem.intValue) DragAnchors.Bottom else DragAnchors.Top
        )

        val toBackground = if (count > focusItem.intValue) -1 else 1

        val modifier = anchoredDraggableModifier(
            state = state,
            count = count,
            toBackground  //Bottom Item to background
        )

        listStates.add(CardsSettings(modifier, state, toBackground))
    }

    return listStates.toTypedArray()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UpdateState(
    cardsSettings: CardsSettings,
    focusItemState: MutableIntState,
) {
    Log.d("newne5555555TAG", "UpdateState: ")

    val initialValue = cardsSettings.state.currentValue

    if (cardsSettings.state.isAnimationRunning) {
        DisposableEffect(Unit) {
            onDispose {
                when {
                    DragAnchors.Top == cardsSettings.state.currentValue && initialValue != cardsSettings.state.currentValue -> {

                        focusItemState.intValue++   //пересчитывается даже когда был top и вернулся обратно
                        cardsSettings.toBackground = 1

                        Log.d("fff12TAG", "swipe top: ")
                    }

                    DragAnchors.Bottom == cardsSettings.state.currentValue && initialValue != cardsSettings.state.currentValue-> {

                        focusItemState.intValue--   //пересчитывается даже когда был bottom и вернулся обратно
                        cardsSettings.toBackground = -1

                        Log.d("fff12TAG", "swipe Bottom: ")
                    }

                    else -> return@onDispose
                }
            }
        }
    }

}


@OptIn(ExperimentalFoundationApi::class)
//@Composable
fun getState(screenHeight: Float, density: Density, count: Int, dragAnchors: DragAnchors): AnchoredDraggableState<DragAnchors> = //remember {
    AnchoredDraggableState(
        initialValue = dragAnchors,
        positionalThreshold = { distance: Float -> distance * 0.5f },
        velocityThreshold = { with(density) { 100.dp.toPx() } },
        animationSpec = tween(),
    ).apply {
        updateAnchors(
            DraggableAnchors {
                DragAnchors.Top at 50f * count
                DragAnchors.Bottom at screenHeight //* 1.7f //400f
            }//, newTarget = ?
        )
    }
//}

//@OptIn(ExperimentalFoundationApi::class)
//@Composable
@OptIn(ExperimentalFoundationApi::class)
fun anchoredDraggableModifier(state: AnchoredDraggableState<DragAnchors>, count: Int, toBackground: Int) =
    Modifier
        .size(300.dp, 200.dp)
        .border(1.dp, Color.Red)
        .offset {
//            if (isMoveGroup){
//            }
            Log.d("fff12TAG", "anchoredDraggableModifier toBackground = $toBackground")



            IntOffset(
                y = state
                    .requireOffset()
                    .roundToInt(),
                x = 0
            )
        }
        .anchoredDraggable(state, Orientation.Vertical)
//        .zIndex(count.toFloat() * toBackground)