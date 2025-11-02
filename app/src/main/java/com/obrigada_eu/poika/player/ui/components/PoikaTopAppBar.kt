package com.obrigada_eu.poika.player.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.window.PopupProperties
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.ui.theme.Dimens
import kotlin.String
import kotlin.collections.Map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoikaTopAppBar(
    menuItems: Map<String, () -> Unit>,
    menuIconOnclick: () -> Unit,
    onDismissRequest: () -> Unit,
    expanded: Boolean,
) {

    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        actions = {
            IconButton(onClick = menuIconOnclick) {
                Icon(Icons.Default.MoreVert, contentDescription = null)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = onDismissRequest,
                offset = DpOffset(Dimens.MenuOffsetX, Dimens.MenuOffsetY),
                modifier = Modifier.widthIn(Dimens.MenuWidthMin, Dimens.MenuWidthMax).background(MaterialTheme.colorScheme.surface),
                properties = PopupProperties(focusable = true)
            ) {

                val items: Map<String, () -> Unit> = menuItems
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface) {
                    items.forEach { (labelRes, action) ->
                        CustomDropdownMenuItem(
                            text = labelRes,
                            onClick = action,
                        )
                    }}
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
    )
}