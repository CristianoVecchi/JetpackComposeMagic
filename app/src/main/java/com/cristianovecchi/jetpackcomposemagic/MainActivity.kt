package com.cristianovecchi.jetpackcomposemagic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.tooling.preview.Preview
import com.cristianovecchi.jetpackcomposemagic.composables.*
import com.cristianovecchi.jetpackcomposemagic.ui.JetpackComposeMagicTheme
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeMagicTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
//                    Column(modifier = Modifier
//                            .fillMaxSize()) {
//                        ComputationalPerformer(transpose = 4, repeat = 1, shuffle = true)
//                    }
                    AbstractNoteSequenceEditor(res_done = R.drawable.ic_baseline_done_24)
                }
            }
        }
    }
}