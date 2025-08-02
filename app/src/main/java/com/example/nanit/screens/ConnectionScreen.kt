package com.example.nanit.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nanit.ui.theme.BentonSans

/**
 * This is the server connection screen.
 */
@Composable
fun ConnectScreen(
    onConnect: (String, String) -> Unit
) {

    // region Members

    var ip by remember { mutableStateOf("") }
    var port by remember { mutableStateOf("") }

    // endregion

    // region UI

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Connect to Nanit Server",
            fontSize = 25.sp,
            fontFamily = BentonSans
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = ip,
            onValueChange = { ip = it },
            label = { Text("IP Address") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = port,
            onValueChange = { port = it },
            label = { Text("Port") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onConnect(ip, port) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Connect",
                fontSize = 15.sp,
                fontFamily = BentonSans
            )
        }
    }

    // endregion

}
