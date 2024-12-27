package au.edu.jcu.kidsmath
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import au.edu.jcu.kidsmath.database.AppDatabase
import au.edu.jcu.kidsmath.database.GameRecord
import au.edu.jcu.kidsmath.database.GameRecordDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class GameRecordDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var gameRecordDao: GameRecordDao

    @Before
    fun setup() {
        // Create an in-memory database for testing
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        gameRecordDao = database.gameRecordDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertGameRecord() = runBlocking {
        // Create a GameRecord instance
        val gameRecord = GameRecord(userName = "test_user", level = "Beginner", score = 10)

        // Insert the GameRecord into the database
        gameRecordDao.insertGameRecord(gameRecord)

        // Retrieve the records and check if the inserted record exists
        val records = gameRecordDao.getAllGameRecords()
        assertEquals(1, records.size)
        assertEquals(gameRecord.userName, records[0].userName)
        assertEquals(gameRecord.level, records[0].level)
        assertEquals(gameRecord.score, records[0].score)
    }

    @Test
    fun deleteAllGameRecords() = runBlocking {
        // Insert a GameRecord into the database
        val gameRecord = GameRecord(userName = "test_user", level = "Beginner", score = 10)
        gameRecordDao.insertGameRecord(gameRecord)

        // Verify that the record is inserted
        var records = gameRecordDao.getAllGameRecords()
        assertEquals(1, records.size)

        // Delete all records
        gameRecordDao.deleteAllGameRecords()

        // Verify that the records are deleted
        records = gameRecordDao.getAllGameRecords()
        assertEquals(0, records.size)
    }
}
