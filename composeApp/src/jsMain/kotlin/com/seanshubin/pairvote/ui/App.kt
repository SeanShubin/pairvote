package com.seanshubin.pairvote.ui

import androidx.compose.runtime.*

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
