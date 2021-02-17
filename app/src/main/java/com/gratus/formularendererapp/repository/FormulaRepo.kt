package com.gratus.formularendererapp.repository

import androidx.lifecycle.LiveData
import com.gratus.formularendererapp.model.common.Formula
import com.gratus.formularendererapp.room.dao.FormulaDao
import com.gratus.formularendererapp.room.db.FormulaDatabase
import com.gratus.formularendererapp.service.FormulaService

class FormulaRepo(private var formulaDatabase: FormulaDatabase): FormulaService {
    private var formulaDao: FormulaDao = formulaDatabase.formulaDao()

    //repo for search result from room  for edit text in offline
    override fun getRecentFormula10(): LiveData<List<Formula>> {
        return formulaDao.getAllRecent10Formula()
    }

    //repo for recent result from room of formulas to show the main page
    override fun getSearchFormula(formulaText: String): LiveData<List<Formula>> {
        return formulaDao.getSearchFormula(formulaText)
    }

    //repo for check formula is there in room db or not and then insert or update accordingly
    override fun insertOrUpdate(formula: Formula) {
        return formulaDao.insertOrUpdate(formula)
    }

}