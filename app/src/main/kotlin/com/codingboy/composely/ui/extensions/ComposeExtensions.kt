package com.codingboy.composely.ui.extensions

import androidx.compose.ui.Modifier


/**
 * Conditionally applies a modifier to the current Modifier.
 *
 * This function evaluates a given condition. If the condition is true, it applies a specified modifier to the current Modifier.
 * If the condition is false, it returns the current Modifier without any changes.
 *
 * @param conditional A Boolean value representing the condition to check.
 * @param whenTrue A lambda function with receiver of type Modifier that returns a Modifier. This function is invoked on the current Modifier when the condition is true.
 * @param whenFalse A lambda function with receiver of type Modifier that returns a Modifier. This function is invoked on the current Modifier when the condition is false. By default, it returns the current Modifier without any changes.
 *
 * @return A Modifier which is either the current Modifier with the applied changes (if the condition is true) or the current Modifier without any changes (if the condition is false).
 *
 * @sample
 * Modifier.applyIf(isSelected) {
 *     border(2.dp, Color.Black)
 * }
 * This sample applies a black border to the Modifier if `isSelected` is true. If `isSelected` is false, no changes are made to the Modifier.
 */
inline fun Modifier.applyIf(
    conditional: Boolean,
    whenTrue: Modifier.() -> Modifier,
    whenFalse: Modifier.() -> Modifier = { this }
): Modifier = if (conditional) then(whenTrue(Modifier)) else then(whenFalse(Modifier))