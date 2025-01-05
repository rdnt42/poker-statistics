package org.marowak.pokerstatisticsback.service

import org.marowak.pokerstatisticsback.entity.Player
import org.marowak.pokerstatisticsback.repository.PlayersRepository
import org.springframework.stereotype.Service

@Service
class PlayerService(
    private val playersRepository: PlayersRepository
) {

    fun getAll(): List<Player> {
        return playersRepository.findAll()
    }
}