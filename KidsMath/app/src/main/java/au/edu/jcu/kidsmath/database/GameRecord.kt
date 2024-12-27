package au.edu.jcu.kidsmath.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "game_records")
data class GameRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userName: String,
    val level: String,
    val score: Int
)
