package com.example.myapplication

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditContactUI(navController: NavHostController, contactIndex: Int) {
    val context = LocalContext.current
    val contact = ContactRepository.contactList.getOrNull(contactIndex)

    if (contact == null) {
        Text("Contact not found")
        return
    }

    var name by remember { mutableStateOf(TextFieldValue(contact.name)) }
    var address by remember { mutableStateOf(TextFieldValue(contact.address)) }
    var phone by remember { mutableStateOf(TextFieldValue(contact.phone)) }
    var email by remember { mutableStateOf(TextFieldValue(contact.email)) }

    Scaffold(
        topBar = { TopAppBar(
            navigationIcon = {
                IconButton(onClick = { navController.navigate("list") }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },title = { Text("Edit Contact") }) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address (min. 5 words)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp, max = 200.dp),
                    maxLines = 5,
                    singleLine = false
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val addressWordCount = address.text.trim().split("\\s+".toRegex()).size
                        if (addressWordCount < 5) {
                            Toast.makeText(context, "Address must be at least 5 words!", Toast.LENGTH_SHORT).show()
                        } else {
                            ContactRepository.contactList[contactIndex] = Contact(
                                name.text, address.text, phone.text, email.text
                            )
                            Toast.makeText(context, "Contact updated!", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Save Changes")
                }
            }
        }
    }
}
