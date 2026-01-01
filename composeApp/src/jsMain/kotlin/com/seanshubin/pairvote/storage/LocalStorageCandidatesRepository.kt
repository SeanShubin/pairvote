package com.seanshubin.pairvote.storage

import com.seanshubin.pairvote.model.CandidateModel
import com.seanshubin.pairvote.model.CandidatesModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString

class LocalStorageCandidatesRepository(
    private val storageProvider: StorageProvider,
    private val storageKey: String = "pairvote.candidates"
) : CandidatesRepository {

    @Serializable
    private data class CandidateDto(val id: String, val name: String)

    override fun load(): CandidatesModel? {
        val json = storageProvider.getItem(storageKey) ?: return null
        return try {
            val dtos = Json.decodeFromString<List<CandidateDto>>(json)
            CandidatesModel(dtos.map { CandidateModel(it.id, it.name) })
        } catch (e: Exception) {
            null  // Invalid data - return null to start fresh
        }
    }

    override fun save(model: CandidatesModel) {
        val dtos = model.candidateList.map {
            CandidateDto(it.id, it.name)
        }
        val json = Json.encodeToString(dtos)
        storageProvider.setItem(storageKey, json)
    }

    override fun clear() {
        storageProvider.removeItem(storageKey)
    }
}
