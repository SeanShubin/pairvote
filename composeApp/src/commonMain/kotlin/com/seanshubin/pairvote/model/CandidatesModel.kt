package com.seanshubin.pairvote.model

import com.seanshubin.pairvote.text.TextNormalizer

data class CandidatesModel(
    val candidateList: List<CandidateModel>
) {
    fun setCell(row: Int, column: Int, value: String): CandidatesModel {
        val normalizedValue = TextNormalizer.normalize(value)
        val setter = CandidateModel.setterByColumn(column)
        val newCandidateList = if (isNewRow(row)) {
            val currentCandidate = CandidateModel.empty
            val newCandidate = setter(currentCandidate, normalizedValue)
            candidateList + newCandidate
        } else {
            val currentCandidate = candidateList[row]
            val updatedCandidate = setter(currentCandidate, normalizedValue)
            candidateList.mapIndexed { index, candidateModel ->
                if (index == row) updatedCandidate else candidateModel
            }
        }
        return copy(candidateList = newCandidateList)
    }

    fun getCell(row: Int, column: Int): CandidateCellModel {
        if (isDeleteColumn(column)) return CandidateCellModelRowDeleter
        if (isNewRow(row)) return CandidateCellModelString("", errorMessage = null)
        val getter = CandidateModel.getterByColumn(column)
        val errorMessage = validationErrorMessage(row, column)
        return CandidateCellModelString(getter(candidateList[row]), errorMessage)
    }

    fun removeRow(row: Int): CandidatesModel {
        val newCandidateList = candidateList.filterIndexed { index, _ -> index != row }
        return copy(candidateList = newCandidateList)
    }

    fun rowIsRemovable(row: Int): Boolean = row < candidateList.size

    fun getRowCount(): Int = candidateList.size + 1

    fun getColumnCount(): Int = CandidateModel.columnNames.size

    fun getColumnNames(): List<String> = CandidateModel.columnNames

    private fun validationErrorMessage(row: Int, column: Int): String? {
        return blankErrorMessage(row, column) ?: duplicateErrorMessage(row, column)
    }

    private fun duplicateErrorMessage(row: Int, column: Int): String? {
        val getter = CandidateModel.getterByColumn(column)
        val currentValue = getter(candidateList[row])
        val duplicateRows = candidateList.mapIndexedNotNull { index, candidateModel ->
            if (index == row) null
            else if (getter(candidateModel) == currentValue) index
            else null
        }
        if (duplicateRows.isEmpty()) return null
        if(duplicateRows.size == 1) return "Row $row duplicates value found in row ${duplicateRows.first()}"
        return "Row $row duplicates values found in rows: ${duplicateRows.joinToString(", ")}"
    }

    private fun blankErrorMessage(row: Int, column: Int): String? {
        val getter = CandidateModel.getterByColumn(column)
        val currentValue = getter(candidateList[row])
        return if(currentValue.isBlank()) "Blank value not allowed in row $row column $column"
        else null
    }

    private fun isNewRow(row: Int): Boolean = row >= candidateList.size
    private fun isDeleteColumn(column: Int): Boolean = column >= CandidateModel.columnNames.size - 1
}
