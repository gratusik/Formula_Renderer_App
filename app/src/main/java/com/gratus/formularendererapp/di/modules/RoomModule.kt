package com.gratus.formularendererapp.di.modules

import android.app.Application
import androidx.room.Room
import com.gratus.formularendererapp.repository.FormulaRepo
import com.gratus.formularendererapp.room.dao.FormulaDao
import com.gratus.formularendererapp.room.db.FormulaDatabase
import com.gratus.formularendererapp.util.constants.AppConstants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule(mApplication: Application) {
    private var formulaApplication = mApplication
    private lateinit var formulaDatabase: FormulaDatabase

    @Singleton
    @Provides
    fun providesFormulaDatabase(): FormulaDatabase {
        formulaDatabase = Room.databaseBuilder(
            formulaApplication,
            FormulaDatabase::class.java,
            AppConstants.FORMULA_DB
        )
            .build()
        return formulaDatabase
    }

    @Singleton
    @Provides
    fun providesFormulaRepo(formulaDatabase: FormulaDatabase): FormulaRepo {
        return FormulaRepo(formulaDatabase)
    }

    @Singleton
    @Provides
    fun providesFormulaDao(formulaDatabase: FormulaDatabase): FormulaDao {
        return formulaDatabase.formulaDao()
    }
}