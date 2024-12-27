package au.edu.jcu.kidsmath.activities.gamehistoryactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import au.edu.jcu.kidsmath.databinding.ActivityGameHistoryBinding
import au.edu.jcu.kidsmath.gamehistory.historyrecycleview.GameHistoryAdapter

class GameHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameHistoryBinding
    private val historyAdapter = GameHistoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = historyAdapter

        // Load game history items from GameHistoryManager
        loadGameHistory()

        // Set up the back button to finish the activity and return to the previous one
        binding.backButton.setOnClickListener {
            finish()  // return to the previous one
        }
    }

    private fun loadGameHistory() {
        historyAdapter.updateItems() // Update adapter data from GameHistoryManager
    }
}
