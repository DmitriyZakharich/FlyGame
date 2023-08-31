package com.example.flygame

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.ViewModel
import kotlin.math.roundToInt

class MyViewModelCompose : ViewModel() {

//    fun getStateArrayOfModifiers(
//        size: Int, screenHeight: Float, density: Density, focusItem: MutableIntState
//    ): Array<Modifier> {
//
//        val arrayStateModifiers = Array<Modifier>(size) { Modifier }
//
//        for (count in arrayStateModifiers.indices) {
//            val state = getState(
//                screenHeight = screenHeight,
//                density = density,
//                count,
//                if (count > focusItem.intValue) DragAnchors.Bottom else DragAnchors.Top
//            )
//
//            UpdateState(state)
//
//            arrayStateModifiers[count] = anchoredDraggableModifier(
//                state = state,
//                count = count,
//                if (count > focusItem.intValue) -1 else 1  //Bottom Item to background
//            )
//        }
//        return arrayStateModifiers
//    }
//
//    @OptIn(ExperimentalFoundationApi::class)
//    fun getState(
//        screenHeight: Float, density: Density, count: Int, dragAnchors: DragAnchors
//    ): AnchoredDraggableState<DragAnchors> = remember {
//        AnchoredDraggableState(
//            initialValue = dragAnchors,
//            positionalThreshold = { distance: Float -> distance * 0.5f },
//            velocityThreshold = { with(density) { 100.dp.toPx() } },
//            animationSpec = tween(),
//        ).apply {
//            updateAnchors(
//                DraggableAnchors {
//                    DragAnchors.Top at 50f * count
//                    DragAnchors.Bottom at screenHeight //* 1.7f //400f
//                }//, newTarget = ?
//            )
//        }
//    }
//
//
//    @OptIn(ExperimentalFoundationApi::class)
//    fun anchoredDraggableModifier(state: AnchoredDraggableState<DragAnchors>, count: Int, toBackground: Int) =
//        Modifier
//            .size(300.dp, 200.dp)
//            .border(1.dp, Color.Red)
//            .offset {
////            if (isMoveGroup){
////            }
//                IntOffset(
//                    y = state
//                        .requireOffset()
//                        .roundToInt(), x = 0
//                )
//            }
//            .anchoredDraggable(state, Orientation.Vertical)
//            .zIndex(count.toFloat() * toBackground)
}