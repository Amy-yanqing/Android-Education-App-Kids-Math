package au.edu.jcu.kidsmath.activities.gameactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameActivityViewModel : ViewModel() {

    // LiveData to observe the current score
    private val _score = MutableLiveData(0)
    val score: LiveData<Int> get() = _score

    // LiveData to observe the index of the current question
    private val _currentQuestionIndex = MutableLiveData(0)
    val currentQuestionIndex: LiveData<Int> get() = _currentQuestionIndex

    // List to store the questions
    private val _questions = mutableSetOf<Pair<Int, Int>>()
    val questions: List<Pair<Int, Int>> get() = _questions.toList()

    // LiveData to observe the current question
    private val _currentQuestion = MutableLiveData<Pair<Int, Int>>()
    val currentQuestion: LiveData<Pair<Int, Int>> get() = _currentQuestion

    // Generates questions based on the selected level and age
    fun generateQuestions(level: String?, age: Int) {
        val (min, max) = when {
            age <= 3 -> {
                when (level) {
                    "Beginner" -> 1 to 5
                    "Advanced" -> 5 to 10
                    else -> 1 to 5
                }
            }
            else -> {
                when (level) {
                    "Beginner" -> 10 to 50
                    "Advanced" -> 51 to 100
                    else -> 10 to 50
                }
            }
        }
        _questions.clear() // Clear any existing questions

        // Generate unique questions
        while (_questions.size < 3) {
            val question = Pair((min..max).random(), (min..max).random())
            _questions.add(question)
        }

        _currentQuestionIndex.value = 0
        _currentQuestion.value = _questions.firstOrNull()
    }

    // Checks the answer and updates the score if correct
    fun checkAnswer(answer: Int?): Boolean {
        val correct = answer == _currentQuestion.value?.let { it.first + it.second }
        if (correct) {
            _score.value = _score.value?.plus(10)
        }
        moveToNextQuestion()
        return correct
    }

    // Moves to the next question
    fun moveToNextQuestion() {
        _currentQuestionIndex.value = _currentQuestionIndex.value?.plus(1)
        if (_currentQuestionIndex.value!! < _questions.size) {
            _currentQuestion.value = _questions.elementAtOrNull(_currentQuestionIndex.value!!)
        }
    }
}
