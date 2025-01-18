package org.marowak.pokerstatisticsback.service

import jakarta.transaction.Transactional
import org.marowak.pokerstatisticsback.entity.Player
import org.marowak.pokerstatisticsback.entity.PlayerInGame
import org.marowak.pokerstatisticsback.repository.PlayersRepository
import org.springframework.stereotype.Service

@Service
class PlayerService(
    private val playersRepository: PlayersRepository
) {

    fun getAll(): List<Player> {
        return playersRepository.findAll()
    }

    @Transactional
    fun updatePlayersCash(playersInGame: List<PlayerInGame>) {
        val playersForUpdate = playersInGame
            .mapNotNull { inGame ->
                inGame.player?.let { player ->
                    player.copy(
                        totalIn = player.totalIn + inGame.cashIn,
                        totalOut = player.totalOut + (inGame.cashOut ?: 0)
                    )
                }

            }
        playersRepository.saveAll(playersForUpdate)
    }
}