package com.gratus.formularendererapp.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gratus.formularendererapp.model.common.Formula

@Dao
interface FormulaDao {
    //insert dao new formula
    @Insert
    fun insert(formula: Formula)

    //update dao existing formula
    @Query("Update formula_table SET lastUpdated= :lastUpdated WHERE formulaText = :formulaText")
    fun updateFormulaTimeStamp(lastUpdated: String, formulaText: String)

    //dao to check formula already present or not
    @Query("SELECT * FROM formula_table WHERE formulaText = :formulaText LIMIT 1")
    fun getFormulaCheck(formulaText: String): List<Formula>

    //last 10 searched results of formula based on timestamp
    @Query("SELECT * FROM formula_table order by lastUpdated DESC LIMIT 10")
    fun getAllRecent10Formula(): LiveData<List<Formula>>

    //searched results of formula on type in edit text
    @Query("SELECT * FROM formula_table WHERE formulaText LIKE '%' || :formulaText || '%' order by lastUpdated DESC LIMIT 10")
    fun getSearchFormula(formulaText: String): LiveData<List<Formula>>

    //function for checing formula and then insert/ update in room
    fun insertOrUpdate(formula: Formula) {
        var itemsFromDB: List<Formula> = getFormulaCheck(formula.formulaText)
        if (itemsFromDB.isEmpty()) {
            insert(formula)
        }
        else {
            updateFormulaTimeStamp(formula.lastUpdated, formula.formulaText)
        }

    }
}