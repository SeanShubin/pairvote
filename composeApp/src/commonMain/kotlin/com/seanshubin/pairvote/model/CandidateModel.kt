package com.seanshubin.pairvote.model

data class CandidateModel(
    val id: String,
    val name: String
) {
    companion object {
        val empty = CandidateModel("", "")

        private data class ColumnInfo(
            val name: String,
            val getter: (CandidateModel) -> String,
            val setter: (CandidateModel, String) -> CandidateModel
        )

        private val columns = listOf(
            ColumnInfo("Id", { it.id }, { model, value -> model.copy(id = value) }),
            ColumnInfo("Name", { it.name }, { model, value -> model.copy(name = value) })
        )

        val columnNames = columns.map { it.name } + ""

        fun setterByColumn(column: Int): ((CandidateModel, String) -> CandidateModel) =
            columns.getOrNull(column)?.setter
                ?: throw RuntimeException("Invalid column: $column")

        fun getterByColumn(column: Int): ((CandidateModel) -> String) =
            columns.getOrNull(column)?.getter
                ?: throw RuntimeException("Invalid column: $column")
    }
}
