package ru.rpuxa.englishtenses.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "learned_sentences", primaryKeys = ["id", "tenseCode"])
class LearnedSentenceEntity(
    val id: Int,
    val tenseCode: Int
)