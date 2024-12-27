// LandingPageActivity.kt
package au.edu.jcu.kidsmath.activities.landingactivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import au.edu.jcu.kidsmath.activities.settingsactivity.SettingsActivity
import au.edu.jcu.kidsmath.databinding.ActivityLandingPageBinding
import au.edu.jcu.kidsmath.activities.gamehistoryactivity.GameHistoryActivity
import au.edu.jcu.kidsmath.activities.topscoreactivity.TopScoresActivity
import au.edu.jcu.kidsmath.activities.catactivity.CatActivity


class LandingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.allScoreButton.setOnClickListener {
            val intent = Intent(this, GameHistoryActivity::class.java)
            startActivity(intent)

        }
        binding.topScoreButton.setOnClickListener {
            val intent = Intent(this, TopScoresActivity::class.java)
            startActivity(intent)

        }

        binding.myCatButton.setOnClickListener {
            val intent = Intent(this, CatActivity::class.java)
            startActivity(intent)

        }

    }
}
