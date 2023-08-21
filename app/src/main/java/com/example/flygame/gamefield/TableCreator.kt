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

class TableCreator(private val context: Context, private val viewModel: GameViewModel) {

    private lateinit var field: TableLayout
    private var tableSize: Int
    private var rows: Array<Array<TableRow?>>                               //двухмерный массив
    private var cells: Array<Array<Array<AppCompatTextView?>>>              //трехмерный массив для ячеек

    init {
        tableSize = PreferencesReader.tableSize
        rows = Array(tableSize + 1) { Array(tableSize + 1) { null } }
        cells = Array(tableSize + 1) { Array(tableSize + 1) { Array(tableSize + 1) { null } } }

//        var cells2: Array<Array<Array<AppCompatTextView?>>> = Array(tableSize + 1) { Array(++tableSize) { Array(++tableSize) { null } }}


        startCreate()
    }

    private fun startCreate() {
        field = TableLayout(context)

        field.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )


        for (z in 0..tableSize){
            for (y in 0..tableSize) {
                rows[z][y] = TableRow(context)
                rows[z][y]?.layoutParams =
                    TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 0, 1F)
                field.addView(rows[z][y])

                if(z == 0) rows[z][y]?.setBackgroundColor(Color.BLUE)
                if(z == 1) rows[z][y]?.setBackgroundColor(Color.BLACK)
                if(z == 2) rows[z][y]?.setBackgroundColor(Color.RED)
            }
        }

        for (z in 0..tableSize) {
            for (y in 0..tableSize) {
                for (x in 0..tableSize) {
                    cells[z][y][x] = AppCompatTextView(context)


                    cells[z][y][x]?.gravity = Gravity.CENTER
                    cells[z][y][x]?.textSize = 25F
//                    cells[z][y][x]?.setBackgroundColor(Color.BLUE)

                    val layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.MATCH_PARENT, 1F
                    )
                    layoutParams.setMargins(1, 1, 1, 1)

                    cells[z][y][x]?.layoutParams = layoutParams
                    cells[z][y][x]?.setOnClickListener(onClick())


                    try {
                        cells[z][y][x]?.id = ("$z$y$x").toInt()      //z, y и x - расположение ячейки -> id
                    } catch (nfe: NumberFormatException) {
                        Toast.makeText(context, nfe.message, Toast.LENGTH_LONG).show()
                    }
                    Log.d("tagTag1233211", "---------${cells[z][y][x]?.id}--------")

                    cells[z][y][x]?.text = cells[z][y][x]?.id.toString()

                    rows[z][y]?.addView(cells[z][y][x])

                }
            }
        }
    }

    private fun onClick() = View.OnClickListener {
        viewModel.cellClickListener(it.id)
        Log.d("tagTag123321", "---------${it.id}--------")

    }


    fun getGameField() = field
}
