package com.github.dwivedyaakash.mindstackai.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.github.dwivedyaakash.mindstackai.viewmodel.MindStackViewModel

@Composable
fun MindStackApp(modifier: Modifier, viewModel: MindStackViewModel) {
    val notes by viewModel.notes.collectAsState()
    val chatHistory by viewModel.chatHistory.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var currentTab by remember { mutableStateOf("Vault") }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Vault") },
                    label = { Text("Vault") },
                    selected = currentTab == "Vault",
                    onClick = { currentTab = "Vault" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Chat") },
                    label = { Text("Chat") },
                    selected = currentTab == "Chat",
                    onClick = { currentTab = "Chat" }
                )
            }
        }
    ) { innerPadding ->
        if (currentTab == "Vault") {
            VaultScreen(
                notes = notes,
                onSaveNote = { viewModel.saveNote(it) },
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            ChatScreen(
                chatHistory = chatHistory,
                isLoading = isLoading,
                onSendMessage = { viewModel.askQuestion(it) },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
