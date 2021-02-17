package com.gratus.formularendererapp.viewModel.activity

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.common.truth.ExpectFailure.assertThat
import com.google.common.truth.Truth
import com.gratus.formularendererapp.model.common.Formula
import com.gratus.formularendererapp.model.request.CheckRequest
import com.gratus.formularendererapp.model.response.CheckResponse
import com.gratus.formularendererapp.service.CheckService
import com.gratus.formularendererapp.service.FormulaService
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable.just
import io.reactivex.Single
import org.junit.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response
import retrofit2.http.Header
import java.util.*

class FormulaViewModelTest {

//    @Rule
//    var testSchedulerRule = RxImmediateSchedulerRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var formulaService: FormulaService

    @Mock
    lateinit var checkService: CheckService


    lateinit var formulaViewModel: FormulaViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        formulaViewModel = FormulaViewModel(checkService, formulaService)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `Get Recent 10 data from room db`() {
        //Setting how up the mock behaves
        var formula: MutableLiveData<List<Formula>> = MutableLiveData<List<Formula>>()
        val formulaList: MutableList<Formula> = ArrayList()
        formulaList.add(
            Formula(
                "a6",
                "/files/Pictures/share_image.png",
                "https://wiki.api/api/rest_v1/media/math/render/png/{imageCache}",
                "2021-02-17 10:02:46.247000",
                3
            )
        )
        formula.setValue(formulaList)
        whenever(formulaService.getRecentFormula10()).thenReturn(formula)

        //Fire the test method
        formulaViewModel.recentResult()

        //Check that our live data is updated
        Truth.assertThat(formulaViewModel.recentResult().value?.size).isGreaterThan(0)
    }

    @Test
    fun `Get Search  data from room db`() {
        //Setting how up the mock behaves
        var formula: MutableLiveData<List<Formula>> = MutableLiveData<List<Formula>>()
        val formulaList: MutableList<Formula> = ArrayList()
        formulaList.add(
            Formula(
                "a1",
                "/files/Pictures/share_image.png",
                "https://wiki.api/api/rest_v1/media/math/render/png/{imageCache}",
                "2021-02-17 10:02:46.247000",
                3
            )
        )
        formula.setValue(formulaList)
        whenever(formulaService.getSearchFormula("a")).thenReturn(formula)

        //Fire the test method
        formulaViewModel.searchResult("a")

        //Check that our live data is updated
        Truth.assertThat(formulaViewModel.searchResult("a").value?.size).isGreaterThan(0)
    }
}