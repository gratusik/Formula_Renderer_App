package com.gratus.formularendererapp.view.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.gratus.formularendererapp.databinding.ItemSearchRecentBinding
import com.gratus.formularendererapp.model.common.Formula
import com.gratus.formularendererapp.util.Interfaces.SearchListListerner
import com.gratus.formularendererapp.view.activity.FormulaActivity
import com.gratus.formularendererapp.view.base.BaseViewHolder
import com.gratus.formularendererapp.viewModel.adapter.SearchItemViewModel
import javax.inject.Inject

class SearchAdapter @Inject constructor(private var formula: ArrayList<Formula>) :
    RecyclerView.Adapter<BaseViewHolder>() {
    private var mListener: SearchListListerner? = null

    var context: Context? = null

    fun setmListener(mListener: FormulaActivity?) {
        this.mListener = mListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        val itemSearchRecentBinding: ItemSearchRecentBinding = ItemSearchRecentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return SearchListViewHolder(itemSearchRecentBinding)
    }

    private inner class SearchListViewHolder(itemSearchRecentBinding: ItemSearchRecentBinding) :
        BaseViewHolder(itemSearchRecentBinding.root) {
        private val mBinding: ItemSearchRecentBinding = itemSearchRecentBinding
        private var searchItemViewModel: SearchItemViewModel? = null

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onBind(position: Int) {
            if (mListener != null) {
                searchItemViewModel = SearchItemViewModel(formula[position], mListener!!)
            }
            mBinding.searchItemViewModel = searchItemViewModel
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return formula.size
    }


    fun addItems(formulaList: ArrayList<Formula>) {
        if (formulaList.size > 0) {
            formula = ArrayList()
            formula.addAll(formulaList)
            notifyDataSetChanged()
        }
    }

    fun clearItems() {
        formula.clear()
    }
}