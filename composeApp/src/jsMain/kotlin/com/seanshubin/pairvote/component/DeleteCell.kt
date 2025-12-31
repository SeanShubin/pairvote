package com.seanshubin.pairvote.component

import androidx.compose.runtime.Composable
import com.seanshubin.pairvote.model.CandidateCellModelRowDeleter
import com.seanshubin.pairvote.model.CandidateCellModelString
import com.seanshubin.pairvote.model.CandidateModel
import com.seanshubin.pairvote.model.CandidatesModel
import org.jetbrains.compose.web.dom.*

@Composable
fun DeleteCell(
    isRemovable: Boolean,
    onRemove: () -> Unit
) {
    Td(attrs = { classes("delete-cell") }) {
        if (isRemovable) {
            Button(attrs = {
                onClick { onRemove() }
                classes("delete-button")
            }) {
                Text("×")
            }
        }
    }
}