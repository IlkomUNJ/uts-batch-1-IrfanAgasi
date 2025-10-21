package com.example.myapplication

import androidx.compose.runtime.mutableStateListOf

data class Contact(
    var name: String,
    var address: String,
    var phone: String,
    var email: String
)

object ContactRepository {
    val contactList = mutableStateListOf<Contact>()
    fun addContact(contact: Contact): Boolean {
        val addressWordCount = contact.address.trim().split("\\s+".toRegex()).size
        if (addressWordCount < 5) return false

        contactList.add(contact)
        return true
    }
}
