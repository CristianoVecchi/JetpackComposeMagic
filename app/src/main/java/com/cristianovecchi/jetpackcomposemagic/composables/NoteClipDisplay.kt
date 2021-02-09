package com.cristianovecchi.jetpackcomposemagic.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun NoteClipDisplay(noteClips: List<Clip>, cursor: Int = -1,
                    nCols: Int = 6, dispatch: (Int) -> Unit) {

    val selectionBackColor = Color.White
    val selectionTextColor = Color.Red
    val selectionBorderColor = Color.Black
    val unselectionBackColor = Color.LightGray
    val unselectionTextColor = Color.Blue
    val unselectionBorderColor = Color.DarkGray
    if (noteClips.isEmpty()){
        Text(text= "ENTER SOME NOTES", modifier = Modifier.padding(16.dp))
    } else {
        val nRows = (noteClips.size / nCols) + 1
        var index = 0
        Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,) {
            for(i in 0 until nRows){
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start ) {
                    for(j in 0 until nCols) {
                        if (index != noteClips.size) {
                            val clip = noteClips[index]

                                Card(modifier = Modifier.background(Color.White).
                                                clip(RoundedCornerShape(6.dp)).padding(6.dp).clickable {dispatch(clip.id)},

                                        backgroundColor = if (cursor == index) selectionBackColor else unselectionBackColor,
                                        contentColor = if (cursor == index) selectionTextColor else unselectionTextColor,
                                        border = BorderStroke(2.dp, if (cursor == index) selectionBorderColor else unselectionBorderColor),
                                        elevation = if (cursor == index) 4.dp else 4.dp
                                    )
                                {
                                    Text(text = clip.text, modifier = Modifier.padding(18.dp),
                                            style = TextStyle(fontSize = TextUnit.Companion.Sp(if (cursor == index) 20 else 20),
                                            fontWeight = if (cursor == index) FontWeight.Bold else FontWeight.Normal))
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