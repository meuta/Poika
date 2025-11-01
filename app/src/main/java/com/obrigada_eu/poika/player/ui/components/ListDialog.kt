package com.obrigada_eu.poika.player.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.common.formatters.toTitleString
import com.obrigada_eu.poika.player.domain.model.SongMetaData

@Composable
fun ListDialog(
    title: String,
    items: List<SongMetaData>,
    isMultiChoice: Boolean = false,
    onConfirm: (SongMetaData) -> Unit = {},
    onConfirmMultiChoice: (List<SongMetaData>) -> Unit = {},
    onEmptySelection: () -> Unit = {},
    onDismiss: () -> Unit,
) {
    val songTitles = items.map { song -> song.toTitleString() }

    val checkedStates = remember {
        mutableStateListOf(*Array(items.size) { false })
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(
            title,
            style = MaterialTheme.typography.titleLarge
        ) },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                items.forEachIndexed { index, song ->
                    if (isMultiChoice) {
                        // MultiChoice
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    checkedStates[index] = !checkedStates[index]
                                }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Checkbox(
                                checked = checkedStates[index],
                                onCheckedChange = {
                                    checkedStates[index] = it
                                },
                            )
                            Text(
                                text = songTitles[index],
                                fontSize = 16.sp,
                                modifier = Modifier.padding(start = 8.dp),
                            )
                        }
                    } else {
                        // SingleChoice
                        Text(
                            text = songTitles[index],
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onConfirm(song)
                                    onDismiss()
                                }
                                .padding(vertical = 8.dp),
                        )
                    }
                }
            }
        },
        confirmButton = {
            if (isMultiChoice) {

                DialogButton(
                    onClick = {
                        val selected = items.filterIndexed { i, _ -> checkedStates[i] }
                        if (selected.isNotEmpty()) {
                            onConfirmMultiChoice(selected)
                        } else {
                            onEmptySelection()
                        }
                    },
                    text = stringResource(R.string.ok),
                )

            } else {
                DialogButton(
                    onClick = onDismiss,
                    text = stringResource(R.string.cancel),
                )
            }
        },
        dismissButton = {
            if (isMultiChoice) {
                DialogButton(
                    onClick = onDismiss,
                    text = stringResource(R.string.cancel),
                )
            }
        }
    )
}


@Composable
fun ConfirmDeleteDialog(
    songs: List<SongMetaData>,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    val songTitles = songs.map { song -> song.toTitleString() }
    val message = buildString {
        append(stringResource(R.string.do_you_really_want_to_delete_these_songs))
        append(songTitles.joinToString(
            separator = "\n\t",
            prefix = "\n\t",
            postfix = "\n",
            limit = 5,
            truncated = " ... ",
        ))
        append(stringResource(R.string.this_action_cannot_be_undone))
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.are_you_sure)) },
        text = { Text(
            text = message,
            fontSize = 16.sp,
            modifier = Modifier.verticalScroll(rememberScrollState()),
        ) },
        confirmButton = {
            DialogButton(
                onClick = {
                    onConfirm()
                    onDismiss()
                },
                text = stringResource(R.string.delete),
            )

        },
        dismissButton = {
            DialogButton(
                onClick = onDismiss,
                text = stringResource(R.string.cancel),
            )
        },
    )
}