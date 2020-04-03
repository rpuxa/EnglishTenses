package english.tenses.practice.model.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class ComplexMigration(
    private vararg val migrations: MyMigration
) : MyMigration {

    override fun migrate(database: SupportSQLiteDatabase) {
       migrations.forEach {
           it.migrate(database)
       }
    }
}