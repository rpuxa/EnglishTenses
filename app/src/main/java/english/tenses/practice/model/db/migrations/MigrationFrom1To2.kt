package english.tenses.practice.model.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object MigrationFrom1To2 : Migration(1, 2) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `translates` (`sentence` TEXT NOT NULL PRIMARY KEY, `translate` TEXT NOT NULL)")
    }
}