package com.codingboy.composely

import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.codingboy.composely.ui.capture.CapturingProgress
import com.codingboy.composely.ui.capture.capture
import com.codingboy.composely.ui.capture.rememberCaptureState
import com.codingboy.composely.ui.extensions.saveAsFile
import com.codingboy.composely.ui.extensions.toBitmap
import com.codingboy.composely.ui.theme.ComposelyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposelyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val captureState = rememberCaptureState()
                    captureState.capture()
                    LaunchedEffect(key1 = captureState.progress) {
                        when (captureState.progress) {
                            is CapturingProgress.Captured -> {
                                val picture =
                                    (captureState.progress as CapturingProgress.Captured).picture
                                val file = picture.toBitmap().saveAsFile(
                                    Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES)
                                        .toString()
                                )
                                Log.i("MainActivity", "captured file name: $file")
                            }

                            else -> Unit
                        }
                    }
                    Greeting(
                        name = "Android",
                        modifier = Modifier
                            .padding(innerPadding)
                            .capture(captureState)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color.LightGray)
    ) {
        Text(
            text = "Hello $name!"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposelyTheme {
        Greeting("Android")
    }
}