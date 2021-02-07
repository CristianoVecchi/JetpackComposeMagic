package com.cristianovecchi.jetpackcomposemagic.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import org.intellij.lang.annotations.JdkConstants


@Composable
fun NoteClipDisplay(noteClips: List<Clip>, cursor: Int = -1, nCols: Int = 6, dispatch: (Int) -> Unit) {
    val selectionBackColor = Color.LightGray
    val selectionTextColor = Color.Black

    val unselectionBackColor = Color.Red
    val unselectionTextColor = Color.Blue
    println("CURSOR: $cursor")

        if (noteClips.isEmpty()){
            Text(text= "ENTER SOME NOTES", modifier = Modifier.padding(16.dp))
        } else {
//            noteClips.map{ print(it.text+" ")}
//            println()

            val nRows = (noteClips.size / nCols) + 1

            var index = 0;
            Column(modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Top,) {
                for(i in 0 until nRows){
                    Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start ) {
                        for(j in 0 until nCols) {
                            if (index != noteClips.size) {
                                val clip = noteClips[index]

                                    Card(modifier = Modifier.padding(4.dp).clickable {dispatch(clip.id)}, shape = RoundedCornerShape(4.dp), elevation = 6.dp, contentColor = if (cursor == index) {
                                        selectionTextColor
                                    } else {
                                        unselectionTextColor
                                    },
                                            backgroundColor = if (cursor == index) selectionBackColor else unselectionBackColor, border = BorderStroke(2.dp, Color.Black)

                                    )
                                    {

                                        Text(text = clip.text, modifier = Modifier.padding(10.dp),
                                                color = if (cursor == index) selectionTextColor else unselectionTextColor,
                                                style = TextStyle(fontSize = TextUnit.Companion.Sp(20))
                                        )

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
                        val name: NoteNamesEn, val ax: Accidents = Accidents.NATURAL) {

}