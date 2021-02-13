package com.cristianovecchi.jetpackcomposemagic.composables


import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp


@Composable
fun NoteTable(parts: List<List<Clip>>){
    ScrollableRow(modifier = Modifier.fillMaxWidth().padding(4.dp), horizontalArrangement = Arrangement.Start ) {
        //val maxSize = (parts.maxWith(Comparator.comparingInt { it.size }))?.size
        val maxSize = parts.maxOf { it.size }
        println(maxSize)
        for( i in 0 until maxSize){
            Column(modifier = Modifier.fillMaxWidth()
                    ) {
                for(j in parts.indices){
                    val clip = if (i < parts[j].size) parts[j][i] else Clip()

                        Box(modifier = Modifier.preferredWidth(80.dp).background(if((i + j) % 2 == 0) Color.Gray else Color.LightGray)

                    ) {

                        Text(text = clip.text,
                                modifier = Modifier.padding(8.dp),

                                style = TextStyle(fontSize = TextUnit.Sp(18),
                                        fontWeight = FontWeight.Normal))


                    }
                }

            }
        }
    }
}




