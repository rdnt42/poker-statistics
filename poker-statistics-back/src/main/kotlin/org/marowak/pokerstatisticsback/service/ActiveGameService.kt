package org.marowak.pokerstatisticsback.service

import org.marowak.pokerstatisticsback.dto.response.ActiveGameDto
import org.marowak.pokerstatisticsback.dto.response.ActiveGameFullDto
import org.marowak.pokerstatisticsback.entity.ActiveGame
import org.marowak.pokerstatisticsback.repository.ActiveGameRepository
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*

@Service
class ActiveGameService(
    private val activeGameRepository: ActiveGameRepository
) {
    fun getAll(): List<ActiveGame> {
        return activeGameRepository.findAll()
    }

    fun crateNewGame(): ActiveGame {
        val game = ActiveGame(UUID.randomUUID(), OffsetDateTime.now(), emptyList())
        activeGameRepository.save(game)

        return game
    }

    fun getById(id: UUID): ActiveGameDto {
        val activeGame =  activeGameRepository.findById(id).orElseThrow()
        return ActiveGameDto(activeGame.id, activeGame.startDate)
    }

    fun getFullInfoById(id: UUID): ActiveGameFullDto {
        val activeGame = activeGameRepository.findById(id).orElseThrow()

        return ActiveGameFullDto(activeGame.id, activeGame.startDate, activeGame.activePlayers)
    }
}