package com.gratus.formularendererapp.view.activity

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.gratus.formularendererapp.BuildConfig
import com.gratus.formularendererapp.R
import com.gratus.formularendererapp.databinding.ActivityFormulaBinding
import com.gratus.formularendererapp.model.common.Formula
import com.gratus.formularendererapp.util.Interfaces.SearchListListerner
import com.gratus.formularendererapp.util.constants.ServiceConstants
import com.gratus.formularendererapp.view.adapter.RecentAdapter
import com.gratus.formularendererapp.view.adapter.SearchAdapter
import com.gratus.formularendererapp.view.base.BaseActivity
import com.gratus.formularendererapp.viewModel.activity.FormulaViewModel
import kotlinx.android.synthetic.main.activity_formula.view.*
import java.io.*
import java.util.*
import javax.inject.Inject


class FormulaActivity : BaseActivity(), SearchListListerner {
    lateinit var activityFormulaBinding: ActivityFormulaBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var formulaViewModel: FormulaViewModel

    @Inject
    lateinit var searchAdapter: SearchAdapter

    @Inject
    lateinit var recentAdapter: RecentAdapter

    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mLayoutManagerRecent: LinearLayoutManager

    var url: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityFormulaBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_formula
        )
        formulaViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(FormulaViewModel::class.java)
        activityFormulaBinding.formulaViewModel = formulaViewModel
        activityFormulaBinding.lifecycleOwner = this
        networkCheck(activityFormulaBinding.parentRL)
        activityFormulaBinding.goBt.setOnClickListener(View.OnClickListener { view -> checkFormula() })
        activityFormulaBinding.clearBt.setOnClickListener(View.OnClickListener { view -> clearTextonClick() })
