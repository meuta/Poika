package com.obrigada_eu.poika.ui.components


import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.obrigada_eu.poika.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PoikaTopAppBar() {
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        actions = {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(Icons.Default.MoreVert, contentDescription = null)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(0.dp, (-48).dp),
                modifier = Modifier.widthIn(min = 168.dp, max = 280.dp)
            ) {
                CustomDropdownMenuItem(
                    text = stringResource(R.string.choose_song),
                    onClick = { /* Handle Action 1 */ }
                )
                CustomDropdownMenuItem(
                    text = stringResource(R.string.delete_song),
                    onClick = { /* Handle Action 2 */ }
                )
                CustomDropdownMenuItem(
                    text = stringResource(R.string.help),
                    onClick = { /* Handle Action 3 */ }
                )

            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
    )
}