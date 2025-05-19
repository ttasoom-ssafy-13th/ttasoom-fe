package com.ssafy.feature.community.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ssafy.data.auth.provider.AuthLocalDataSource
import com.ssafy.di.navigation.Navigator
import com.ssafy.domain.community.model.Board
import com.ssafy.feature.R
import com.ssafy.feature.community.BoardViewModel
import com.ssafy.feature.community.CommunityViewModel
import com.ssafy.feature.community.EditViewModel
import com.ssafy.feature.community.ProfileImg
import com.ssafy.feature.databinding.FragmentCommunityBinding
import com.ssafy.feature.databinding.FragmentCommunityEditBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import javax.inject.Inject

private const val TAG = "CommunityEditFragment_싸피"

@AndroidEntryPoint
class CommunityEditFragment : Fragment() {

    private var _binding: FragmentCommunityEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditViewModel by activityViewModels()
    private val viewModel_Board: BoardViewModel by activityViewModels()


    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var authLocalDataSource : AuthLocalDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator.hide()

        val post_id = arguments?.getString("post_id") ?: ""
        val isBoard = arguments?.getBoolean("isBoard") == true

        val title = arguments?.getString("title") ?: ""
        val content = arguments?.getString("content") ?: ""

        if (isBoard) initUIModifyVersion(title, content)
        binding.communityProfileImg.setImageResource(ProfileImg.setImg(authLocalDataSource.getUserId()!!))

        binding.communityEditRegisterBtn.setOnClickListener {
            val content = binding.communityEditContent.text.toString().trim()
            val title = binding.communityEditTitle.text.toString().trim()


            if (content.isBlank() || title.isBlank()) {
                Toast.makeText(requireContext(), "텍스트를 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (!isBoard) {
                viewModel.postBoard(title, content)
                Toast.makeText(requireContext(), "등록 완료", Toast.LENGTH_LONG).show()
            } else {
                viewModel.putBoard(post_id, title, content)
                Toast.makeText(requireContext(), "수정 완료", Toast.LENGTH_LONG).show()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.isUpdate.collectLatest {
                if(it) navigator.toPrev()

            }
        }


    }

    private fun initUIModifyVersion(title: String, content: String) {
        binding.communityEditRegisterBtn.text = "MODIFY"
        binding.communityEditTitle.setText(title)
        binding.communityEditContent.setText(content)
    } //수정 할 때 모드

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}