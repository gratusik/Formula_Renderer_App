package com.gratus.formularendererapp.util.bindingUtil

import android.text.TextWatcher
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


object BindingUtils {
    @JvmStatic
    @BindingAdapter("errorText")
    fun setErrorMessage(
        textInputLayout: TextInputLayout,
        strOrResId: Any?
    ) {
        if (strOrResId is Int) {
            textInputLayout.error = textInputLayout.context.getString((strOrResId as Int?)!!)
        } else {
            textInputLayout.error = strOrResId as String?
        }
    }

    @JvmStatic
    @BindingAdapter("textChangedListener")
    fun bindTextWatcher(editText: TextInputEditText, textWatcher: TextWatcher?) {
        editText.addTextChangedListener(textWatcher)
    }

    @JvmStatic
    @BindingAdapter("textenabled")
    fun setTextEnabled(editText: TextInputEditText, enabled: Boolean) {
        editText.isEnabled = enabled
    }

    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(imageView: ImageView, formalaDir: String) {
//        val myBitmap = BitmapFactory.decodeFile(formalaDir)
//        imageView.setImageBitmap(myBitmap)
        Glide.with(imageView.context)
            .asBitmap()
            .load(formalaDir)
            .into(imageView)
    }
//
//    @JvmStatic
//    @BindingAdapter("searchListAdapter")
//    fun addSearchItems(
//        recyclerView: RecyclerView,
//        formula: ArrayList<Formula>
//    ) {
//        val adapter: SearchAdapter = recyclerView.adapter as SearchAdapter
//        if (formula.size > 0) {
//            adapter.clearItems()
//        }
//        if (formula.size > 0) {
//            adapter.addItems(formula)
//        }
//    }
}