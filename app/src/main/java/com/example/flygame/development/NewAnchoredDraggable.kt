package com.example.flygame.development
//
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.gestures.AnchoredDraggableState
//import androidx.compose.foundation.gestures.DraggableAnchors
//import androidx.compose.foundation.gestures.Orientation
//import androidx.compose.foundation.gestures.anchoredDraggable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.IntOffset
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.flygame.R
//import kotlin.math.roundToInt
//
//enum class NewDragAnchors {
//    FOCUS_ONE,
//    FOCUS_TWO,
//    FOCUS_THREE,
//    FOCUS_FOUR,
//    FOCUS_FIVE
//}
//
//@Preview
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun NewAnchoredDraggable() {
//    val density = LocalDensity.current
//
//    var i = 1
//    val list = List(5) { i++ }
//    val statesList: MutableList<AnchoredDraggableState<NewDragAnchors>> = mutableListOf()
//    val visibleStateList: MutableList<Boolean> = mutableListOf()
//    val stepOffset = 400f
//
//    val anchoredDraggableState = remember {
//        AnchoredDraggableState(
//            initialValue = NewDragAnchors.FOCUS_FIVE,
//            positionalThreshold = { distance: Float -> distance * 0.5f },
//            velocityThreshold = { with(density) { 100.dp.toPx() } },
//            animationSpec = tween(),
//        ).apply {
//
//            updateAnchors(
//                DraggableAnchors {
//                    NewDragAnchors.FOCUS_FIVE at stepOffset * 0
//                    NewDragAnchors.FOCUS_FOUR at stepOffset * 1
//                    NewDragAnchors.FOCUS_THREE at stepOffset * 2
//                    NewDragAnchors.FOCUS_TWO at stepOffset * 3
//                    NewDragAnchors.FOCUS_ONE at stepOffset * 4
//                }
//            )
//        }
//    }
//
//    var focusItem = 0
//
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .anchoredDraggable(anchoredDraggableState, Orientation.Vertical)
//    ) {
//
//        list.forEachIndexed { index, item ->
//
////            statesList.add(index, getAnchoredDraggableState(density, index))
////            visibleStateList.add(index, getVisibilityState(index, focusItem))
////            if (statesList[index].isAnimationRunning) ++focusItem
////            if (statesList[index].currentValue == NewDragAnchors.Top)
//
//            Column(
//                Modifier
//                    .border(1.dp, Color.Red)
//                    .offset {
//
////                            if (anchoredDraggableState.currentValue.ordinal == 5) 0
//                        if (anchoredDraggableState.currentValue.ordinal < index)
//
//                            IntOffset(
//                                x = 40 * index,
//                                y = anchoredDraggableState
//                                    .requireOffset()
//                                    .roundToInt(),
//                            )
//                        else
//                            IntOffset(40 * index, 0)
//
//                    }
//            ) {
//                Text(
//                    text = "Слой $item",
//                    color = Color.White,
//                    fontSize = 20.sp,
//                    modifier = Modifier.background(
//                        Color.DarkGray
//                    )
//                )
//                Image(
//                    painter = painterResource(id = R.drawable.icon_fly),
//                    modifier = Modifier
//                        .size(125.dp),
//                    contentDescription = null,
//                )
//            }
//        }
//    }
//}
//
//
////@Composable
////@OptIn(ExperimentalFoundationApi::class)
////fun getAnchoredDraggableState(density: Density, index: Int) = remember {
////    AnchoredDraggableState(
////        initialValue = NewDragAnchors.FOCUS_ONE,
////        positionalThreshold = { distance: Float -> distance * 0.5f },
////        velocityThreshold = { with(density) { 100.dp.toPx() } },
////        animationSpec = tween(),
////    ).apply {
////        val step = 400f
////        updateAnchors(
////            DraggableAnchors {
////                NewDragAnchors.FOCUS_ONE at step * 0
////                NewDragAnchors.FOCUS_TWO at step * 1
////                NewDragAnchors.FOCUS_THREE at step * 2
////                NewDragAnchors.FOCUS_FOUR at step * 3
////                NewDragAnchors.FOCUS_FIVE at step * 4
////            }
////        )
////    }
////}
//
//
////fun getVisibilityState(index: Int, focusItem: Int): Boolean {
////
////    if (index < focusItem + 1) return false
////
////    return true
////}
//
