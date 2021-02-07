package com.cristianovecchi.jetpackcomposemagic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.tooling.preview.Preview
import com.cristianovecchi.jetpackcomposemagic.composables.*
import com.cristianovecchi.jetpackcomposemagic.ui.JetpackComposeMagicTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeMagicTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    DefaultPreview()


                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposeMagicTheme {
        //val output = remember { mutableStateOf("OUTPUT")}
        val clips: MutableList<Clip> = remember { mutableStateListOf()}
        val cursor = remember { mutableStateOf(-1) }
        val id = remember { mutableStateOf(0) }
        Column {
            //Text(text = output.value)
            NoteClipDisplay(noteClips = clips, cursor = cursor.value,
                    dispatch = { id -> clips.forEachIndexed{index, clip -> if (clip.id == id) cursor.value = index} })
            NoteKeyboard(dispatch = { out, text ->
                    when (out) {
                        is Out.Note -> {
                            if(cursor.value == clips.size-1){
                                cursor.value++
                                clips.add(Clip(text, id.value++ , -1, out.note,Accidents.NATURAL))

                            } else {
                               clips.add(cursor.value ,Clip(text,  id.value++,-1, out.note,Accidents.NATURAL))
                            }
                        }
                        is Out.Accident -> {}
                        is Out.Delete -> {
                                            if(clips.isNotEmpty()) clips.removeAt(cursor.value )
                                            if(cursor.value > 0) cursor.value--
                                            if(clips.isEmpty()) cursor.value = -1
                        }
                        is Out.Forward -> {if (cursor.value < clips.size -1) cursor.value++}
                        is Out.Back -> {if (clips.isNotEmpty() && cursor.value > 0) cursor.value--}
                        is Out.Enter -> {}
                        is Out.Undo -> {}
                    }
                }
            )
            //MagicTabs()
        }
    }
}