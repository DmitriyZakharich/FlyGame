package com.example.flygame.settings

import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.flygame.gamefield.FlatField
import com.example.flygame.gamefield.Table
import com.example.flygame.settings.models.SettingsData
import com.example.flygame.settings.viewstate.SettingsState
import kotlinx.coroutines.launch


//@OptIn(ExperimentalMaterial3Api::class)
@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun BottomSheetSettings(answer: (id: Int) -> Unit = {}) {

    val contextForToast = LocalContext.current.applicationContext

//    val coroutineScope = rememberCoroutineScope()
//    val scaffoldState = rememberBottomSheetScaffoldState()

    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
//            Box(
//                modifier = Modifier.background(Color.Cyan)
//            ) {
//                Row(){
//                    Text(text = "123")
//                    Text(text = "123")
//                    Text(text = "123")
//                }
//
//            }
                       SettingsScreen()

                       },
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
//        Text(text = "Content")

        /**Находится здесь, потому content BottomSheet занимает всё доступное пространство
         * и перекрывает поле.
         * */
        Table(settingsStore = SettingsStore(LocalContext.current), answer)
    }
}

