package com.seanshubin.pairvote.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.*

@Composable
fun CandidateEditor(
    candidatesModel: CandidatesModel,
    onCellChange: (row: Int, column: Int, value: String) -> Unit,
    onRowRemove: (row: Int) -> Unit
) {
    P {
        Text("Candidates")
    }
    Table {
        Thead {
            Tr {
                CandidateModel.columnNames.forEach { columnName ->
                    Th { Text(columnName) }
                }
            }
        }
        Tbody {
            (0 until candidatesModel.getRowCount()).forEach { rowIndex ->
                Tr {
                    candidatesModel.editableColumnIndices().forEach { columnIndex ->
                        EditableCell(
                            value = candidatesModel.getCell(rowIndex, columnIndex),
                            onValueChange = { newValue ->
                                onCellChange(rowIndex, columnIndex, newValue)
                            }
                        )
                    }
                    Td(attrs = { classes("delete-cell") }) {
                        if (candidatesModel.rowIsRemovable(rowIndex)) {
                            Button(attrs = {
                                onClick {
                                    onRowRemove(rowIndex)
                                }
                                classes("delete-button")
                            }) {
                                Text("×")
                            }
                        }
                    }
                }
            }
        }
    }
}
