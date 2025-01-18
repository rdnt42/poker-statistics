package org.marowak.pokerstatisticsback.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Table(name = "players")
@Entity
data class Player(
    @Id
    val id: UUID,

    val name: String,

    val nickname: String,

    val totalIn: Int,

    val totalOut: Int,
)