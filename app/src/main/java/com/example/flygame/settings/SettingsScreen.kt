package com.example.flygame.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flygame.R
import com.example.flygame.instructions.AppSettingsState
import com.example.flygame.instructions.AppSettingsViewModel
import com.example.flygame.instructions.InstructionsScreen
import com.example.flygame.instructions.TypeInstruction
import com.example.flygame.settings.models.listNumberOfMoves
import com.example.flygame.settings.models.listSpeed
import com.example.flygame.settings.models.listTableSizes
import com.example.flygame.settings.models.listVoiceArrows
import com.example.flygame.settings.viewstate.SettingsState

@Composable
fun SettingsScreen() {
    val viewModel: SettingsViewModel = viewModel()
    val settingsData = viewModel.data.collectAsState()
    val checkedStateVolume = remember { mutableStateOf(settingsData.value.spinnerIsVolume) }
    val checkedStateHideField = remember { mutableStateOf(settingsData.value.spinnerIsHideField) }

    val callback : (SettingsState) -> Unit = { state ->
        viewModel.spinnerItemSelected(state)
    }

    val appSettingsViewModel: AppSettingsViewModel = viewModel()
    val showInstructionsDialog = remember { mutableStateOf(Pair(TypeInstruction.VOLUMETRIC_FIELD, false)) }

    if (showInstructionsDialog.value.second ){
        InstructionsScreen(showInstructionsDialog.value.first, showInstructionsDialog){
            appSettingsViewModel.setAppSettings(AppSettingsState.Instructions(false))
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
         Text(
             text = stringResource(R.string.Settings),
             modifier = Modifier.fillMaxWidth(),
             maxLines = 1,
             textAlign = TextAlign.Center,
             fontSize = 10.sp)

        MySpinnerRow(
            id = R.string.table_size,
            options = listTableSizes,
            state = settingsData.value.spinnerTableSize,
            callback
        )
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            MyText(
                id = R.string.volume,
                Modifier
            )
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart) {
                Icon(
                    painterResource(
                    id = R.drawable.ic_circle_question),
                    contentDescription = "Инструкция",
                    Modifier
                        .padding(start = 5.dp)
                        .clickable {
                            showInstructionsDialog.value =
                                Pair(TypeInstruction.VOLUMETRIC_FIELD, true)
//                            showInstructionsDialog.value = true
                        },
                )

            }
            Switch(
                checked = settingsData.value.spinnerIsVolume,
                onCheckedChange = {
                    checkedStateVolume.value = it
                    viewModel.clickListener(SettingsState.Volume(it))
                }
            )
        }
        MySpinnerRow(
            id = R.string.speed,
            options = listSpeed,
            state = settingsData.value.spinnerSpeed,
            callback
        )
        MySpinnerRow(
            id = R.string.voice,
            options = listVoiceArrows,
            state = settingsData.value.spinnerVoice,
            callback
        )
        MySpinnerRow(
            id = R.string.number_of_moves,
            options = listNumberOfMoves,
            state = settingsData.value.spinnerNumberOfMoves,
            callback
        )

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            MyText(
                id = R.string.hide_field,
                Modifier
            )
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart) {
                Icon(
                    painterResource(
                        id = R.drawable.ic_circle_question),
                    contentDescription = "Инструкция",
                    Modifier
                        .padding(start = 5.dp)
                        .clickable {
                            showInstructionsDialog.value = Pair(TypeInstruction.HIDE_FIELD, true)
                        },
                )

            }
            Switch(
                checked = settingsData.value.spinnerIsHideField,
                onCheckedChange = {
                    checkedStateHideField.value = it
                    viewModel.clickListener(SettingsState.HideField(it))
                }
            )
        }
        Text(
            text = stringResource(id = R.string.complication),
            color = Color.Blue,
            fontSize = 30.sp
        )
    }
}

@Composable
fun MySpinnerRow(
    id: Int,
    options: List<String>,
    state: Int,
    onSelect: (state: SettingsState) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        MyText(
            id = id,
            modifier = Modifier.weight(4f)
        )
        Box(modifier = Modifier
            .wrapContentSize()
            .weight(1.8f)){
            MySpinner(id, options, state, onSelect)
        }
    }
}

@Composable
fun MyText(id: Int, modifier: Modifier) {
    Text(
        maxLines = 1,
        text = stringResource(id = id),
        color = Color.Blue,
        fontSize = 20.sp,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySpinner(
    id: Int,
    options: List<String>,
    state: Int,
    onSelect: (state: SettingsState) -> Unit) {

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[state]) }

    ExposedDropdownMenuBox(/*modifier = Modifier.wrapContentSize(),*/
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ){
        TextField(
            modifier = Modifier
                .menuAnchor()
                .widthIn(1.dp),
            readOnly = true,
            value = options[state],
            onValueChange = { },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            maxLines = 1,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),

        )

        ExposedDropdownMenu(modifier = Modifier/*.wrapContentSize()*/,
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ){
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(text = selectionOption, modifier = Modifier/*.wrapContentSize()*/) },
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                        onSelect(
                            when(id) {
                                R.string.table_size -> { SettingsState.TableSize(selectionOption.toInt()) }
                                R.string.speed -> { SettingsState.Speed(selectionOption.toInt()) }
                                R.string.voice -> { SettingsState.Voice(selectionOption) }
                                R.string.number_of_moves -> { SettingsState.NumberOfMoves(selectionOption.toInt()) }
                                else -> { SettingsState.Speed (0) }
                            }
                        )
                    })
            }
        }
    }
}

@Preview
@Composable
fun PreviewScreen() {
    SettingsScreen()
}