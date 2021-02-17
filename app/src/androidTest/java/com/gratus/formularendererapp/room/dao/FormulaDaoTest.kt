package com.gratus.formularendererapp.room.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.gratus.formularendererapp.getOrAwaitValue
import com.gratus.formularendererapp.model.common.Formula
import com.gratus.formularendererapp.room.db.FormulaDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class FormulaDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: FormulaDatabase
    private lateinit var formulaDao: FormulaDao

    @Before
    fun initDB() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FormulaDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        formulaDao = database.formulaDao()
    }

    @After
    fun closeDB() {
        database.close()
    }

    @Test
    fun insertFormulaTest() = runBlockingTest {
        val formulaItem = Formula(
            "a2",
            "/files/Pictures/share_image.png",
            "https://wiki.api/api/rest_v1/media/math/render/png/{imageCache}",
            "2021-02-15 10:02:46.247000",
            1
        )
        formulaDao.insert(formulaItem)
        val checkFormulaItems = formulaDao.getFormulaCheck("a2")
        assertThat(checkFormulaItems.get(0).formulaText.toString()).contains("a2")
    }

    @Test
    fun updateFormulaTest() = runBlockingTest {
        val formulaItem = Formula(
            "a3",
            "/files/Pictures/share_image.png",
            "https://wiki.api/api/rest_v1/media/math/render/png/{imageCache}",
            "2021-02-15 10:02:46.247000",
            1
        )
        formulaDao.insert(formulaItem)
        formulaDao.updateFormulaTimeStamp("2021-02-16 10:02:46.247000", "a3")
        assertThat(
            formulaDao.getFormulaCheck("a3").get(0).lastUpdated
        ).contains("2021-02-16 10:02:46.247000")
    }

    @Test
    fun recentFormulaTest() = runBlockingTest {
        val formulaItem = Formula(
            "a4",
            "/files/Pictures/share_image.png",
            "https://wiki.api/api/rest_v1/media/math/render/png/{imageCache}",
            "2021-02-15 10:02:46.247000",
            1
        )
        val formulaItem1 = Formula(
            "a5",
            "/files/Pictures/share_image.png",
            "https://wiki.api/api/rest_v1/media/math/render/png/{imageCache}",
            "2021-02-16 10:02:46.247000",
            2
        )
        val formulaItem2 = Formula(
            "a6",
            "/files/Pictures/share_image.png",
            "https://wiki.api/api/rest_v1/media/math/render/png/{imageCache}",
            "2021-02-17 10:02:46.247000",
            3
        )
        formulaDao.insert(formulaItem)
        formulaDao.insert(formulaItem1)
        formulaDao.insert(formulaItem2)
        val recentFormulaItems = formulaDao.getAllRecent10Formula().getOrAwaitValue()
        assertThat(recentFormulaItems.size).isGreaterThan(0)
    }

    @Test
    fun searchFormulaTest() = runBlockingTest {
        val formulaItem = Formula(
            "a4",
            "/files/Pictures/share_image.png",
            "https://wiki.api/api/rest_v1/media/math/render/png/{imageCache}",
            "2021-02-15 10:02:46.247000",
            1
        )
        val formulaItem1 = Formula(
            "a5",
            "/files/Pictures/share_image.png",
            "https://wiki.api/api/rest_v1/media/math/render/png/{imageCache}",
            "2021-02-16 10:02:46.247000",
            2
        )
        val formulaItem2 = Formula(
            "a6",
            "/files/Pictures/share_image.png",
            "https://wiki.api/api/rest_v1/media/math/render/png/{imageCache}",
            "2021-02-17 10:02:46.247000",
            3
        )
        formulaDao.insert(formulaItem)
        formulaDao.insert(formulaItem1)
        formulaDao.insert(formulaItem2)
        val searchFormulaItems = formulaDao.getSearchFormula("a").getOrAwaitValue()
        assertThat(searchFormulaItems.size).isEqualTo(3)
    }
}