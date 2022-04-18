package com.example.healthapp.ui.videos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthapp.databinding.FragmentVideosBinding
import com.example.healthapp.ui.videos.adapter.VideoAdapter
import com.example.healthapp.ui.videos.data.VideoModel

class VideosFragment : Fragment(), VideoAdapter.OnItemClicked {

    private var _binding: FragmentVideosBinding? = null
    private var viewModel: VideosViewModel? = null
    private lateinit var recyclerView: RecyclerView
    private val data = ArrayList<VideoModel>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[VideosViewModel::class.java]

        _binding = FragmentVideosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        return root
    }

    private fun setupRecyclerView() {
        recyclerView = binding.videoRecyclerView

        recyclerView.layoutManager = LinearLayoutManager(context)

        data.add(
            VideoModel(
                "XMcab1MFaLc",
                "https://img.youtube.com/vi/XMcab1MFaLc/mqdefault.jpg",
                "A healthy diet, a healthier world"
            )
        )

        data.add(
            VideoModel(
                "Bnd2Uir_O3g",
                "https://img.youtube.com/vi/Bnd2Uir_O3g/mqdefault.jpg",
                "What makes us healthy"
            )
        )

        data.add(
            VideoModel(
                "QWF9mGtjju4",
                "https://img.youtube.com/vi/QWF9mGtjju4/mqdefault.jpg",
                "12 HEALTHY HABITS & TIPS | change your life + feel better long term\n"
            )
        )

        data.add(
            VideoModel(
                "dhpCdqOtuj0",
                "https://img.youtube.com/vi/dhpCdqOtuj0/mqdefault.jpg",
                "Well being for Children: Healthy Habits"
            )
        )

        data.add(
            VideoModel(
                "c06dTj0v0sM",
                "https://img.youtube.com/vi/c06dTj0v0sM/mqdefault.jpg",
                "Nutrition for a Healthy Life"
            )
        )
        data.add(
            VideoModel(
                "azbaxrg75A4",
                "https://img.youtube.com/vi/azbaxrg75A4/mqdefault.jpg",
                "Health care for all: let's make it a reality"
            )
        )

        data.add(
            VideoModel(
                "0aNNYEUARAk",
                "https://img.youtube.com/vi/0aNNYEUARAk/mqdefault.jpg",
                "Tips for Starting a Healthy Lifestyle!"
            )
        )

        data.add(
            VideoModel(
                "uZX14W4rVCU",
                "https://img.youtube.com/vi/uZX14W4rVCU/mqdefault.jpg",
                "Let's be active for health for all"
            )
        )

        val adapter = VideoAdapter(data)
        adapter.setOnClick(this)
        // Setting the Adapter with the recyclerview
        recyclerView.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        val videoModel = data[position]
        startActivity(
            Intent(context, PlayVideoActivity::class.java)
                .putExtra("videoID", videoModel.videoId)
                .putExtra("videoTitle", videoModel.videoTitle)
        )
    }
}