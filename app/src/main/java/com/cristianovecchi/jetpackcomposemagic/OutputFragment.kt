package com.cristianovecchi.jetpackcomposemagic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.cristianovecchi.jetpackcomposemagic.AIMUSIC.MikroKanon
import com.cristianovecchi.jetpackcomposemagic.composables.Clip
import com.cristianovecchi.jetpackcomposemagic.composables.NoteNamesEn
import com.cristianovecchi.jetpackcomposemagic.composables.NoteNamesIt
import com.cristianovecchi.jetpackcomposemagic.composables.NoteTable
import com.cristianovecchi.jetpackcomposemagic.ui.JetpackComposeMagicTheme
import java.util.*

class OutputFragment: Fragment() {

    private var list: List<Clip> = emptyList()

    fun toClips(mikroKanon: MikroKanon, noteNames: List<String>) : List<List<Clip>>{
        return mikroKanon.parts.map { part ->
            part.absPitches.map{ absPitch ->
                Clip(Clip.convertAbsToString(absPitch,noteNames),-1,absPitch) }.toList()
            }.toList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelableArrayList<Clip>("list")?.let {
            this.list = it
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                JetpackComposeMagicTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {

                        ScrollableColumn(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            val intervalSet = listOf(2,10,3,9,4,8,5,7) //Pentatonal
                            val absPitches = list.map{it.abstractNote}.toList()
                            //Text(text = Arrays.toString(absPitches.toIntArray()))
                            val mikroKanons = MikroKanon.findAll2AbsPartMikroKanons(absPitches,intervalSet, 5)
                            mikroKanons.filter {it.emptiness < 10f }.forEach{
                                val parts = toClips(it, NoteNamesIt.values().map{value -> value.toString()})
                                NoteTable(parts)
                            }
                        }
                    }
                }
            }
        }
    }
}