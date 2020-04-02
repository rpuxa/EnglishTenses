package english.tenses.practice.model.db.migrations

import androidx.sqlite.db.SupportSQLiteDatabase

interface MyMigration {
    fun migrate(database: SupportSQLiteDatabase)
}