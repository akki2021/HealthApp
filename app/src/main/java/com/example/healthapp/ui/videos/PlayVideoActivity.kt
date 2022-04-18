package com.example.healthapp.ui.videos

import android.content.Intent
import android.os.Bundle
import com.example.healthapp.R
import com.example.healthapp.databinding.ActivityPlayVideoBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer

class PlayVideoActivity : YouTubeBaseActivity() {

    private lateinit var binding: ActivityPlayVideoBinding

    private lateinit var lectureLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        var lectureID: Any = ""
        if (bundle != null) {
            lectureID = bundle.get("lectureID")!!
            val lectureTitleText = bundle.get("lectureTitle")
            binding.lectureTitle.text = lectureTitleText.toString()
        }

        lectureLink = "https://www.youtube.com/watch?v=$lectureID"

        binding.ytPlayer.initialize(
            getString(R.string.developer_key),
            object : YouTubePlayer.OnInitializedListener {

                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider,
                    youTubePlayer: YouTubePlayer, b: Boolean
                ) {
                    youTubePlayer.cueVideo(lectureID.toString())
                    youTubePlayer.play()
                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider,
                    youTubeInitializationResult: YouTubeInitializationResult
                ) {
                    Snackbar.make(
                        binding.parentLayout,
                        "Video player Failed",
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
            })


        shareVideo()
    }

    private fun shareVideo() {
        binding.lectureShare.setOnClickListener {
            val share = Intent(Intent.ACTION_SEND)
            share.type = "text/plain"
            share.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
            share.putExtra(Intent.EXTRA_TEXT, lectureLink)
            this.startActivity(Intent.createChooser(share, "Share Quote!"))
        }
    }
}