package com.cristianovecchi.jetpackcomposemagic.composables

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.runtime.remember
import com.cristianovecchi.jetpackcomposemagic.AIMUSIC.MikroKanon

@Composable
fun ResultDisplay(intervalSet:List<Int> = listOf(2,10,3,9,4,8,5,7), absPitches: List<Int>) {
    val intervals: MutableList<Int> = remember { intervalSet.toMutableStateList() }

    Column(modifier = Modifier.fillMaxHeight()) {
        val modifier4 = Modifier
                .fillMaxWidth()
                .weight(4f)
        val modifier1 = Modifier
                .fillMaxSize()
                .weight(1f)
                    val mikroKanons = MikroKanon.findAll2AbsPartMikroKanons(absPitches, intervals, 5)
                    val limit = 7f
                    val topMikroKanons = mikroKanons.filter{it.emptiness <limit}
                    Text(text = "N. of Results selected: ${topMikroKanons.size}")
                    ScrollableColumn(
                        modifier4
                    ) {
                        topMikroKanons.forEach {
                            val parts = toClips(it, NoteNamesIt.values().map { value -> value.toString() })
                            NoteTable(parts)
                        }
                    }
                    IntervalSetSelector(intervalSet = intervals, dispatch = {newIntervals -> intervals.clear(); intervals.addAll(newIntervals)})
    }

}
fun toClips(mikroKanon: MikroKanon, noteNames: List<String>) : List<List<Clip>>{
    return mikroKanon.parts.map { part ->
        part.absPitches.map{ absPitch ->
            Clip(Clip.convertAbsToString(absPitch,noteNames),-1,absPitch) }.toList()
    }.toList()
}