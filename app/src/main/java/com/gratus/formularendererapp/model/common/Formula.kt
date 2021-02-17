package com.gratus.formularendererapp.model.common

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "formula_table")
data class Formula(
    var formulaText: String,
    val formulaDir: String,
    val formulaUrl: String,
    val lastUpdated: String,
    @PrimaryKey(autoGenerate = false) val id: Int? = null
)