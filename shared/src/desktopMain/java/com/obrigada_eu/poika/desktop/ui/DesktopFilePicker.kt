package com.obrigada_eu.poika.desktop.ui

import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

class DesktopFilePicker {
    fun pickZipFile(): File? {
        val chooser = JFileChooser()
        chooser.fileFilter =
            FileNameExtensionFilter(
                "ZIP archives",
                "zip"
            )

        return if (
            chooser.showOpenDialog(null) ==
            JFileChooser.APPROVE_OPTION
        ) chooser.selectedFile
        else null
    }
}