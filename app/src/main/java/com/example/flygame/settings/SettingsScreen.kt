package com.example.flygame.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flygame.R

@Composable
fun SettingsScreen() {
    val viewModel: SettingsViewModel = viewModel()
    val checkedState = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
         Text(
             text = stringResource(R.string.Settings),
             modifier = Modifier.fillMaxWidth(),
             maxLines = 1,
             textAlign = TextAlign.Center,
             fontSize = 40.sp)

        MyRow(
            string = stringResource(id = R.string.table_size),
            list = listOf("3", "4", "5", "6", "7", "8", "9", "10")
        ) {type, position ->
            viewModel.spinnerItemSelected(type, position)
        }
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            MyText(
                string = stringResource(id = R.string.volumetric_field),
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it }
            )
        }
        MyRow(
            string = stringResource(id = R.string.speed),
            list = listOf("1", "2", "3", "4", "5")
        )
        MyRow(
            string = stringResource(id = R.string.voice),
            list = listOf("1", "2", "3", "4", "5")
        )
        MyRow(
            string = stringResource(id = R.string.number_of_moves),
            list = listOf("1", "2", "3", "4", "5")
        )
        Text(
            text = stringResource(id = R.string.complication),
            color = Color.Blue,
            fontSize = 30.sp
        )
    }
}

@Composable
fun MyRow(string: String, list: List<String>,
          onSelect: (type: Int, position: Int) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        MyText(
            string = string,
            modifier = Modifier.weight(3f)
        )
        Box(modifier = Modifier.weight(1f)){
            MySpinner(list, onSelect)
        }
    }
}

@Composable
fun MyText(string: String, modifier: Modifier) {
    Text(
        maxLines = 1,
        text = string,
        color = Color.Blue,
        fontSize = 25.sp,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySpinner(options: List<String>, onSelect: (type: Int, position: Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(modifier = Modifier.wrapContentSize(),
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ){
        TextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .wrapContentSize()
        )

        ExposedDropdownMenu(modifier = Modifier.wrapContentSize(),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ){
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false

                        onSelect(15, 15)
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