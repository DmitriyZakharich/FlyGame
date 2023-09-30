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
import com.example.flygame.gamefield.GameViewModel
import com.example.flygame.gamefield.Table
import kotlinx.coroutines.launch


//@OptIn(ExperimentalMaterial3Api::class)
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
        sheetContent = { SettingsScreen() },
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
                    contentDescription = "Favorite"
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

