package au.edu.jcu.kidsmath.activities.gameactivity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import au.edu.jcu.kidsmath.activities.landingactivity.LandingActivity
import au.edu.jcu.kidsmath.database.AppDatabase
import au.edu.jcu.kidsmath.database.GameRecord
import au.edu.jcu.kidsmath.databinding.ActivityGamePageBinding
import au.edu.jcu.kidsmath.gamehistory.historyrecycleview.GameHistoryItem
import au.edu.jcu.kidsmath.gamehistory.historyrecycleview.GameHistoryManager
import kotlinx.coroutines.launch


private const val SCORE = 10

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGamePageBinding
    private val viewModel: GameActivityViewModel by viewModels()
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the database instance
        database = AppDatabase.getDatabase(this)

        // Retrieve userName, level, and age from the Intent
        val userName = intent.getStringExtra("USER_NAME")
        val level = intent.getStringExtra("LEVEL")
        val userAge = intent.getStringExtra("USER_AGE")?.toIntOrNull() ?: 0


        // Generate questions based on the selected level and age
        viewModel.generateQuestions(level, userAge)

        // Observe the ViewModel's LiveData to update the question TextView
        viewModel.currentQuestion.observe(this) { question ->
            question?.let { it ->
                "${it.first} + ${it.second} = ?".also { binding.questionTextView.text = it }
            }
        }

        // Observe the ViewModel's LiveData to update the current question index TextView
        viewModel.currentQuestionIndex.observe(this) { index ->
            "Question ${index + 1}".also { it.also { binding.questionNumberTextView.text = it } }
        }

        // Set up the Submit button click listener
        binding.submitButton.setOnClickListener {
            // Check if the game has ended (after the third question)
            if (viewModel.currentQuestionIndex.value!! >= viewModel.questions.size) {
                Toast.makeText(this, "Game over, can't submit.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Get the user's answer from the input field
            val answerText = binding.answerEditText.text.toString()

            if (answerText.isBlank()) {
                // Show a toast if the answer is empty
                Toast.makeText(this, "Empty Answer.", Toast.LENGTH_SHORT).show()
            } else {
                val answer = answerText.toIntOrNull()
                if (answer != null) {
                    // Check if the answer is correct
                    val correct = viewModel.checkAnswer(answer)
                    val message = if (correct) {
                        //"Correct! Score: ${viewModel.score.value}"
                        "Correct! Score: Gain $SCORE"
                    } else {
                        "Incorrect! Try again"
                    }
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                    // Delay clearing the answer input field by 400ms
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.answerEditText.setText("")
                    }, 400)

                    // Check if the third question has been answered
                    if (viewModel.currentQuestionIndex.value == 3) {
                        // Update the TextView to show a completion message
                        Toast.makeText(
                            this,
                            "Game over!",
                            Toast.LENGTH_SHORT
                        ).show()
                        "Hi $userName,all questions done. \n Game Over! Final Score: ${viewModel.score.value}".also { binding.questionTextView.text = it }
                        Toast.makeText(
                            this,
                            "Game over!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            }
        }

        binding.skipButton.setOnClickListener {
            val currentIndex = viewModel.currentQuestionIndex.value!!

            if (currentIndex < 2) {
                // If the current question is less than 3, show "Skipped" toast
                Toast.makeText(this, "Skipped", Toast.LENGTH_SHORT).show()
                viewModel.moveToNextQuestion()
            } else if (currentIndex == 2) {
                // If the current question is the 3rd one, show "You skipped, game over"
                Toast.makeText(this, "all questions done, game over", Toast.LENGTH_SHORT).show()
                "Hi $userName,Game Over! Final Score: ${viewModel.score.value}".also { binding.questionTextView.text = it }
            }


        }


        // Set up the Exit button click listener
        binding.playAgainButton.setOnClickListener {
            // Save game history and top record
            saveGameHistory(userName, viewModel.score.value ?: 0, level)
            saveGameTopRecord(userName, viewModel.score.value ?: 0, level)
            finish()
        }

        // Set up the Exit button click listener
        binding.exitButton.setOnClickListener {
            // Save game history and top record
            saveGameHistory(userName, viewModel.score.value ?: 0, level)
            saveGameTopRecord(userName, viewModel.score.value ?: 0, level)

            // Navigate to LandingActivity and clear the activity stack
            val intent = Intent(this, LandingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            Toast.makeText(this, "Exit! back to landing page", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    // Function to save game history to GameHistoryManager
    private fun saveGameHistory(userName: String?, score: Int, level: String?) {
        if (userName != null && level != null) {
            val newHistoryItem = GameHistoryItem(userName, score, level)
            GameHistoryManager.addGameHistoryItem(newHistoryItem)
        }
    }

    // Function to save the top game record to the database if the score is 30
    private fun saveGameTopRecord(userName: String?, score: Int, level: String?) {
        if (userName != null && level != null && score == 30) {
            lifecycleScope.launch {
                val newRecord = GameRecord(userName = userName, score = score, level = level)
                database.gameRecordDao().insertGameRecord(newRecord)
            }
        }
    }
}
