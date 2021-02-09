package com.cristianovecchi.jetpackcomposemagic.composables

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import java.util.*


private val contrapunctus = mutableListOf(3, 6, 10, 11, 2)

@Composable
fun ComputationalPerformer(pitches: MutableList<Int> = contrapunctus,
                           transpose: Int = 0,
                           repeat: Int = 0,
                           shuffle: Boolean = false,

                           ){


    Transposer(pitches = pitches, transpose = transpose){
        Repetitor(pitches = pitches, repeat = repeat) {

        }
        Repetitor(pitches = pitches, repeat = repeat){
            Shuffler(pitches = pitches, shuffle = shuffle) {
                ComputationalDisplay(pitches = pitches)
            }
        }
    }

}

@Composable
fun Transposer(pitches: MutableList<Int>, transpose: Int = 0, content: @Composable () -> Unit){
    for(i in 0 until pitches.size) {
        pitches[i] = pitches[i] + transpose
    }
    content()
}

@Composable
fun Repetitor(pitches: MutableList<Int>, repeat: Int = 0, content: @Composable () -> Unit){
    if(repeat > 0){
        val clone: MutableList<Int> = pitches.toMutableList()
        for (i in 0 until repeat){
            pitches.addAll(clone)
        }
    }
    content()
}

@Composable
fun Shuffler(pitches: MutableList<Int>, shuffle: Boolean = false,content: @Composable () -> Unit ){
    if (shuffle) pitches.shuffle()
    content()
}

@Composable
fun ComputationalDisplay(pitches: MutableList<Int>){
    Text( text= "COMPUTATION: ")
    Text( text= pitches.map{it.toString()}.toTypedArray().contentToString())
}