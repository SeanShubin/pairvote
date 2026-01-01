package com.seanshubin.pairvote.app

import androidx.compose.runtime.*
import com.seanshubin.pairvote.component.CandidateEditor
import com.seanshubin.pairvote.component.TopWrapper
import com.seanshubin.pairvote.model.CandidateModel
import com.seanshubin.pairvote.model.CandidatesModel
import com.seanshubin.pairvote.storage.BrowserStorageProvider
import com.seanshubin.pairvote.storage.CandidatesRepository
import com.seanshubin.pairvote.storage.LocalStorageCandidatesRepository

@Composable
fun App(
    repository: CandidatesRepository = LocalStorageCandidatesRepository(
        BrowserStorageProvider
    )
) {
    var candidatesModel by remember {
        mutableStateOf(
            repository.load() ?:CandidatesModel(
                listOf(
                    CandidateModel("a", "Alfa"),
                    CandidateModel("b", "Bravo"),
                    CandidateModel("c", "Charlie"),
                )
            )
        )
    }

    fun updateModel(newModel: CandidatesModel) {
        candidatesModel = newModel
        repository.save(newModel)  // ← Save after each change
    }
    TopWrapper {
        CandidateEditor(
            candidatesModel = candidatesModel,
            onCellChange = { row, column, value ->
                updateModel(candidatesModel.setCell(row, column, value))
            },
            onRowRemove = { row ->
                updateModel(candidatesModel.removeRow(row))
            })
    }
}
