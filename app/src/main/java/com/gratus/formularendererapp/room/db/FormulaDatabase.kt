package com.gratus.formularendererapp.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gratus.formularendererapp.model.common.Formula
import com.gratus.formularendererapp.room.dao.FormulaDao

@Database(entities = [Formula::class], version = 1)
abstract class FormulaDatabase : RoomDatabase() {

    abstract fun formulaDao(): FormulaDao

}