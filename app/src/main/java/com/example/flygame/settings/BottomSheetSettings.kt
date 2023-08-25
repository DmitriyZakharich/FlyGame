package com.example.flygame.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BottomSheetSettings() {

    val contextForToast = LocalContext.current.applicationContext

    val coroutineScope = rememberCoroutineScope()

    val scaffoldState = rememberBottomSheetScaffoldState()

//    BottomSheetScaffold(
//        sheetContent = {MyBottomSheet()},
//        floatingActionButton ={
////            FloaActioButton
//        }) {
//
//        }




//    FloatingActionButton(onClick = { /*TODO*/ }) {
//
//    }
//
//    BottomSheetScaffold(
//
//        sheetShape = CutCornerShape(20.dp), //Rounded corners
//        sheetShadowElevation = 20.dp,//To provide Shadow
//        sheetPeekHeight = 50.dp,//Initial height of sheet[Collapsed]{maybe too much 4 u}
//        sheetContent = {
//            MyBottomSheet()//Create a sheet Composable
//        },
//
//        scaffoldState = scaffoldState,
//
//
//
////        sheetPeekHeight = 100.dp,
////        sheetContent = {
////            Column {
////                Text(text = "123")
////                Text(text = "123")
////                Text(text = "123")
////                Text(text = "123")
////            }
//////            LazyColumn {
//////                // the first item that is visible
//////                item {
//////                    Box(
//////                        modifier = Modifier
//////                            .height(56.dp) // set the height
//////                            .fillMaxWidth()
//////                    ) {
//////                        Text(
//////                            text = "Swipe up to Expand the sheet",
//////                            modifier = Modifier.align(alignment = Alignment.Center),
//////                            color = Color.White
//////                        )
//////                    }
//////                }
//////
//////                // remaining items
//////                items(count = 5) {
//////                    ListItem(
//////                        modifier = Modifier.clickable {
//////                            Toast.makeText(contextForToast, "Item $it", Toast.LENGTH_SHORT)
//////                                .show()
//////
//////                            coroutineScope.launch {
//////                                scaffoldState.bottomSheetState.hide()
//////                            }
//////                        },
//////
////////                        text = {
////////                            Text(text = "Item $it")
////////                        },
////////                        icon = {
////////                            Icon(
////////                                imageVector = Icons.Default.Favorite,
////////                                contentDescription = "Favorite",
////////                                tint = MaterialTheme.colors.primary
////////                            )
////////                        }
//////                    )
//////                }
//////            }
////        }
//    ) {
//        // app UI
////        Column(
////            modifier = Modifier.fillMaxSize(),
////            verticalArrangement = Arrangement.Center,
////            horizontalAlignment = Alignment.CenterHorizontally
////        )
//    }

//    BottomSheetScaffold(sheetContent =) {
//
//    }
}

@Composable
fun MyBottomSheet() {
    Column(
        modifier = Modifier
            .fillMaxSize()//Do this to make sheet expandable
            .background(Color.Black.copy(0.2f))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(//Using this to create a kind of Icon that tells the user that the sheet is expandable
            modifier = Modifier
                .height(3.dp)
                .width(70.dp)
                .background(Color.White)
        )
        Spacer(//Another spacer to add a space
            modifier = Modifier
                .height(20.dp)
        )
        Text(text = "Bottom Sheet")
    }
}