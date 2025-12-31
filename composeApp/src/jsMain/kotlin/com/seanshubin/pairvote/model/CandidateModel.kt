package com.seanshubin.pairvote.model

data class CandidateModel(
    val id: String,
    val name: String
) {
    companion object {
        val empty = CandidateModel("", "")
        val columnNames = listOf("Id", "Name", "")
        val editableColumnIndices = 0 until columnNames.size -1
        private val idSetter: (CandidateModel, String) -> CandidateModel = { candidateModel, value ->
            candidateModel.copy(id = value)
        }
        private val nameSetter: (CandidateModel, String) -> CandidateModel = { candidateModel, value ->
            candidateModel.copy(name = value)
        }
        private val idGetter: (CandidateModel) -> String = { candidateModel -> candidateModel.id }
        private val nameGetter: (CandidateModel) -> String = { candidateModel -> candidateModel.name }
        private val setterByColumn: Map<Int, (CandidateModel, String) -> CandidateModel> = mapOf(
            0 to idSetter,
            1 to nameSetter
        )
        private val getterByColumn: Map<Int, (CandidateModel) -> String> = mapOf(
            0 to idGetter,
            1 to nameGetter
        )

        fun setterByColumn(column: Int): ((CandidateModel, String) -> CandidateModel) =
            setterByColumn[column] ?: throw RuntimeException("Invalid column: $column")

        fun getterByColumn(column: Int): ((CandidateModel) -> String) =
            getterByColumn[column] ?: throw RuntimeException("Invalid column: $column")
    }
}
