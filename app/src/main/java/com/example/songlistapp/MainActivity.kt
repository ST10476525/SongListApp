package com.example.songlistapp

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.songlistapp.ui.theme.SongListAppTheme
import org.w3c.dom.Comment

class MainActivity : ComponentActivity() {

    private var songTitle = ArrayList<String>()
    private var artistNames = ArrayList<String>()
    private var ratings = ArrayList<String>()
    private var comments = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)

        //layout References
        val addLayout = findViewById<LinearLayout>(R.id.layoutAdd)
        val viewLayout= findViewById<LinearLayout>(R.id.layoutView)

        //Added screen buttons
        val btnAddPlaylist = findViewById<Button>(R.id.btnAdd)
        val btnGoToView = findViewById<Button>(R.id.btnGoToView)
        val btnExit  = findViewById<Button>(R.id.btnExit)

        //View layout Buttons
        val btnShowAll = findViewById<Button>(R.id.btnShowAll)
        val btnShowRating = findViewById<Button>(R.id.btnShowRating)
        val btnBack = findViewById<Button>(R.id.btnBack)
        val txtResults = findViewById<TextView>(R.id.txtResult)

        //Screen Navigation
        btnGoToView.setOnClickListener {
            addLayout.visibility = View.GONE
            addLayout.visibility = View.VISIBLE
        }
        btnBack.setOnClickListener {
            addLayout.visibility = View.GONE
            viewLayout.visibility = View.VISIBLE
        }
        btnExit.setOnClickListener {
            finish()
        }
        btnAddPlaylist.setOnClickListener {
            val detailsView = layoutInflater.inflate(R.layout.layout_details,null)
            val editSong = detailsView.findViewById<EditText>(R.id.editSong)
            val editArtists = detailsView.findViewById<EditText>(R.id.editArtist)
            val editRatings= detailsView.findViewById<EditText>(R.id.editRatings)
            val editComments =detailsView.findViewById<EditText>(R.id.editComments)

            android.app.AlertDialog.Builder(this)
                .setTitle("Add Song and artist detials")
                .setView(detailsView)
                .setPositiveButton("Add"){_, _->

                    val song = editSong.text.toString()
                    val artist = editArtists.text.toString()
                    val rating = editRatings.text.toString().toIntOrNull()
                    val comment = editComments.text.toString()

                    if (song.isBlank() || artist.isBlank() || rating == null || comment.isBlank()) {
                        showError("All Fields must be filled in.")
                        Log.e("Error", "Invalid input: $song, $artist, $rating, $comment")
                        return@setPositiveButton
                    }

                    songTitle.add(song)
                    artistNames.add(artist)
                    ratings.add(rating.toString())
                    comments.add(comment)

                    Log.i("SONG_ADDED", "$song added with ratings $rating.")
                }
                .setNegativeButton("Cancel",null)
                .show()
        }
        //Display all Songs
        btnShowAll.setOnClickListener {
            val result = StringBuilder()
            for (i in songTitle.indices) {
                result.append("Songs: ${songTitle[i]}\nArtist: ${artistNames[i]}\nRatings: ${ratings[i]}\nComments: ${comments[i]}\n\n")
            }
            txtResults.text = result.toString()
            Log.i("DISPLAY", "All songs displayed.")
        }

        //Display songs with ratings >= 2
        btnShowRating.setOnClickListener {
            val result = StringBuilder()
            for (i in ratings.indices) {
                result.append("song: ${songTitle[i]} - rating: ${ratings[i]}\n")
            }
        }
        txtResults.text = txtResults.toString()
        Log.i("DISPLAY", "Filtered songs displayed")

}
private fun showError(message: String) {
    AlertDialog.Builder(this)
        .setTitle("Error")
        .setMessage(message)
        .setPositiveButton("OK", null)
        .show()
}
}

