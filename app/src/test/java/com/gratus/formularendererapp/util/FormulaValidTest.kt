package com.gratus.formularendererapp.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class FormulaValidTest {

    @Test
    fun `empty formula text`() {
        val result = FormulaValid.isFormulaValid("")
        assertThat(result).isFalse()
    }

    @Test
    fun `formula text length greater equal to one and matches`() {
        val result = FormulaValid.isFormulaValid("a")
        assertThat(result).isTrue()
    }

    @Test
    fun `formula text length greater equal to one and not matches`() {
        val result = FormulaValid.isFormulaValid("@")
        assertThat(result).isFalse()
    }
}