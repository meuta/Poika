package com.obrigada_eu.poika.shared.ui.components

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.obrigada_eu.poika.shared.ui.theme.Dimens
import org.jetbrains.compose.resources.stringResource
import poika.shared.generated.resources.Res
import poika.shared.generated.resources.*


@Composable
fun HelpDialog(
    onDismiss: () -> Unit,
) {

    val title = stringResource(Res.string.help)

    val sectionTitles = listOf(
        stringResource(Res.string.title_how_to_use),
        stringResource(Res.string.title_song_versions),
        stringResource(Res.string.title_troubleshooting)
    )

    val messageTemplate = stringResource(
        Res.string.help_dialog_message,
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
            SelectionContainer {
                Text(
                    text = annotated,
                    fontSize = Dimens.MediumFontSize,
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                )
            }
        },
        confirmButton = {
            DialogButton(
                onClick = onDismiss,
                text = stringResource(Res.string.ok),
            )
        }
    )
}