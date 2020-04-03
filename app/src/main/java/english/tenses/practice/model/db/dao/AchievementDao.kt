package english.tenses.practice.model.db.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.room.*
import english.tenses.practice.model.db.entity.AchievementEntity
import english.tenses.practice.model.enums.Achievement

@Dao
interface AchievementDao {

    @Query("SELECT * FROM achievements WHERE id = :id")
    suspend fun getOrNull(id: Int): AchievementEntity?

    suspend fun get(id: Int) = getOrNull(id) ?: AchievementEntity(
        id,
        0
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(achievementEntity: AchievementEntity)

    @Query("SELECT * FROM achievements")
    fun getExisting(): LiveData<List<AchievementEntity>>

    val all: LiveData<List<Achievement>>
        get() = getExisting().map {
           Achievement.IDS.map { id ->
                val entity = it.find { it.id == id } ?: AchievementEntity(
                    id,
                    0
                )
                Achievement.create(entity.id, entity.progress)
            }
        }
}