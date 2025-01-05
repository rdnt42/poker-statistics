package org.marowak.pokerstatisticsback.repository

import org.marowak.pokerstatisticsback.entity.ActiveGame
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ActiveGameRepository: JpaRepository<ActiveGame, UUID>