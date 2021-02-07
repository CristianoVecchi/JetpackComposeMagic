package com.cristianovecchi.jetpackcomposemagic.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp


@Composable
fun NoteClipDisplay(noteClips: List<Clip>, cursor: Int = -1,
                    nCols: Int = 6, dispatch: (Int) -> Unit) {

    val selectionBackColor = Color.Red
    val selectionTextColor = Color.Black
    val unselectionBackColor = Color.Black
    val unselectionTextColor = Color.Blue
    if (noteClips.isEmpty()){
        Text(text= "ENTER SOME NOTES", modifier = Modifier.padding(16.dp))
    } else {
//            noteClips.map{ print(it.text+" ")}
//            println()
        val nRows = (noteClips.size / nCols) + 1
        var index = 0
        Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,) {
            for(i in 0 until nRows){
                Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start ) {
                    for(j in 0 until nCols) {
                        if (index != noteClips.size) {
                            val clip = noteClips[index]

                                Card(modifier = Modifier.shadow(12.dp).background(Color.Black).
                                                border(2.dp, Color.Red).clip(RoundedCornerShape(4.dp)).padding(4.dp).clickable {dispatch(clip.id)},

                                        backgroundColor = if (cursor == index) selectionBackColor else unselectionBackColor,
                                        contentColor = if (cursor == index) selectionTextColor else unselectionTextColor,
                                          )
                                {
                                    Text(text = clip.text, modifier = Modifier.padding(10.dp),
                                            style = TextStyle(fontSize = TextUnit.Companion.Sp(20)))
                                }
                            index++
                        }
                    }
                }
            }
        }
    }
}

data class Clip(val text: String, val id: Int,
                val abstractNote: Int = -1,
                val name: NoteNamesEn, val ax: Accidents = Accidents.NATURAL)