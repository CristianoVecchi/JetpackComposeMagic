package com.cristianovecchi.jetpackcomposemagic.composables



import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


//sealed class Tabs {
//    data class Tab1(val text: String = "FILES", val selected: bool)
//    data class Tab2(val text: String = "USERS")
//}
enum class Tabs(val text:String){
    Tab1("FILES"),
    Tab2("USERS"),
}

val tabTitles = Tabs.values().map{it.text}

@Composable
fun MagicTabs(items: List<String> = tabTitles) {
    val selectedIndex = remember { mutableStateOf(0)}
    TabRow(
           selectedIndex.value
    ) {

        Tab(selected = selectedIndex.value == 0, onClick = {
            selectedIndex.value = 0

        }) {
            Text(text = Tabs.Tab1.text)
        }
        Tab(selected = selectedIndex.value == 1, onClick = {
            selectedIndex.value = 1

        }) {
            Text(text = Tabs.Tab2.text)
        }
        Tab(selected = selectedIndex.value == 2, onClick = {
            selectedIndex.value = 2

        })  {
            Text(text = selectedIndex.value.toString())

    }

    }
}