//        activityFormulaBinding.moreTv.setOnClickListener(View.OnClickListener { view -> intentMoreActivity() })
        recentList()
        //observe for formula checking result and get cache id to hit another service to get rendered image
        formulaViewModel.checkResponse.observe(this, Observer {
            if (it.success) {
                if (it.checkHeader.size > 0) {
                    if (it.checkHeader.get("x-resource-location") != null) {
                        url =
                            ServiceConstants.BASE_URL + ServiceConstants.IMAGE_URL_GLIDE + it.checkHeader.get(
                                "x-resource-location"
                            )
                        setFormulaImage(url)
                    }
                }
            } else {
                Toast.makeText(
                    this@FormulaActivity,
                    "Check your formula", Toast.LENGTH_SHORT
                ).show()
            }
        })

        activityFormulaBinding.formulaEt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (count > 0) {
                    activityFormulaBinding.imageRl.visibility = View.GONE
                    recentVisibilty(View.GONE)
                    searchList(s.toString())
                } else {
                    recentList()
                    activityFormulaBinding.searchRecV.visibility = View.GONE
                }
            }
        })
        setSearchRecentAdapter()
    }

    private fun searchList(toString: String) {
        formulaViewModel.searchResult(toString).observe(this, Observer {
            if (it.size > 0) {
                if (activityFormulaBinding.imageRl.visibility == View.GONE) {
                    activityFormulaBinding.searchRecV.visibility = View.VISIBLE
                    formulaViewModel.searchResponse.clear()
                    formulaViewModel.searchResponse.addAll(it)
                    updateSearch()
                }
            } else {
                activityFormulaBinding.searchRecV.visibility = View.GONE
            }
        })
    }

    private fun recentList() {
        formulaViewModel.recentResult().observe(this, Observer {
            if (it.size > 0) {
                recentVisibilty(View.VISIBLE)
                formulaViewModel.recentResponse.clear()
                formulaViewModel.recentResponse.addAll(it)
                updateRecent()
            } else {
                recentVisibilty(View.GONE)
            }
        })
    }

    //set rendered image using glide and store in cache and local storage for sharing and use when require in offline
    private fun setFormulaImage(url: String) {
        activityFormulaBinding.imageRl.visibility = View.VISIBLE
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
//        formulaViewModel.getImage(cacheImage)
//        var bitmap: Bitmap? = null
//        formulaViewModel.response.observe(this, Observer {
//            val inputStream: InputStream = it.byteStream()
//            bitmap = BitmapFactory.decodeStream(inputStream)
//        })
//        activityFormulaBinding.formulaImg.setOnClickListener(View.OnClickListener {
//            bitmap?.let { intentShareActivity(it) }
//        })
        Glide.with(this)
            .asBitmap()
            .load(url)
            .apply(requestOptions)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    activityFormulaBinding.formulaImg.setImageBitmap(resource)
                    activityFormulaBinding.moreTv.visibility = View.VISIBLE
                    activityFormulaBinding.shareImg.visibility = View.VISIBLE
                    val uri: Uri = getUriImageFromBitmap(resource, this@FormulaActivity) ?: return
                    formulaViewModel.checkFormula(url, uri)
                    recentList()
                    activityFormulaBinding.shareImg.setOnClickListener(View.OnClickListener {
                        intentShareActivity(uri)
                    })
                    activityFormulaBinding.moreTv.setOnClickListener(View.OnClickListener {
                        intentOpenActivity(uri)
                    })
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

    //on click go button to check formula using wiki api and internal formula validation
    private fun checkFormula() {
        setupPermissions()
        if (isNetworkConnected()) {
            if (formulaViewModel.checkRequest.isFormulaValid()) {
                formulaViewModel.hitCheckForumla()
            } else {
                Toast.makeText(
                    this@FormulaActivity,
                    "Check your formula", Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            showSnack(false, activityFormulaBinding.parentRL)
        }
    }

    //from bitmap get uri to store image for sharing
    private fun getUriImageFromBitmap(bmp: Bitmap, context: Context): Uri? {
        if (bmp == null) return null
        var bmpUri: Uri? = null
        try {
            val file = File(
                this.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "IMG_" + System.currentTimeMillis() + ".png"
            )
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
            bmpUri =
                FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }

    //sharing image to other application
    fun intentShareActivity(uri: Uri) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Share Via")
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.type = "image/png"
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(Intent.createChooser(shareIntent, "Share image using"))
    }

    //opening image in other application
    fun intentOpenActivity(uri: Uri) {
        val openintent = Intent(Intent.ACTION_VIEW)
        openintent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        openintent.setDataAndType(uri, "image/png")
        openintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(openintent)
    }
//    private fun intentMoreActivity() {
//        val intent = Intent(this, MoreActivity::class.java)
//        intent.putExtra("formulaText", formulaViewModel.checkRequest.formula.toString())
//        startActivity(intent)
//    }

    private fun clearTextonClick() {
        activityFormulaBinding.moreTv.visibility = View.INVISIBLE
        activityFormulaBinding.shareImg.visibility = View.INVISIBLE
        activityFormulaBinding.imageRl.visibility = View.GONE
        activityFormulaBinding.formulaEt.text?.clear()
    }

    //item click for recent and search list and get from room db and cache
    override fun onItemClick(formula: Formula) {
        activityFormulaBinding.searchRecV.visibility = View.GONE
        activityFormulaBinding.formulaEt.text?.clear()
        activityFormulaBinding.formulaEt.append(formula.formulaText)
        activityFormulaBinding.formulaTv.text =
            formula.formulaText + resources.getString(R.string.render)
        setFormulaImage(formula.formulaUrl)
    }

    //seting of adapters search and recent
    private fun setSearchRecentAdapter() {
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        activityFormulaBinding.searchRecV.layoutManager = mLayoutManager
        activityFormulaBinding.searchRecV.itemAnimator = DefaultItemAnimator()
        activityFormulaBinding.searchRecV.adapter = searchAdapter
        searchAdapter.setmListener(this)
        mLayoutManagerRecent.orientation = LinearLayoutManager.VERTICAL
        activityFormulaBinding.recentRecV.layoutManager = mLayoutManagerRecent
        activityFormulaBinding.recentRecV.itemAnimator = DefaultItemAnimator()
        activityFormulaBinding.recentRecV.adapter = recentAdapter
        recentAdapter.setmListener(this)
    }

    private fun updateSearch() {
        searchAdapter.addItems(formulaViewModel.searchResponse)
    }

    private fun updateRecent() {
        recentAdapter.addItems(formulaViewModel.recentResponse)
    }

    private fun recentVisibilty(visiblity: Int) {
        activityFormulaBinding.headerTv.visibility = visiblity
        activityFormulaBinding.recentRecV.visibility = visiblity
    }
}