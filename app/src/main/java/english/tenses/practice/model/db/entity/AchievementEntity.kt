package english.tenses.practice.model.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievements")
class AchievementEntity(
    @PrimaryKey
    val id: Int,
    var progress: Int
)