package english.tenses.practice.model.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object MigrationFrom1To2 : Migration(1, 2) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `sentences` (`id` INTEGER NOT NULL PRIMARY KEY, `text` TEXT NOT NULL)")
        database.execSQL("CREATE TABLE IF NOT EXISTS `answers` (" +
                "`id` INTEGER NOT NULL PRIMARY KEY," +
                "`sentenceId` INTEGER NOT NULL," +
                "`infinitive` TEXT NOT NULL," +
                "`correct` TEXT NOT NULL, " +
                "`tense` INTEGER NOT NULL," +
                "`verb` TEXT NOT NULL," +
                "`subject` TEXT NOT NULL," +
                "`person` INTEGER NOT NULL" +
                ")")
        database.execSQL("CREATE TABLE IF NOT EXISTS `translates` (`id` INTEGER NOT NULL PRIMARY KEY, `sentenceId` INTEGER NOT NULL, `language` TEXT NOT NULL, `text` TEXT NOT NULL)")
        database.execSQL("CREATE TABLE IF NOT EXISTS `learned_sentences2` (`id` INTEGER NOT NULL PRIMARY KEY)")
        database.execSQL("CREATE TABLE IF NOT EXISTS `translates` (`sentence` TEXT NOT NULL PRIMARY KEY, `translate` TEXT NOT NULL)")
    }
}