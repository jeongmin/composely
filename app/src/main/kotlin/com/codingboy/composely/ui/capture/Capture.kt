package com.codingboy.composely.ui.capture

import android.graphics.Picture
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import com.codingboy.composely.ui.extensions.applyIf


/**
 * Represents the different states of the capturing process.
 */
sealed class CapturingProgress {
    /**
     * Represents the idle state of the capturing process.
     */
    data object Idle : CapturingProgress()

    /**
     * Represents the capturing state of the capturing process.
     */
    data object Capturing : CapturingProgress()

    /**
     * Represents the captured state of the capturing process.
     * @property picture The captured snapshot.
     */
    data class Captured(val picture: Picture) : CapturingProgress()

    /**
     * Represents the error state of the capturing process.
     * @property exception The exception that occurred during the capturing process.
     */
    data class Error(val exception: Throwable) : CapturingProgress()
}

/**
 * Defines the contract for managing the capturing process.
 */
interface CaptureState {
    /**
     * The current state of the capturing process.
     */
    var progress: CapturingProgress

    /**
     * Starts the capturing process.
     */
    fun capture()
}

/**
 * An implementation of the [CaptureState] interface.
 */
class CaptureStateImpl : CaptureState {
    /**
     * The current state of the capturing process.
     */
    override var progress: CapturingProgress by mutableStateOf(CapturingProgress.Idle)

    /**
     * Starts the capturing process.
     */
    override fun capture() {
        progress = CapturingProgress.Capturing
    }
}

/**
 * Creates and remembers a [CaptureState] object.
 * @return The created [CaptureState] object.
 */
@Composable
fun rememberCaptureState(): CaptureState {
    return remember { CaptureStateImpl() }
}

/**
 * Captures a snapshot when the [state] is [CapturingProgress.Capturing].
 * @param state The [CaptureState] object.
 * @return A new [Modifier] that captures a snapshot.
 */
fun Modifier.capture(state: CaptureState): Modifier {
    return this.applyIf(state.progress == CapturingProgress.Capturing, {
        try {
            drawWithCache {
                val width = this.size.width.toInt()
                val height = this.size.height.toInt()

                onDrawWithContent {
                    val picture = Picture()

                    val pictureCanvas = Canvas(picture.beginRecording(width, height))
                    draw(this, this.layoutDirection, pictureCanvas, this.size) {
                        this@onDrawWithContent.drawContent()
                    }
                    picture.endRecording()

                    drawIntoCanvas { canvas -> canvas.nativeCanvas.drawPicture(picture) }
                    state.progress = CapturingProgress.Captured(picture)
                }
            }
        } catch (e: Exception) {
            state.progress = CapturingProgress.Error(e)
            this
        }
    })
}