package com.seanshubin.pairvote.model

data class CandidatesModel(
    val candidateList: List<CandidateModel>
) {
    fun setCell(row: Int, column: Int, value: String): CandidatesModel {
        val setter = CandidateModel.setterByColumn(column)
        val newCandidateList = if (isNewRow(row)) {
            val currentCandidate = CandidateModel.empty
            val newCandidate = setter(currentCandidate, value)
            candidateList + newCandidate
        } else {
            val currentCandidate = candidateList[row]
            val updatedCandidate = setter(currentCandidate, value)
            candidateList.mapIndexed { index, candidateModel ->
                if (index == row) updatedCandidate else candidateModel
            }
        }
        return copy(candidateList = newCandidateList)
    }

    fun getCell(row: Int, column: Int): CandidateCellModel {
        if(isDeleteColumn(column)) return CandidateCellModelRowDeleter
        if (isNewRow(row)) return CandidateCellModelString("", errorMessage = null)
        val getter = CandidateModel.getterByColumn(column)
        val errorMessage = duplicateErrorMessage(row, column)
        return CandidateCellModelString(getter(candidateList[row]), errorMessage)
    }

    fun removeRow(row: Int): CandidatesModel {
        val newCandidateList = candidateList.filterIndexed { index, _ -> index != row }
        return copy(candidateList = newCandidateList)
    }

    fun rowIsRemovable(row: Int): Boolean = row < candidateList.size

    fun getRowCount(): Int {
        return candidateList.size + 1
    }

    fun getColumnCount(): Int {
        return CandidateModel.columnNames.size
    }

    private fun duplicateErrorMessage(row:Int, column:Int):String? {
        val getter = CandidateModel.getterByColumn(column)
        val currentValue = getter(candidateList[row])
        val duplicateRows = candidateList.mapIndexedNotNull { index, candidateModel ->
            if(index == row) null
            else if(getter(candidateModel) == currentValue) index
            else null
        }
        if(duplicateRows.isEmpty()) return null
        return "Duplicate value found in row(s) ${duplicateRows.joinToString(", ")}"
    }

    private fun isNewRow(row: Int): Boolean = row >= candidateList.size
    private fun isDeleteColumn(column: Int): Boolean = column >= CandidateModel.columnNames.size -1
}
