package com.cristianovecchi.jetpackcomposemagic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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




                //}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

        //val output = remember { mutableStateOf("OUTPUT")}
        val clips: MutableList<Clip> = remember { mutableStateListOf()}
        val cursor = remember { mutableStateOf(-1) }
        val id = remember { mutableStateOf(0) }

            //Text(text = output.value)
    Column(modifier = Modifier.fillMaxHeight()){
        val modifier3 = Modifier
                .fillMaxWidth()
                .weight(3f)
        val modifier4 = Modifier
                .fillMaxWidth()
                .weight(4f)
        Row(modifier3){

        }

        ScrollableColumn(modifier4) {
            NoteClipDisplay(noteClips = clips, cursor = cursor.value,
                    dispatch = { id -> clips.forEachIndexed { index, clip -> if (clip.id == id) cursor.value = index } })
        }

        Row(modifier3) {
            NoteKeyboard(names = NoteNamesEn.values().map{it.toString()},
                        dispatch = { out, text ->
                when (out) {
                    is Out.Note -> {
                        if (cursor.value == clips.size - 1) {
                            cursor.value++
                            clips.add(Clip(text, id.value++, -1, out.note, Accidents.NATURAL))

                        } else {
                            clips.add(cursor.value, Clip(text, id.value++, -1, out.note, Accidents.NATURAL))
                        }
                    }
                    is Out.Accident -> {
                        val oldClip = clips[cursor.value]
                        var newClip: Clip
                        if(oldClip.ax != Accidents.NATURAL) {
                            if(oldClip.text.contains(out.ax.ax)){
                                newClip = Clip(oldClip.text.removeSuffix(out.ax.ax), oldClip.id, oldClip.abstractNote, oldClip.name, Accidents.NATURAL)
                            } else {
                                val noteName = oldClip.text.removeSuffix(oldClip.ax.ax)
                                newClip = Clip(noteName + out.ax.ax, oldClip.id, oldClip.abstractNote, oldClip.name, out.ax)
                            }
                        } else {
                            newClip = Clip(oldClip.text + out.ax.ax, oldClip.id, oldClip.abstractNote, oldClip.name, out.ax)
                        }
                        clips.set(cursor.value, newClip)
                    }
                    is Out.Delete -> {
                        if (clips.isNotEmpty()) clips.removeAt(cursor.value)
                        if (cursor.value > 0) cursor.value--
                        if (clips.isEmpty()) cursor.value = -1
                    }
                    is Out.Forward -> {
                        if (cursor.value < clips.size - 1) cursor.value++
                    }
                    is Out.Back -> {
                        if (clips.isNotEmpty() && cursor.value > 0) cursor.value--
                    }
                    is Out.Enter -> {
                    }
                    is Out.Undo -> {
                    }
                }
            }
            )
        }
    }

}