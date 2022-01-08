package com.example.flygame.gamefield

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.widget.AppCompatTextView
import com.example.flygame.settings.PreferencesReader

class TableCreator(val context: Context) {

    private lateinit var field: TableLayout
    private var tableSize: Int
    private var rows: Array<TableRow?>
    private lateinit var cells: Array<Array<AppCompatTextView?>>

    init {
        tableSize = PreferencesReader.tableSize
        rows = Array(tableSize) { null }

        cells = Array(tableSize) { Array(tableSize) { null } }

        startCreate()
    }

    private fun startCreate() {
        field = TableLayout(context)

        field.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )



        for (i in 0 until tableSize) {
            rows[i] = TableRow(context)
            rows[i]?.layoutParams =
                TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 0, 1F)
            field.addView(rows[i])

        }

        for (i in 0 until tableSize)
            for (j in 0 until tableSize) {
                cells[i][j] = AppCompatTextView(context)

                cells[i][j]?.text = "1"
                cells[i][j]?.gravity = Gravity.CENTER
                cells[i][j]?.textSize = 50F
                cells[i][j]?.setBackgroundColor(Color.BLUE)

                val layoutParams = TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.MATCH_PARENT, 1F
                )
                layoutParams.setMargins(1, 1, 1, 1)

                cells[i][j]?.layoutParams = layoutParams


                cells[i][j]?.setOnClickListener(OnClick)


                rows[i]?.addView(cells[i][j])
            }

    }


    fun getGameField(): TableLayout {
        return field
    }
}

object OnClick : View.OnClickListener {
    override fun onClick(v: View?) {
        println("Click")
    }

}
