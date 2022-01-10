package com.example.flygame.gamefield

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.example.flygame.settings.PreferencesReader

class TableCreator(val context: Context, val viewModel: GameViewModel) {

    private lateinit var field: TableLayout
    private var tableSize: Int
    private var rows: Array<TableRow?>
    private var cells: Array<Array<AppCompatTextView?>>

    init {
        tableSize = PreferencesReader.tableSize
        rows = Array(tableSize + 1) { null }
        cells = Array(tableSize + 1) { Array(tableSize + 1) { null } }

        startCreate()
    }

    private fun startCreate() {
        field = TableLayout(context)

        field.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )



        for (i in 0..tableSize) {
            rows[i] = TableRow(context)
            rows[i]?.layoutParams =
                TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 0, 1F)
            field.addView(rows[i])
        }

        for (y in 0..tableSize) {
            for (x in 0..tableSize) {
                cells[y][x] = AppCompatTextView(context)

                cells[y][x]?.text = "1"
                cells[y][x]?.gravity = Gravity.CENTER
                cells[y][x]?.textSize = 50F
                cells[y][x]?.setBackgroundColor(Color.BLUE)

                val layoutParams = TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.MATCH_PARENT, 1F
                )
                layoutParams.setMargins(1, 1, 1, 1)

                cells[y][x]?.layoutParams = layoutParams
                cells[y][x]?.setOnClickListener(onClick())


                try {
                    Log.d("tagTag12332112", "$y$x")
                    cells[y][x]?.id = ("$y$x").toInt()      //y и x - расположение ячейки -> id
                } catch (nfe: NumberFormatException) {
                    Toast.makeText(context, nfe.message, Toast.LENGTH_LONG).show()
                }
                Log.d("tagTag1233211", "---------${cells[y][x]?.id}--------")
                Log.d("tagTag1233211", "y = $y  x = $x")

                rows[y]?.addView(cells[y][x])

            }
        }
    }

    private fun onClick() = View.OnClickListener {
        viewModel.cellClickListener(it.id)
        Log.d("tagTag123321", "---------${it.id}--------")

    }


    fun getGameField() = field
}
