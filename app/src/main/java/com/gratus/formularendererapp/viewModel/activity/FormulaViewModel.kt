package com.gratus.formularendererapp.viewModel.activity


import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gratus.formularendererapp.model.common.Formula
import com.gratus.formularendererapp.model.request.CheckRequest
import com.gratus.formularendererapp.model.response.CheckResponse
import com.gratus.formularendererapp.repository.CheckRepo
import com.gratus.formularendererapp.repository.FormulaRepo
import com.gratus.formularendererapp.repository.ImageRepo
import com.gratus.formularendererapp.util.DateTimeUtil
import com.gratus.formularendererapp.viewModel.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class FormulaViewModel @Inject constructor(
    private var checkRepo: CheckRepo,
    private var formulaRepo: FormulaRepo,
    private var imageRepo: ImageRepo
) : BaseViewModel() {
    var checkResponse: MutableLiveData<CheckResponse> =
        MutableLiveData<CheckResponse>()
    var response: MutableLiveData<ResponseBody> =
        MutableLiveData<ResponseBody>()
    var checkRequest: CheckRequest = CheckRequest()
    var searchResponse: ArrayList<Formula> = ArrayList()
    var recentResponse: ArrayList<Formula> = ArrayList()


    //Checking formula is correct or not using wiki api
    fun hitCheckForumla() {
        if (checkRequest.isFormulaValid()) {
            compositeDisposable.add(
                checkRepo.checkFormulaResponse(checkRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<Response<CheckResponse>>() {
                        override fun onSuccess(checkResponses: Response<CheckResponse>) {
                            if (checkResponses.body() != null) {
                                checkResponse.value = CheckResponse(
                                    checkResponses.body()!!.success,
                                    checkResponses.headers()
                                )
                            } else {
                                checkResponse.value = CheckResponse(
                                    false,
                                    checkResponses.headers()
                                )
                            }
                        }

                        override fun onError(e: Throwable) {
                            if (e is HttpException) {
                                val response = e.response()
                                try {
                                    val jObjError =
                                        JSONObject(response!!.errorBody()!!.string())
                                    println(jObjError)
                                    checkResponse.value = CheckResponse(
                                        false,
                                        response.headers()
                                    )
                                } catch (ex: JSONException) {
                                    ex.printStackTrace()
                                } catch (ex: IOException) {
                                    ex.printStackTrace()
                                }
                            }
                        }
                    })
            )
        }
    }

    //getting render image formula from wiki api using retrofit additional we can use instead of glide
    fun getImage(cachedir: String) {
        compositeDisposable.add(
            imageRepo.getImage(cachedir)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseBody>() {
                    override fun onSuccess(checkResponses: ResponseBody) {
                        response.value = checkResponses
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "error");
                    }
                })
        )
    }

    //search result from room  for edit text in offline
    fun searchResult(formulaText: String): LiveData<List<Formula>> {
        return formulaRepo.getSearchFormula(formulaText)
    }

    //recent result from room of formulas to show the main page
    fun recentResult(): LiveData<List<Formula>> {
        return formulaRepo.getRecentFormula10()
    }

    //check formula is there in room db or not and then insert or update accordingly
    fun checkFormula(url: String, uri: Uri) {
        GlobalScope.launch {
            formulaRepo.insertOrUpdate(
                Formula(
                    checkRequest.formula.toString(),
                    uri.toString(),
                    url,
                    DateTimeUtil.currentTimeStamp()
                )
            )
        }
    }
}