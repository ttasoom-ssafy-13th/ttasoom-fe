package com.ssafy.feature.community.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ssafy.di.navigation.Navigator
import com.ssafy.feature.R
import com.ssafy.feature.community.CommunityViewModel
import com.ssafy.feature.databinding.FragmentCommunityBinding
import com.ssafy.feature.databinding.FragmentCommunityEditBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CommunityEditFragment : Fragment() {

    private var _binding: FragmentCommunityEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CommunityViewModel by activityViewModels()

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentCommunityEditBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator.hide()

        binding.communityEditRegisterBtn.setOnClickListener {
            val content=binding.communityEditContent.text.toString()
            val title= binding.communityEditTitle.text.toString()
            viewModel.postBoard(title,content)

            Toast.makeText(requireContext(),"등록 완료",Toast.LENGTH_LONG).show()
            navigator.toPrev()
        }


    }


}