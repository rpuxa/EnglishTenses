package english.tenses.practice.model.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.room.*
import english.tenses.practice.model.Achievement

@Dao
interface AchievementDao {

    @Query("SELECT * FROM achievements WHERE id = :id")
    suspend fun getOrNull(id: Int): AchievementEntity?

    suspend fun get(id: Int) = getOrNull(id) ?: AchievementEntity(id, 0)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(achievementEntity: AchievementEntity)

    @Query("SELECT * FROM achievements")
    fun getExisting(): LiveData<List<AchievementEntity>>

    val all: LiveData<List<Achievement>>
        get() = getExisting().map {
            val existing = ArrayList(it)
            Achievement.IDS.forEach { id ->
                if (it.all { it.id != id }) {
                    existing += AchievementEntity(id, 0)
                }
            }
            existing.map { Achievement.create(it.id, it.progress) }
        }
}