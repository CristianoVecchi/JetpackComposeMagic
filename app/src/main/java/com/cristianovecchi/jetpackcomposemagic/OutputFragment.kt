package com.cristianovecchi.jetpackcomposemagic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.cristianovecchi.jetpackcomposemagic.composables.Clip
import com.cristianovecchi.jetpackcomposemagic.composables.NoteTable
import com.cristianovecchi.jetpackcomposemagic.ui.JetpackComposeMagicTheme

class OutputFragment: Fragment() {

    private var list: List<Clip> = emptyList()

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

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            val parts = listOf(
                                list, list, list
                            )
                            NoteTable(parts)

                        }
                    }
                }
            }
        }
    }
}