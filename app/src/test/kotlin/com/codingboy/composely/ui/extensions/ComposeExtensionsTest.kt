package com.codingboy.composely.ui.extensions

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.junit.Assert
import org.junit.Test

class ComposeExtensionsTest {

    @Test
    fun applyIfAppliesModifierWhenConditionIsTrue() {
        val originalModifier = Modifier
        val newModifier = Modifier.padding(10.dp)

        val result = originalModifier.applyIf(true, { newModifier })
        Assert.assertNotEquals(result, originalModifier)
    }

    @Test
    fun applyIfDoesNotApplyModifierWhenConditionIsFalse() {
        val originalModifier = Modifier
        val newModifier = Modifier.padding(10.dp)

        val result = originalModifier.applyIf(false, { newModifier })

        Assert.assertEquals(originalModifier, result)
    }

    @Test
    fun applyIfAppliesWhenFalseModifierWhenConditionIsFalse() {
        val originalModifier = Modifier
        val newModifier = Modifier.padding(10.dp)

        val result = originalModifier.applyIf(false, { originalModifier }, { newModifier })

        Assert.assertEquals(newModifier, result)
    }

    @Test
    fun applyIfDoesNotApplyWhenFalseModifierWhenConditionIsTrue() {
        val originalModifier = Modifier
        val newModifier = Modifier.padding(10.dp)

        val result = originalModifier.applyIf(true, { newModifier }, { originalModifier })

        Assert.assertEquals(newModifier, result)
    }
}