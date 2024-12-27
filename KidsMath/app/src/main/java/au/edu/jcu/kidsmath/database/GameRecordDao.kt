package au.edu.jcu.kidsmath.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameRecordDao {

    @Insert
    suspend fun insertGameRecord(gameRecord: GameRecord)

    @Query("SELECT * FROM game_records")
    suspend fun getAllGameRecords(): List<GameRecord>

    @Query("DELETE FROM game_records")
    suspend fun deleteAllGameRecords()
}
