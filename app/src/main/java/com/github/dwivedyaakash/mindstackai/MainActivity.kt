package com.github.dwivedyaakash.mindstackai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.github.dwivedyaakash.mindstackai.ui.screens.MindStackApp
import com.github.dwivedyaakash.mindstackai.ui.theme.MindStackAITheme
import com.github.dwivedyaakash.mindstackai.viewmodel.MindStackViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MindStackViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindStackAITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MindStackApp(modifier = Modifier.padding(innerPadding), viewModel = viewModel)
                }
            }
        }
    }
}
