package com.obrigada_eu.poika.ui

import android.app.AlertDialog
import android.content.Context
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.domain.SongMetaData

class ListDialog(
    private val title: String,
    private val items: List<SongMetaData>,
    private val isMultiChoice: Boolean = false,
    private val onConfirm: (SongMetaData) -> Unit = {},
    private val onConfirmMultiChoice: (List<SongMetaData>) -> Unit = {},
    private val onEmptySelection: () -> Unit = {},
) {

    private val songTitles = items.map { song -> SongMetaDataMapper().mapToSongTitle(song) }

    fun show(context: Context) {

        val builder = AlertDialog.Builder(context)
            .setTitle(title)
            .setNegativeButton(R.string.cancel, null)

        val selectedItems = mutableListOf<SongMetaData>()

        val dialog = if (isMultiChoice) {

             builder.setMultiChoiceItems(songTitles.toTypedArray(), null) { _, i, isChecked ->
                    if (isChecked) selectedItems.add(items[i]) else selectedItems.remove(items[i])
                }
                 .setPositiveButton(R.string.ok, null)
                .create().apply {
                    setOnShowListener {
                        getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                            if (selectedItems.isNotEmpty()) {
                                onConfirmMultiChoice(selectedItems)
                                dismiss()
                            } else {
                                onEmptySelection()
                            }
                        }
                    }
                }

        } else {
            builder.setItems(songTitles.toTypedArray()) { _, which ->
                val selectedSong = items[which]
                onConfirm(selectedSong)
            }.create()

        }

        dialog.show()
    }

    companion object {

        fun showDeleteConfirmationDialog(context: Context, songs: List<SongMetaData>, onConfirm: () -> Unit) {
            val songTitles = songs.map { SongMetaDataMapper().mapToSongTitle(it) }
            AlertDialog.Builder(context)
                .setTitle("Are you sure?")
                .setMessage("Do you really want to delete these songs?\n${songTitles.joinToString(
                    separator = "\n\t",
                    prefix = "\n\t",
                    postfix ="\n",
                    limit = 5,
                    truncated = " ... "
                )}\nThis action cannot be undone.")
                .setPositiveButton("Delete") { _, _ -> onConfirm() }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

}
