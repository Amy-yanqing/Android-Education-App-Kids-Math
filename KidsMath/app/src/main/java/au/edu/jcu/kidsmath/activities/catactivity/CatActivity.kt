package au.edu.jcu.kidsmath.activities.catactivity


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import au.edu.jcu.kidsmath.databinding.ActivityCatBinding
import au.edu.jcu.kidsmath.thecatAPI.CatImageResponse
import au.edu.jcu.kidsmath.thecatAPI.RetrofitClient
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using Data Binding
        binding = ActivityCatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchCatImage()

        binding.likeButton.setOnClickListener {
            Toast.makeText(this, "Thank you! purr purr !", Toast.LENGTH_SHORT).show()
        }

        binding.dislikeButton.setOnClickListener {
            Toast.makeText(this, "hiss!", Toast.LENGTH_SHORT).show()
        }

        binding.refreshButton.setOnClickListener {
            fetchCatImage()
        }

        binding.exitButton.setOnClickListener {
            finish()
        }
    }

    // Function to fetch a cat image from the API
    private fun fetchCatImage() {
        // Make an asynchronous request to get cat images
        RetrofitClient.instance.getCatImages().enqueue(object : Callback<List<CatImageResponse>> {
            override fun onResponse(
                call: Call<List<CatImageResponse>>,
                response: Response<List<CatImageResponse>>
            ) {
                if (response.isSuccessful) {
                    val catImages = response.body()
                    // Check if the response contains cat images and load the first one
                    if (!catImages.isNullOrEmpty()) {
                        val imageUrl = catImages[0].url
                        // Load the cat image using Picasso library
                        Picasso.get().load(imageUrl).into(binding.myCat)
                    }
                } else {
                    Toast.makeText(this@CatActivity, "Failed to load cat image", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<List<CatImageResponse>>, t: Throwable) {
                Toast.makeText(this@CatActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
