package com.cristianovecchi.jetpackcomposemagic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.cristianovecchi.jetpackcomposemagic.composables.AbstractNoteSequenceEditor
import com.cristianovecchi.jetpackcomposemagic.ui.JetpackComposeMagicTheme

class InputFragment: Fragment() {
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
                        AbstractNoteSequenceEditor(res_done = R.drawable.ic_baseline_done_24,
                        done_action = { bundle: Bundle -> findNavController().navigate(R.id.outputFragment, bundle)})
                    }
                }
            }
        }
    }
}