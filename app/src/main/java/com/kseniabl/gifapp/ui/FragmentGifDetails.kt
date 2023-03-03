package com.kseniabl.gifapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.kseniabl.gifapp.Helper.getShimmerDrawable
import com.kseniabl.gifapp.databinding.FragmentGifDetailsBinding

class FragmentGifDetails: Fragment() {

    private var _binding: FragmentGifDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: FragmentGifDetailsArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGifDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = args.item
        binding.apply {
            usernameValue.text = item.title
            titleValue.text = item.title
            ratingValue.text = item.rating

            Glide.with(gifHolder.context)
                .asGif()
                .load(item.images.original.url)
                .placeholder(getShimmerDrawable())
                .into(gifHolder)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}