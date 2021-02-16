package com.cristianovecchi.jetpackcomposemagic.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun IntervalSetSelector(intervalSet: List<Int>, dispatch: (List<Int>) -> Unit) {
    val intervals: MutableList<Int> = remember { intervalSet.toMutableStateList() }
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        if(intervals.containsAll(listOf(1,11))){
            SelectedCard(text = "2m\n7M", onClick = { intervals.removeAll(listOf(1,11)); dispatch(intervals) })
        } else {
            UnSelectedCard(text = "2m\n7M", onClick = {intervals.addAll(listOf(1,11)); dispatch(intervals)})
        }
        if(intervals.containsAll(listOf(2,10))){
            SelectedCard(text = "2M\n7m", onClick = {intervals.removeAll(listOf(2,10)); dispatch(intervals)})
        } else {
            UnSelectedCard(text = "2M\n7m", onClick = {intervals.addAll(listOf(2,10)); dispatch(intervals)})
        }
        if(intervals.containsAll(listOf(3,9))){
            SelectedCard(text = "3m\n6M", onClick = {intervals.removeAll(listOf(3,9)); dispatch(intervals)})
        } else {
            UnSelectedCard(text = "3m\n6M", onClick = {intervals.addAll(listOf(3,9)); dispatch(intervals)})
        }
        if(intervals.containsAll(listOf(4,8))){
            SelectedCard(text = "3M\n6m", onClick = {intervals.removeAll(listOf(4,8)); dispatch(intervals)})
        } else {
            UnSelectedCard(text = "3M\n6m", onClick = {intervals.addAll(listOf(4,8)); dispatch(intervals)})
        }
        if(intervals.containsAll(listOf(5,7))){
            SelectedCard(text = "4\n5", onClick = {intervals.removeAll(listOf(5,7)); dispatch(intervals)})
        } else {
            UnSelectedCard(text = "4\n5", onClick = {intervals.addAll(listOf(5,7)); dispatch(intervals)})
        }
        if(intervals.contains(6)){
            SelectedCard(text = "4a\n5d", onClick = {intervals.remove(6); dispatch(intervals)})
        } else {
            UnSelectedCard(text = "4a\n5d", onClick = {intervals.add(6); dispatch(intervals)})
        }
        if(intervals.contains(0)){
            SelectedCard(text = "un\noct", onClick = {intervals.remove(0); dispatch(intervals)})
        } else {
            UnSelectedCard(text = "un\noct", onClick = {intervals.add(0); dispatch(intervals)})
        }
    }
}

@Composable
fun SelectedCard(text: String, onClick: () -> Unit){
    val selectionBackColor = Color.White
    val selectionTextColor = Color.Red
    val selectionBorderColor = Color.Black
    Card(modifier = Modifier
            .background(Color.White)
            .clip(RoundedCornerShape(6.dp))
            .padding(6.dp)
            .clickable { onClick() },
                            backgroundColor = selectionBackColor,
                            contentColor = selectionTextColor,
                            border = BorderStroke(2.dp, selectionBorderColor )) {

        Text(text = text, modifier = Modifier.padding(18.dp),
                style = TextStyle(fontSize = TextUnit.Sp(18),
                        fontWeight = FontWeight.Bold))
    }
}

@Composable
fun UnSelectedCard(text: String, onClick: () -> Unit){
    val unselectionBackColor = Color.LightGray
    val unselectionTextColor = Color.Blue
    val unselectionBorderColor = Color.DarkGray
    Card(modifier = Modifier
            .background(Color.White)
            .clip(RoundedCornerShape(6.dp))
            .padding(6.dp)
            .clickable { onClick() },
            backgroundColor = unselectionBackColor,
            contentColor = unselectionTextColor,
            border = BorderStroke(2.dp, unselectionBorderColor )) {

        Text(text = text, modifier = Modifier.padding(18.dp),
                style = TextStyle(fontSize = TextUnit.Sp(16),
                        fontWeight = FontWeight.Bold))
    }
}