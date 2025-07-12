package com.obrigada_eu.poika.player.ui.components

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.common.formatters.toTitleString
import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.player.ui.preview.PreviewData
import com.obrigada_eu.poika.ui.theme.Dimens
import com.obrigada_eu.poika.ui.theme.PoikaTheme

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
                                .padding(vertical = Dimens.DialogMultipleChoicePadding),
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
                                fontSize = Dimens.MediumFontSize,
                                modifier = Modifier.padding(
                                    start = Dimens.DialogMultipleChoiceTextPadding
                                ),
                            )
                        }
                    } else {
                        // SingleChoice
                        Text(
                            text = songTitles[index],
                            fontSize = Dimens.MediumFontSize,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onConfirm(song)
                                    onDismiss()
                                }
                                .padding(vertical = Dimens.DialogSingleChoicePadding),
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
            fontSize = Dimens.MediumFontSize,
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

class ListDialogIsMultiChoiceProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(true, false)
}


@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
fun ListDialogPreview(
    @PreviewParameter(ListDialogIsMultiChoiceProvider::class) isMultiChoice: Boolean,
) {
    PoikaTheme {
        ListDialog(
            title = "Choose Song",
            items = PreviewData.songs,
            isMultiChoice = isMultiChoice,
            onConfirm = {},
            onConfirmMultiChoice = {},
            onEmptySelection = {},
            onDismiss = {}
        )
    }
}

@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
fun ConfirmDeleteDialogPreview() {
    PoikaTheme {
        ConfirmDeleteDialog(
            songs = PreviewData.songs,
            onConfirm = {},
            onDismiss = {}
        )
    }
}