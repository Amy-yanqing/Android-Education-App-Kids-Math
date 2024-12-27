package au.edu.jcu.kidsmath.activities.settingsactivity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import au.edu.jcu.kidsmath.databinding.ActivitySettingsPageBinding
import au.edu.jcu.kidsmath.activities.gameactivity.GameActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using data binding
        binding = ActivitySettingsPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the back button to finish the activity and return to the previous one
        binding.backButton.setOnClickListener {
            finish()
        }

        // Set up the start game button click event
        binding.startGameButton.setOnClickListener {
            // Get and trim the name input from the EditText
            val name = binding.nameEditText.text.toString().trim()
            // Get and trim the age input from the EditText
            val age = binding.ageEditText.text.toString().trim()


            // Determine the selected level based on which RadioButton is checked
            val level = when {
                binding.beginnerRadioButton.isChecked -> "Beginner"
                binding.advancedRadioButton.isChecked -> "Advanced"
                else -> null // No level selected
            }

            // Validate the name, age  and level inputs
            when {
                name.isEmpty() -> {
                    // Show a Toast message if the name field is empty
                    Toast.makeText(this, "Please enter your name.", Toast.LENGTH_SHORT).show()
                }
                age.isEmpty() -> {
                    // Show a Toast message if the name field is empty
                    Toast.makeText(this, "Please enter your age.", Toast.LENGTH_SHORT).show()
                }
                level == null -> {
                    // Show a Toast message if no level is selected
                    Toast.makeText(this, "Please select a level.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // If both name and level are provided, start GameActivity
                    val intent = Intent(this, GameActivity::class.java).apply {
                        putExtra("USER_NAME", name) // Pass the name to GameActivity
                        putExtra("LEVEL", level) // Pass the level to GameActivity
                        putExtra("USER_AGE", age)// Pass the age to GameActivity
                    }
                    startActivity(intent) // Start GameActivity
                }
            }
        }
    }
}
