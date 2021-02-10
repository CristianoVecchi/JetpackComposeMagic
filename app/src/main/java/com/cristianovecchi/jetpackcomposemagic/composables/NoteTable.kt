package com.cristianovecchi.jetpackcomposemagic.composables


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


val parts: List<List<Int>> =
    listOf(
            listOf(0, 1, 2, 3, 4),
            listOf(-1, 9, 2, 3, 11, 9),
            listOf(0, 0, -1, 7, 4))



@Composable
fun NoteTable(parts: List<List<Clip>>){
    ScrollableRow(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Start) {
        //val maxSize = (parts.maxWith(Comparator.comparingInt { it.size }))?.size
        val maxSize = parts.maxOf { it.size }
        println(maxSize)
        for( i in 0 until maxSize){
            Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                for(j in parts.indices){
                    val clip = if (i < parts[j].size) parts[j][i] else Clip()
                        //val note = -1
                    Text( text = clip.text)

                }

            }
        }
    }
}

