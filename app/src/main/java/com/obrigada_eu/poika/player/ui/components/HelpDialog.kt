package com.obrigada_eu.poika.player.ui.components

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.ui.theme.Dimens

@Composable
fun HelpDialog(
    onDismiss: () -> Unit,
) {

    val title = stringResource(R.string.help)

    val sectionTitles = listOf(
        stringResource(R.string.title_how_to_use),
        stringResource(R.string.title_song_versions),
        stringResource(R.string.title_troubleshooting)
    )

    val messageTemplate = stringResource(
        R.string.help_dialog_message,
        sectionTitles[0],
        sectionTitles[1],
        sectionTitles[2],
    )

    val highlightColor = MaterialTheme.colorScheme.primary

    val annotated = buildAnnotatedString {
        var currentIndex = 0

        sectionTitles.forEach { sectionTitle ->
            val start = messageTemplate.indexOf(sectionTitle, currentIndex)
            if (start >= 0) {
                // Append text before section title
                if (start > currentIndex) {
                    append(messageTemplate.substring(currentIndex, start))
                }
                // Append styled section title
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = Dimens.MediumFontSize,
                        color = highlightColor
                    )
                ) {
                    append(sectionTitle)
                }
                currentIndex = start + sectionTitle.length
            }
        }

        // Append remaining text
        if (currentIndex < messageTemplate.length) {
            append(messageTemplate.substring(currentIndex))
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        ) },
        text = {
            Text(
                text = annotated,
                fontSize = Dimens.MediumFontSize,
                modifier = Modifier.verticalScroll(rememberScrollState()),
            )
        },
        confirmButton = {
            DialogButton(
                onClick = onDismiss,
                text = stringResource(R.string.ok),
            )
        }
    )
}

@Preview
@Composable
fun HelpDialogPreview() {
    HelpDialog(onDismiss = {})
}