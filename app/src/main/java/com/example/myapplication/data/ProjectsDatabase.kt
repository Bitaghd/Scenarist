package com.example.myapplication.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.model.Location
import com.example.myapplication.model.Projects
import com.example.myapplication.model.Scene

@Database(
    entities = [Projects::class, Scene::class, Location::class],
    version = 3,
    exportSchema = true,
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
                ).addMigrations(migration2to3).build()
                INSTANCE = instance
                return instance
            }
        }

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
    }


}