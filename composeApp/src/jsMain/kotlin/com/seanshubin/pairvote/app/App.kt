package com.seanshubin.pairvote.app

import androidx.compose.runtime.*
import com.seanshubin.pairvote.component.CandidateEditor
import com.seanshubin.pairvote.component.TopWrapper
import com.seanshubin.pairvote.model.CandidateModel
import com.seanshubin.pairvote.model.CandidatesModel

@Composable
fun App() {
    var candidatesModel by remember {
        mutableStateOf(
            CandidatesModel(
                listOf(
                    CandidateModel("a", "Alfa"),
                    CandidateModel("b", "Bravo"),
                    CandidateModel("c", "Charlie"),
                )
            )
        )
    }
    TopWrapper {
        CandidateEditor(
            candidatesModel = candidatesModel,
            onCellChange = { row, column, value ->
                candidatesModel = candidatesModel.setCell(row, column, value)
            },
            onRowRemove = { row ->
                candidatesModel = candidatesModel.removeRow(row)
            })
    }
}
