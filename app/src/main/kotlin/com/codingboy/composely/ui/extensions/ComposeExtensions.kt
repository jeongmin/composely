package com.codingboy.composely.ui.extensions

import androidx.compose.ui.Modifier


/**
 * Applies a modifier to the current Modifier if a given condition is true.
 *
 * This function checks a given condition and if the condition is true, it applies a specified modifier to the current Modifier.
 * If the condition is false, it returns the current Modifier without any changes.
 *
 * @param conditional A Boolean value representing the condition to check.
 * @param modifierWhenTrue A lambda function with receiver of type Modifier that returns a Modifier. This function is invoked on the current Modifier when the condition is true.
 *
 * @return A Modifier which is either the current Modifier with the applied changes (if the condition is true) or the current Modifier without any changes (if the condition is false).
 *
 * @sample
 * Modifier.applyIf(isSelected) {
 *     border(2.dp, Color.Black)
 * }
 * This sample applies a black border to the Modifier if `isSelected` is true.
 */
inline fun Modifier.applyIf(
    conditional: Boolean,
    modifierWhenTrue: Modifier.() -> Modifier,
): Modifier = if (conditional) then(modifierWhenTrue(Modifier)) else this