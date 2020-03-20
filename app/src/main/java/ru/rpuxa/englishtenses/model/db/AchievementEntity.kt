package ru.rpuxa.englishtenses.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievements")
class AchievementEntity(
    @PrimaryKey
    val id: Int,
    var progress: Int
)