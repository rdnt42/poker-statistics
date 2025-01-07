package org.marowak.pokerstatisticsback.repository

import org.marowak.pokerstatisticsback.entity.PlayerInGame
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface PlayerInGameRepository: JpaRepository<PlayerInGame, UUID> {
    fun findByPlayerId(playerId: UUID): Optional<PlayerInGame>
}