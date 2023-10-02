package com.example.flygame.settings

import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.flygame.gamefield.GameViewModel
import com.example.flygame.gamefield.Table
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetSettings(
    gameViewModel: GameViewModel
) {
    val contextForToast = LocalContext.current.applicationContext

    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
//        modifier = Modifier.height(40.dp),
        sheetContent = { SettingsScreen() },
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

