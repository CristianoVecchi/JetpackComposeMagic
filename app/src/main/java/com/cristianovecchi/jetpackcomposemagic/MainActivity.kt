package com.cristianovecchi.jetpackcomposemagic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import java.util.*

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

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {

        val clips: MutableList<Clip> = remember { mutableStateListOf() }
        val cursor = remember { mutableStateOf(-1) }
        val id = remember { mutableStateOf(0) }
        val lastOutIsNotUndo = remember { mutableStateOf(true) }

        data class Undo(val list: MutableList<Clip>, val cursor: Int)

        val stack: Stack<Undo> = Stack()

        Column(modifier = Modifier.fillMaxHeight()) {
            val modifier3 = Modifier
                    .fillMaxWidth()
                    .weight(3f)
            val modifier4 = Modifier
                    .fillMaxWidth()
                    .weight(4f)
            Row(modifier3) {

            }

            ScrollableColumn(modifier4) {
                NoteClipDisplay(noteClips = clips, cursor = cursor.value,
                        dispatch = { id ->
                            clips.forEachIndexed { index, clip ->
                                if (clip.id == id) {
                                    cursor.value = index
                                    stack.push(Undo(ArrayList<Clip>(clips), cursor.value))
                                    lastOutIsNotUndo.value = true
                                }
                            }
                        })
            }


            Row(modifier3) {
                NoteKeyboard(names = NoteNamesIt.values().map { it.toString() }, res_done = R.drawable.ic_baseline_done_24,
                        dispatch = { out, text ->
                            when (out) {
                                is Out.Note -> {
                                    if (cursor.value == clips.size - 1) {
                                        cursor.value++
                                        clips.add(Clip(text, id.value++, -1, out.note, Accidents.NATURAL))
                                        stack.push(Undo(ArrayList<Clip>(clips), cursor.value))
                                        lastOutIsNotUndo.value = true
                                    } else {
                                        clips.add(cursor.value, Clip(text, id.value++, -1, out.note, Accidents.NATURAL))
                                        stack.push(Undo(ArrayList<Clip>(clips), cursor.value))
                                        lastOutIsNotUndo.value = true
                                    }
                                }
                                is Out.Accident -> {
                                    if (clips.isNotEmpty()) {
                                        val oldClip = clips[cursor.value]
                                        val newClip: Clip
                                        val change: Boolean
                                        if (oldClip.ax != Accidents.NATURAL) {
                                            if (out.ax == Accidents.NATURAL) {
                                                newClip = Clip(oldClip.text.removeSuffix(oldClip.ax.ax), oldClip.id, oldClip.abstractNote, oldClip.name, Accidents.NATURAL)
                                                change = true
                                            } else {
                                                if (oldClip.text.contains(out.ax.ax)) {
                                                    newClip = Clip(oldClip.text.removeSuffix(out.ax.ax), oldClip.id, oldClip.abstractNote, oldClip.name, Accidents.NATURAL)
                                                    change = true
                                                } else {
                                                    val noteName = oldClip.text.removeSuffix(oldClip.ax.ax)
                                                    newClip = Clip(noteName + out.ax.ax, oldClip.id, oldClip.abstractNote, oldClip.name, out.ax)
                                                    change = true
                                                }
                                            }
                                        } else {
                                            if (out.ax == Accidents.NATURAL) {
                                                change = false
                                                newClip = oldClip
                                            } else {
                                                newClip = Clip(oldClip.text + out.ax.ax, oldClip.id, oldClip.abstractNote, oldClip.name, out.ax)
                                                change = true
                                            }
                                        }
                                        if (change) {
                                            clips[cursor.value] = newClip
                                            stack.push(Undo(ArrayList<Clip>(clips), cursor.value))
                                            lastOutIsNotUndo.value = true
                                        }
                                    }
                                }
                                is Out.Delete -> {
                                    var change = false
                                    if (clips.isNotEmpty()) {
                                        clips.removeAt(cursor.value)
                                        change = true
                                    }
                                    if (cursor.value > 0) {
                                        cursor.value--
                                        change = true
                                    }
                                    if (clips.isEmpty()) {
                                        cursor.value = -1
                                        change = true
                                    }
                                    if (change) stack.push(Undo(ArrayList<Clip>(clips), cursor.value))
                                    lastOutIsNotUndo.value = true
                                }
                                is Out.Forward -> {
                                    if (cursor.value < clips.size - 1) {
                                        cursor.value++
                                        stack.push(Undo(ArrayList<Clip>(clips), cursor.value))
                                        lastOutIsNotUndo.value = true
                                    }
                                }
                                is Out.Back -> {
                                    if (clips.isNotEmpty() && cursor.value > 0) {
                                        cursor.value--
                                        stack.push(Undo(ArrayList<Clip>(clips), cursor.value))
                                        lastOutIsNotUndo.value = true
                                    }
                                }
                                is Out.FullForward -> {
                                    if (cursor.value < clips.size - 1) {
                                        cursor.value = clips.size - 1
                                        stack.push(Undo(ArrayList<Clip>(clips), cursor.value))
                                        lastOutIsNotUndo.value = true
                                    }
                                }
                                is Out.FullBack -> {
                                    if (clips.isNotEmpty() && cursor.value > 0) {
                                        cursor.value = 0
                                        stack.push(Undo(ArrayList<Clip>(clips), cursor.value))
                                        lastOutIsNotUndo.value = true
                                    }
                                }
                                is Out.Undo -> {
                                    if (stack.isNotEmpty()) {
                                        if (lastOutIsNotUndo.value) {
                                            stack.pop()
                                            lastOutIsNotUndo.value = false
                                        }
                                        val undo = stack.pop()
                                        clips.clear()
                                        clips.addAll(0, undo.list)
                                        cursor.value = undo.cursor
                                    } else {
                                        clips.clear()
                                        cursor.value = -1
                                    }
                                }
                                is Out.Analysis -> {
                                }
                                is Out.Enter -> {
                                }
                            }
                        }
                )
            }
        }
    }
}