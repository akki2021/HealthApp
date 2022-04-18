package com.example.healthapp.ui.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.healthapp.R
import com.example.healthapp.databinding.FragmentInformationBinding

class InformationFragment : Fragment() {

    private var _binding: FragmentInformationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: InformationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this)[InformationViewModel::class.java]

        _binding = FragmentInformationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        onClickListeners()
        observeEvents()


        return root
    }

    private fun onClickListeners() {

        binding.videosCard.setOnClickListener {
            viewModel.videoCardClicked()
        }

        binding.blogsCard.setOnClickListener {
            viewModel.blogCardClicked()
        }
    }

    private fun observeEvents() {
        with(viewModel){

            onVideoCardClickEvent.observe(viewLifecycleOwner) {
                findNavController().navigate(R.id.navigation_videos)
            }

            onBlogCardClickEvent.observe(viewLifecycleOwner) {
                findNavController().navigate(R.id.navigation_blogs)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}