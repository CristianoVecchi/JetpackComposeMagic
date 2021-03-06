package com.cristianovecchi.jetpackcomposemagic.composables

import android.os.Bundle
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import java.util.*


@Composable
fun AbstractNoteSequenceEditor(res_done: Int? = null, done_action: (ArrayList<Clip>) -> Unit) {
    val nClipCols = 3

    val clips: MutableList<Clip> = remember { mutableStateListOf() }
    val cursor = remember { mutableStateOf(-1) }
    val id = remember { mutableStateOf(0) }
    val lastOutIsNotUndo = remember { mutableStateOf(true) }
    val lastIsCursorChanged = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    data class Undo(val list: MutableList<Clip>, val cursor: Int)

    val stack: Stack<Undo> = Stack()

    Column(modifier = Modifier.fillMaxHeight()) {
        val modifier3 = Modifier
                .fillMaxWidth()
                .weight(3f)
        val modifier4 = Modifier
                .fillMaxSize()
                .weight(4f)
        Row(modifier3) {

        }

        ScrollableColumn(modifier4, scrollState = scrollState) {

            NoteClipDisplay(noteClips = clips, cursor = cursor.value, nCols = nClipCols,
                    dispatch = { id ->
                        clips.forEachIndexed { index, clip ->
                            if (clip.id == id) {
                                cursor.value = index
                                stack.push(Undo(ArrayList<Clip>(clips), cursor.value))
                                lastOutIsNotUndo.value = true
                                lastIsCursorChanged.value = true
                            }
                        }
                    })
            if(lastIsCursorChanged.value){

                val nRows = (clips.size / nClipCols)
                val maybeRow = if (clips.size % nClipCols != 0) 1 else 0
                val clipHeight: Float = scrollState.maxValue / nRows.toFloat()
                val cursorRows: Int = (cursor.value / nClipCols)
                val maybeCursorRow = if(cursorRows % nClipCols != 0) 1 else 0
                val limit = (nRows + maybeRow) * nClipCols - nClipCols

                println("Cursor: ${cursor.value}   Limit: $limit   Scroll: ${scrollState.maxValue}")
                scrollState.smoothScrollTo(clipHeight * (cursorRows + maybeCursorRow).toFloat())
//                    when (cursor.value) {
//                        in -1 until nClipCols -> scrollState.smoothScrollTo(0f)
//                        in nClipCols until limit -> scrollState.smoothScrollTo(clipHeight * (cursorRows + maybeCursorRow).toFloat() )
//
//                        else -> {scrollState.smoothScrollTo(scrollState.maxValue); println("LIMIT CONDITION")}
//                    }
                lastIsCursorChanged.value = false
            }


        }


        Row(modifier3) {
            NoteKeyboard(names = NoteNamesIt.values().map { it.toString() }, res_done = res_done,
                    dispatch = { out, text ->
                        when (out) {
                            is Out.Note -> {
                                if (cursor.value == clips.size - 1) {
                                    cursor.value++
                                    clips.add(Clip(text, id.value++, out.note.abs , out.note, Accidents.NATURAL))

                                    stack.push(Undo(ArrayList<Clip>(clips), cursor.value))
                                    lastOutIsNotUndo.value = true
                                    lastIsCursorChanged.value = true

                                } else {
                                    clips.add(cursor.value, Clip(text, id.value++, out.note.abs, out.note, Accidents.NATURAL))
                                    stack.push(Undo(ArrayList<Clip>(clips), cursor.value))
                                    lastOutIsNotUndo.value = true
                                }
                            }
                            is Out.Accident -> {
                                if (clips.isNotEmpty()) {
                                    val oldClip = clips[cursor.value]
                                    val newClip: Clip
                                    val change: Boolean
                                    if (oldClip.ax != Accidents.NATURAL) { // Note has accident
                                        if (out.ax == Accidents.NATURAL) { // Removes the previous accident
                                            newClip = Clip(oldClip.text.removeSuffix(oldClip.ax.ax), oldClip.id, Clip.inAbsRange(oldClip.abstractNote - oldClip.ax.sum), oldClip.name, Accidents.NATURAL)
                                            change = true
                                        } else {
                                            if (oldClip.text.contains(out.ax.ax)) { // Removes the same accident
                                                newClip = Clip(oldClip.text.removeSuffix(out.ax.ax), oldClip.id, Clip.inAbsRange(oldClip.abstractNote - oldClip.ax.sum), oldClip.name, Accidents.NATURAL)
                                                change = true
                                            } else { // Replaces the previous accident with a new one
                                                val noteName = oldClip.text.removeSuffix(oldClip.ax.ax)
                                                newClip = Clip(noteName + out.ax.ax, oldClip.id, Clip.inAbsRange(oldClip.abstractNote - oldClip.ax.sum + out.ax.sum) , oldClip.name, out.ax)
                                                change = true
                                            }
                                        }
                                    } else {
                                        if (out.ax == Accidents.NATURAL) {
                                            change = false
                                            newClip = oldClip
                                        } else {
                                            newClip = Clip(oldClip.text + out.ax.ax, oldClip.id, Clip.inAbsRange(oldClip.abstractNote  + out.ax.sum), oldClip.name, out.ax)
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
                                    lastIsCursorChanged.value = true

                                }
                                if (clips.isEmpty()) {
                                    cursor.value = -1
                                    change = true
                                    lastIsCursorChanged.value = true

                                }
                                if (change) stack.push(Undo(ArrayList<Clip>(clips), cursor.value))
                                lastOutIsNotUndo.value = true
                            }
                            is Out.Forward -> {
                                if (cursor.value < clips.size - 1) {
                                    cursor.value++
                                    stack.push(Undo(ArrayList<Clip>(clips), cursor.value))
                                    lastOutIsNotUndo.value = true
                                    lastIsCursorChanged.value = true

                                }
                            }
                            is Out.Back -> {
                                if (clips.isNotEmpty() && cursor.value > 0) {
                                    cursor.value--
                                    stack.push(Undo(ArrayList<Clip>(clips), cursor.value))
                                    lastOutIsNotUndo.value = true
                                    lastIsCursorChanged.value = true

                                }
                            }
                            is Out.FullForward -> {
                                if (cursor.value < clips.size - 1) {
                                    cursor.value = clips.size - 1
                                    stack.push(Undo(ArrayList<Clip>(clips), cursor.value))
                                    lastOutIsNotUndo.value = true
                                    lastIsCursorChanged.value = true

                                }
                            }
                            is Out.FullBack -> {
                                if (clips.isNotEmpty() && cursor.value > 0) {
                                    cursor.value = 0
                                    stack.push(Undo(ArrayList<Clip>(clips), cursor.value))
                                    lastOutIsNotUndo.value = true
                                    lastIsCursorChanged.value = true

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
                                    lastIsCursorChanged.value = true

                                } else {
                                    clips.clear()
                                    cursor.value = -1
                                    lastIsCursorChanged.value = true

                                }
                            }
                            is Out.Analysis -> {
                            }
                            is Out.Enter -> {
                                val list = ArrayList<Clip>()
                                clips.forEach { list.add(it) }
                                done_action(list)
                            }
                        }
                    }
            )
        }
    }
}
