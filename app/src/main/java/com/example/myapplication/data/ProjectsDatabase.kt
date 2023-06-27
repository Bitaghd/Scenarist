package com.example.myapplication.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.model.Characters
import com.example.myapplication.model.Details
import com.example.myapplication.model.Location
import com.example.myapplication.model.Projects
import com.example.myapplication.model.Scene

@Database(
    entities = [Projects::class, Scene::class, Location::class, Characters::class, Details::class],
    version = 7,
    exportSchema = true,
    autoMigrations =  [
        AutoMigration(from = 3, to = 4),
        AutoMigration(from = 6, to = 7)
        //AutoMigration(from = 4, to = 5)
        //AutoMigration(from = 4, to = 5, spec = ProjectsDatabase.Companion.AutoMigration4to5Ver2::class)
    ]
    //autoMigrations = [AutoMigration(from = 1, to = 2)]
)

@TypeConverters(Converters::class)
abstract class ProjectsDatabase : RoomDatabase() {
    abstract fun projectsDao(): ProjectsDao

    companion object{
        @Volatile
        private var INSTANCE: ProjectsDatabase? = null
        fun getDatabase(context: Context): ProjectsDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProjectsDatabase::class.java,
                    "projects_database"
                ).addMigrations(migration1to2, migration2to3, migration4to5, migration5to6).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }

        }

        @RenameColumn.Entries(RenameColumn(tableName = "location", fromColumnName = "image", toColumnName = "desc"),
        RenameColumn(tableName = "location", fromColumnName = "desc", toColumnName = "image"))
        class AutoMigration4to5 : AutoMigrationSpec
        @DeleteColumn.Entries(
            DeleteColumn(tableName = "location", columnName = "image")
        )
        class AutoMigration4to5Ver2: AutoMigrationSpec

        private val migration1to2 = object : Migration (1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `scene` (`scene_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `scene_name` TEXT NOT NULL, `location` TEXT NOT NULL, `desc` TEXT NOT NULL, `projectID` INTEGER NOT NULL)")
            }

        }
        private val migration2to3 = object : Migration(2,3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `location` (`location_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `location_name` TEXT NOT NULL, `location_scene` TEXT NOT NULL, `projectID` INTEGER NOT NULL, `image` BLOB NOT NULL)")
            }

        }

        private val migration4to5 = object : Migration(4,5){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE 'location'")
                database.execSQL("CREATE TABLE IF NOT EXISTS `location` (`location_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `location_name` TEXT NOT NULL, `location_scene` TEXT NOT NULL, `projectID` INTEGER NOT NULL, `image` TEXT, `desc` TEXT NOT NULL DEFAULT 'not null')")
            }

        }

        private val migration5to6 = object  : Migration(5,6){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `character` (`char_id` INTEGER NOT NULL, `char_name` TEXT NOT NULL, `char_alias` TEXT NOT NULL, `char_scene` TEXT NOT NULL, `bio` TEXT NOT NULL, `projectID` INTEGER NOT NULL, `profile_image` TEXT, PRIMARY KEY(`char_id`))")
            }

        }
    }


}