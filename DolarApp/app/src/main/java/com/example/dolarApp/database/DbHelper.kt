package com.example.dolarApp.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.dolarApp.database.entities.ValueEntity

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = "CREATE TABLE " +
                "$TABLE_NAME (" +
                "$ID_COL, INTEGER PRIMARY KEY, " +
                "$BUY_COL TEXT, " +
                "$SELL_COL TEXT, " +
                "$BUY_COL_BLU TEXT, " +
                "$SELL_COL_BLU TEXT, $DATE_COL TEXT)"

        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun delete() {
        val query = ("DELETE FROM val_table;")
        val db = this.writableDatabase
        db.execSQL(query)
    }

    fun addValue(item: ValueEntity ){

        val values = ContentValues()

        values.put(BUY_COL, item.buy)
        values.put(SELL_COL, item.sell)
        values.put(BUY_COL_BLU, item.buyBlu)
        values.put(SELL_COL_BLU, item.sellBlu)
        values.put(DATE_COL, item.date)



        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getAllValues(): MutableList<ValueEntity> {
        val db = this.readableDatabase
        var cursor: Cursor? = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        cursor!!.moveToFirst()
        val list: MutableList<ValueEntity> = mutableListOf()
        if (cursor.count < 1) return mutableListOf()

        list.add(ValueEntity(
            buy = cursor.getString(cursor.getColumnIndex(BUY_COL)),
            sell = cursor.getString(cursor.getColumnIndex(SELL_COL)),
            buyBlu = cursor.getString(cursor.getColumnIndex(BUY_COL_BLU)),
            sellBlu = cursor.getString(cursor.getColumnIndex(SELL_COL_BLU)),
            date = cursor.getString(cursor.getColumnIndex(DATE_COL)) ))

        while(cursor.moveToNext()){
            list.add(ValueEntity(
                buy = cursor.getString(cursor.getColumnIndex(DBHelper.BUY_COL)),
                sell = cursor.getString(cursor.getColumnIndex(DBHelper.SELL_COL)),
                buyBlu = cursor.getString(cursor.getColumnIndex(DBHelper.BUY_COL_BLU)),
                sellBlu = cursor.getString(cursor.getColumnIndex(DBHelper.SELL_COL_BLU)),
                date = cursor.getString(cursor.getColumnIndex(DBHelper.DATE_COL)) ))
        }

        cursor.close()

        return list
    }

    companion object{
        private val DATABASE_NAME = "values_db"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "val_table"
        val ID_COL = "id"
        val NAME_COL = "name"
        val BUY_COL = "buy"
        val SELL_COL = "sell"


        val BUY_COL_BLU = "buy_blue"
        val SELL_COL_BLU = "sell_blue"


        val DATE_COL = "fecha"

    }
}