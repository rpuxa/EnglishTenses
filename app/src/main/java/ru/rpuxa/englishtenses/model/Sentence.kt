package ru.rpuxa.englishtenses.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Sentence(
    val text: String,
    val answers: List<WordAnswer>
)

@Entity(tableName = "sentences")
class SentenceEntity(
    @PrimaryKey
    val id: Int,
    val text: String
)

