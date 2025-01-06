package org.marowak.pokerstatisticsback.service

import org.marowak.pokerstatisticsback.dto.response.*
import org.marowak.pokerstatisticsback.entity.ActiveGame
import org.marowak.pokerstatisticsback.repository.ActiveGameRepository
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*

@Service
class ActiveGameService(
    private val activeGameRepository: ActiveGameRepository
) {
    fun getAll(): List<ActiveGameDto> {
        return activeGameRepository.findAll()
            .map { it.toDto() }
    }

    fun crateNewGame(): ActiveGameDto {
        val game = ActiveGame(UUID.randomUUID(), OffsetDateTime.now(), emptyList())
        return activeGameRepository
            .save(game)
            .toDto()
    }

    fun getById(id: UUID): ActiveGameDto {
        return activeGameRepository
            .findById(id)
            .orElseThrow()
            .toDto()
    }

    fun getFullInfoById(id: UUID): ActiveGameFullDto {
        return activeGameRepository
            .findById(id)
            .orElseThrow()
            .toFullDto()
    }
}