package com.seanshubin.pairvote.storage

import com.seanshubin.pairvote.model.CandidatesModel

interface CandidatesRepository {
    fun load(): CandidatesModel?
    fun save(model: CandidatesModel)
    fun clear()
}