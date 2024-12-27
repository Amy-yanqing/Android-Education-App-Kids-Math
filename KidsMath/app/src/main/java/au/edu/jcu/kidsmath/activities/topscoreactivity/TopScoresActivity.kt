package au.edu.jcu.kidsmath.activities.topscoreactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.databinding.DataBindingUtil
import au.edu.jcu.kidsmath.R
import au.edu.jcu.kidsmath.databinding.ActivityTopScoresBinding
import au.edu.jcu.kidsmath.database.AppDatabase
import kotlinx.coroutines.launch

class TopScoresActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var binding: ActivityTopScoresBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_top_scores)

        // Initialize the database
        database = AppDatabase.getDatabase(this)

        // Load top scores
        loadTopScores()

        // Set click listeners
        binding.clearTopRecordButton.setOnClickListener {
            clearTopRecords()
        }

        binding.TopScoreBackButton.setOnClickListener {
            finish() // Close the current activity
        }
    }

    private fun loadTopScores() {
        lifecycleScope.launch {
            val records = database.gameRecordDao().getAllGameRecords()
            val displayText = records.joinToString("\n") { "${it.userName} - ${it.level} - Score: ${it.score}" }
            binding.scoresListView.text = displayText
        }
    }

    private fun clearTopRecords() {
        lifecycleScope.launch {
            database.gameRecordDao().deleteAllGameRecords()
            binding.scoresListView.text = ""
        }
    }
}
