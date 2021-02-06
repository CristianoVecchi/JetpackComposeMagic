package com.cristianovecchi.jetpackcomposemagic.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


enum class NoteNamesEn {
    C,D,E,F,G,A,B
}enum class NoteNamesIt {
    Do,Re,Mi,Fa,Sol,La,Si
}
enum class Accidents(val ax : Char){
    SHARP('#'), FLAT('b') , D_SHARP('x'), D_FLAT('§')
}
sealed class Out {
    data class Note(val note: NoteNamesEn) : Out()
    data class Accident(val ax: Accidents) : Out()
    object Delete: Out()
    object Forward: Out()
    object Back: Out()
    object Enter: Out()
    object Undo: Out()
}

private data class ButtonInfo(val text: String, val output: Out)

@Composable
fun NoteKeyboard(
    names : List<String> = NoteNamesIt.values().map{it.toString()},
    nRows: Int = 4, nCols: Int = 4,
    dispatch : (Out) -> Unit ) {
    val buttonInfos = listOf(
        ButtonInfo(text = Accidents.D_SHARP.ax.toString(), output = Out.Accident(Accidents.D_SHARP)),
        ButtonInfo(text = Accidents.SHARP.ax.toString(), output = Out.Accident(Accidents.SHARP)),
        ButtonInfo(text = "UN", output = Out.Undo),
        ButtonInfo(text = names[3], output = Out.Note(NoteNamesEn.F)),

        ButtonInfo(text = Accidents.D_FLAT.ax.toString(), output = Out.Accident(Accidents.D_FLAT)),
        ButtonInfo(text = Accidents.FLAT.ax.toString(), output = Out.Accident(Accidents.FLAT)),
        ButtonInfo(text = names[6], output = Out.Note(NoteNamesEn.B)),
        ButtonInfo(text = names[2], output = Out.Note(NoteNamesEn.E)),

        ButtonInfo(text = "DEL", output = Out.Delete),
        ButtonInfo(text = "->", output = Out.Forward),
        ButtonInfo(text = names[5], output = Out.Note(NoteNamesEn.A)),
        ButtonInfo(text = names[1], output = Out.Note(NoteNamesEn.D)),

        ButtonInfo(text = "OK", output = Out.Enter),
        ButtonInfo(text = "<-", output = Out.Back),
        ButtonInfo(text = names[4], output = Out.Note(NoteNamesEn.G)),
        ButtonInfo(text = names[0], output = Out.Note(NoteNamesEn.C)),
    )
    var buttonIndex = 0;
    Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,){

        for (i in 0 until nRows){
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,) {
                for (j in 0 until nCols) {
                    if( buttonIndex == buttonInfos.size ){
                        Text(text = "  ")
                    } else {
                        val buttonInfo = buttonInfos[buttonIndex++]
                        Button(onClick = {dispatch(buttonInfo.output)} ) {
                            Text(text = buttonInfo.text)
                        }
                    }


                }
            }
        }

    }
}