package com.seanshubin.pairvote.component

import androidx.compose.runtime.Composable
import com.seanshubin.pairvote.model.CandidateCellModelRowDeleter
import com.seanshubin.pairvote.model.CandidateCellModelString
import com.seanshubin.pairvote.model.CandidateModel
import com.seanshubin.pairvote.model.CandidatesModel
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
                candidatesModel.getColumnNames().forEach { columnName ->
                    Th { Text(columnName) }
                }
            }
        }
        Tbody {
            (0 until candidatesModel.getRowCount()).forEach { rowIndex ->
                Tr {
                    (0 until candidatesModel.getColumnCount()).forEach { columnIndex ->
                        val candidateCellModel = candidatesModel.getCell(rowIndex, columnIndex)
                        when (candidateCellModel) {
                            is CandidateCellModelRowDeleter -> {
                                DeleteCell(
                                    isRemovable = candidatesModel.rowIsRemovable(rowIndex),
                                    onRemove = { onRowRemove(rowIndex) }
                                )
                            }
                            is CandidateCellModelString -> {
                                EditableCell(
                                    value = candidateCellModel.value,
                                    errorMessage = candidateCellModel.errorMessage,
                                    onValueChange = { newValue ->
                                        onCellChange(rowIndex, columnIndex, newValue)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
