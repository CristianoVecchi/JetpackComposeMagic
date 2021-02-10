package com.cristianovecchi.jetpackcomposemagic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import com.cristianovecchi.jetpackcomposemagic.composables.*
import com.cristianovecchi.jetpackcomposemagic.ui.JetpackComposeMagicTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeMagicTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    var id = 0
                    val names = NoteNamesIt.values().map{it.toString()}
                    Column(modifier = Modifier
                           .fillMaxSize()) {
                            val parts = listOf(
                                    randomClipSequence(names,0,24),
                                    randomClipSequence(names,4,12),
                                    randomClipSequence(names,11,19),
                            )
                            NoteTable(parts)
                    }
//                    Column(modifier = Modifier
//                            .fillMaxSize()) {
//                        ComputationalPerformer(transpose = 4, repeat = 1, shuffle = true)
//                    }

                //                    AbstractNoteSequenceEditor(res_done = R.drawable.ic_baseline_done_24)
                }
            }
        }
    }
}