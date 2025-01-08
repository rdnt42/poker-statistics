package org.marowak.pokerstatisticsback.repository

import org.marowak.pokerstatisticsback.entity.PlayerInGame
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PlayerInGameRepository: JpaRepository<PlayerInGame, UUID>