package com.gratus.formularendererapp.di.modules

import androidx.recyclerview.widget.LinearLayoutManager
import com.gratus.formularendererapp.view.activity.FormulaActivity
import com.gratus.formularendererapp.view.adapter.RecentAdapter
import com.gratus.formularendererapp.view.adapter.SearchAdapter
import dagger.Module
import dagger.Provides

@Module
class FormulaAdapterModule {
    @Provides
    fun provideSearchAdapter(): SearchAdapter {
        return SearchAdapter(ArrayList())
    }

    @Provides
    fun provideRecentAdapter(): RecentAdapter {
        return RecentAdapter(ArrayList())
    }

    @Provides
    fun provideLinearLayoutManager(formulaActivity: FormulaActivity): LinearLayoutManager {
        return LinearLayoutManager(formulaActivity)
    }
}