package com.example.flygame.development

//import android.util.Log
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.border
//import androidx.compose.foundation.gestures.AnchoredDraggableState
//import androidx.compose.foundation.gestures.DraggableAnchors
//import androidx.compose.foundation.gestures.Orientation
//import androidx.compose.foundation.gestures.anchoredDraggable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.DisposableEffect
//import androidx.compose.runtime.MutableIntState
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.snapshots.SnapshotStateList
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalConfiguration
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.Density
//import androidx.compose.ui.unit.IntOffset
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.zIndex
//import kotlin.math.roundToInt
//
//enum class DragAnchors {
//    Top,
//    Bottom
//}
//
//data class CardSettings @OptIn(ExperimentalFoundationApi::class) constructor(
//    val state: AnchoredDraggableState<DragAnchors>,
//    var toBackground: Int
//)
//
//@Preview
//@Composable
//fun VerticalDraggableSample() {
//    val configuration = LocalConfiguration.current
//    val screenHeight = configuration.screenHeightDp.toFloat()
//    val density = LocalDensity.current
//
//    val list = listOf(1, 2, 3, 4, 5)
//    val focusItemState = remember { mutableIntStateOf(list.size / 2) }
//
//    val statesSettings = remember {
//        mutableStateListOf(
//            *getArrayStates(
//                size = list.size,
//                screenHeight = screenHeight,
//                density = density,
//                focusItem = focusItemState.intValue
//            )
//        )
//    }
////    val statesSettings2 = remember {mutableStateOf(listOf<CardSettings>())}
//
//    StackField(list, statesSettings, focusItemState)
//
//    UpdateFocusItem(
//        statesSettings,
//        size = list.size,
//        screenHeight = screenHeight,
//        density = density,
//        focusItemState = focusItemState
//    )
//
//    statesSettings.forEachIndexed{ count, item ->
//        UpdateState(item, count, focusItemState)
//    }
//}
//
///**Можно не Composable*/
////@Composable
//fun UpdateFocusItem(
//    statesSettings: SnapshotStateList<CardSettings>,
//    size: Int,
//    screenHeight: Float,
//    density: Density,
//    focusItemState: MutableIntState
//) {
//    statesSettings.clear()
//    statesSettings.addAll(
//        getArrayStates(
//        size = size,
//        screenHeight = screenHeight,
//        density = density,
//        focusItem = focusItemState.intValue
//    )
//    )
//}
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun StackField(
//    list: List<Int>,
//    statesSettings: SnapshotStateList<CardSettings>,
//    focusItem: MutableIntState
//    ) {
//    Log.d("newne5555555TAG", "StackField: ")
//
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center,
//    ) {
//
//        for (count in list.indices) {
//            Button(
//                onClick = {
//                    focusItem.intValue = count
//                },
////                modifier = listCardsSettings[count].modifier.zIndex(count.toFloat() * if (count > focusItem.intValue) -1 else 1)
//                modifier = anchoredDraggableModifier(
//                    state = statesSettings[count].state,
//                    count = count,
//                    toBackground = if (count > focusItem.intValue) -1 else 1,  //Bottom Item to background
////                    statesSettings[count].toBackground  //Bottom Item to background
//                )
////
//            ){
//                Text(text = "Button ${list[count]}")
//            }
//        }
//    }
//}
//
//
//@OptIn(ExperimentalFoundationApi::class)
//fun getArrayStates(
//    size: Int,
//    screenHeight: Float,
//    density: Density,
//    focusItem: Int
//): Array<CardSettings> {
//
//    val listStates = mutableListOf<CardSettings>()
//
//    for (count in 0 until size) {
//        val state = getState(
//            screenHeight = screenHeight,
//            density = density,
//            count,
//            if (count > focusItem) DragAnchors.Bottom else DragAnchors.Top
//        )
//
//        val toBackground = if (count > focusItem) -1 else 1
//
//        listStates.add(CardSettings(state, toBackground))
//    }
//    return listStates.toTypedArray()
//}
//
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun UpdateState(
//    cardSettings: CardSettings,
//    count: Int,
//    focusItemState: MutableIntState,
//) {
//
//    val initialValue = cardSettings.state.currentValue
//
//    if (cardSettings.state.isAnimationRunning) {
//
//        if (count < focusItemState.intValue ) {
////            groupMovement.floatValue = cardsSettings.state.offset
////            Log.d("hhhhh5555TAG", "cardsSettings.state.offset ${cardsSettings.state.offset}: ")
//        }
//
//        DisposableEffect(Unit) {
//            onDispose {
//                when {
//                    DragAnchors.Top == cardSettings.state.currentValue && initialValue != cardSettings.state.currentValue -> {
//
//                        focusItemState.intValue++   //пересчитывается даже когда был top и вернулся обратно
//                        cardSettings.toBackground = 1
//                    }
//
//                    DragAnchors.Bottom == cardSettings.state.currentValue && initialValue != cardSettings.state.currentValue-> {
//
//                        focusItemState.intValue--   //пересчитывается даже когда был bottom и вернулся обратно
//                        cardSettings.toBackground = -1
////                        groupMovement.floatValue = 0f
//                    }
//
//                    else -> return@onDispose
//                }
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalFoundationApi::class)
//fun getState(
//    screenHeight: Float, density: Density, count: Int, dragAnchors: DragAnchors
//): AnchoredDraggableState<DragAnchors> = AnchoredDraggableState(
//    initialValue = dragAnchors,
//    positionalThreshold = { distance: Float -> distance * 0.5f },
//    velocityThreshold = { with(density) { 100.dp.toPx() } },
//    animationSpec = tween(),
//).apply {
//    updateAnchors(
//        DraggableAnchors {
//            DragAnchors.Top at 50f * count
//            DragAnchors.Bottom at screenHeight //* 1.7f //400f
//        }
//    )
//}
//
//@Composable
//@OptIn(ExperimentalFoundationApi::class)
//fun anchoredDraggableModifier(
//    state: AnchoredDraggableState<DragAnchors>,
//    count: Int,
//    toBackground: Int
//) = Modifier
//    .size(300.dp, 200.dp)
//    .border(1.dp, Color.Red)
//    .offset {
//
//        IntOffset(
//            y = state
//                .requireOffset()
//                .roundToInt(), x = 0
//        )
//    }
//    .anchoredDraggable(state, Orientation.Vertical)
//    .zIndex(count.toFloat() * toBackground)