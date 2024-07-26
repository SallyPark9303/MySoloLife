package com.sally.mysololife.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.sally.mysololife.R
import com.sally.mysololife.databinding.FragmentBookmarkBinding


class BookmarkFragment : Fragment() {



   private lateinit var binding : FragmentBookmarkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.tipTap.setOnClickListener{
            Log.d("HomeFragment","tipTap")
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_tipFragment)

        }

        binding.talkTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_talkFragment)
        }

        binding.homeTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_homeFragment)
        }

        binding.storeTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_storeFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}