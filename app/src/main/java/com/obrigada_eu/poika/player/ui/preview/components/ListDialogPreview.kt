package com.obrigada_eu.poika.player.ui.preview.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import poika.shared.generated.resources.Res
import poika.shared.generated.resources.*
import com.obrigada_eu.poika.player.ui.preview.PreviewData
import com.obrigada_eu.poika.shared.ui.components.ConfirmDeleteDialog
import com.obrigada_eu.poika.shared.ui.components.ListDialog
import com.obrigada_eu.poika.shared.ui.theme.PoikaTheme
import org.jetbrains.compose.resources.stringResource


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
            title = stringResource(Res.string.choose_song),
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