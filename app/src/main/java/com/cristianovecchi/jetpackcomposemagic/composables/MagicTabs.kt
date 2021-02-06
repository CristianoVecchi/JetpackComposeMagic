package com.cristianovecchi.jetpackcomposemagic.composables



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import java.util.Locale.filter


//sealed class Tabs {
//    data class Tab1(val text: String = "FILES", val selected: bool)
//    data class Tab2(val text: String = "USERS")
//}

enum class Tabs(val text:String,  val page: @Composable ()->Unit){
    Tab1("FILES", {Page1()} ),
    Tab2("USERS", {Page2()} ),
}

@Composable
fun MagicTabs(){
    val selectedIndex = remember { mutableStateOf(0) }
    val tabTitles = Tabs.values().map{it.text}

    Column( modifier = Modifier.fillMaxSize()) {
        val value = selectedIndex.value
        TabRow(
                value
        ) {
            Tab(selected = value == 0, onClick = {
                selectedIndex.value = 0
            }) {
                Text(text = tabTitles[0])
            }
            Tab(selected = value == 1, onClick = {
                selectedIndex.value = 1
            }) {
                Text(text = tabTitles[1])
            }
        }
        Column( modifier = Modifier.fillMaxSize()) { //tabPageToDisplay
            if ( Tabs.values().filter{ it.ordinal == value}.map{ it.page()}.isEmpty() )
                Tabs.Tab1.page()


            /*var notFound: Boolean = true
            for (tab in Tabs.values()) {
                if (tab.ordinal == value) {
                    tab.page()
                    notFound = false
                }
            }
            if(notFound) Tabs.Tab1.page()*/
            /*when (value) {
                0 -> Tabs.Tab1.page()
                1 -> Tabs.Tab2.page()
                else -> Tabs.Tab1.page()
            }*/
        }
    }
}

@Composable
fun Page1(){
    Text(text = "I'm Page 1")
}

@Composable
fun Page2(){
    Text(text = "I'm Page 2")
}

