package org.marowak.pokerstatisticsback.repository

import org.marowak.pokerstatisticsback.entity.HistoricalGame
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface HistoricalGameRepository: JpaRepository<HistoricalGame, UUID>