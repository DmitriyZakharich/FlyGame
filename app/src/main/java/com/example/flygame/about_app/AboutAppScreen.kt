package com.example.flygame.about_app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.flygame.R

@Composable
fun AboutAppScreen(stateAboutApp: MutableState<Boolean>) {
    val uriHandler = LocalUriHandler.current

    Dialog(
        onDismissRequest = {
            stateAboutApp.value = false
        }
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {

            Column(
                modifier = Modifier.padding(17.dp)
            ) {
                Text(text = stringResource(R.string.about_app), fontSize = 30.sp)

                Spacer(Modifier.height(15.dp))

                Text (text = "Многие идеи по повышению сложности игры я подчеркнул, проходя курсы Advance. " +
                        "Advance - это образовательный центр, который преподаёт технологии эффективного обучения." +
                        "Если вы заинтересовались, то можете ознакомиться с их курсами по следующей ссылке:",
                    fontSize = 18.sp)


                ClickableText(
                    text = AnnotatedString("Курсы Advance"),
                    onClick = {uriHandler.openUri("https://ya.ru/")},
                    style = TextStyle(
                        color = colorResource(id = R.color.light_sky_blue),
                        fontSize = 18.sp,
                        fontFamily = FontFamily.SansSerif,
                        textDecoration = TextDecoration.Underline,
                    )
                )

                Spacer(Modifier.height(15.dp))

                Text (text = stringResource(R.string.other_apps),
                    fontSize = 20.sp)

                ClickableText(
                    text = AnnotatedString(stringResource(R.string.schulte_table)),
                    onClick = {uriHandler.openUri("https://play.google.com/store/apps/details?id=ru.schultetabledima.schultetable")},
                    style = TextStyle(
                        color = colorResource(id = R.color.light_sky_blue),
                        fontSize = 18.sp,
                        fontFamily = FontFamily.SansSerif,
                        textDecoration = TextDecoration.Underline,
                    )
                )
            }
        }
    }
}