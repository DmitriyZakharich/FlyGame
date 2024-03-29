package com.example.flygame.settings.presentation

import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.flygame.gamefield.viewmodel.IGameViewModel
import com.example.flygame.gamefield.presentation.Table
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetSettings(
    gameViewModel: IGameViewModel
) {
    val contextForToast = LocalContext.current.applicationContext

    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = { SettingsScreen() },
        backgroundColor = Color.Transparent,
        sheetPeekHeight = 0.dp, //высота свёрнутого меню настроек

        floatingActionButton = {
            FloatingActionButton(onClick = {
                coroutineScope.launch {
                    if (scaffoldState.bottomSheetState.isCollapsed)
                        scaffoldState.bottomSheetState.expand()
                    else
                        scaffoldState.bottomSheetState.collapse()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            }
        }
    ) {
        /**Находится здесь, потому content BottomSheet занимает всё доступное пространство
         * и перекрывает поле.
         * */
        Table(gameViewModel)
    }
}

