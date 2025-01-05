package org.marowak.pokerstatisticsback.repository

import org.marowak.pokerstatisticsback.entity.Player
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PlayersRepository: JpaRepository<Player, UUID> {
}