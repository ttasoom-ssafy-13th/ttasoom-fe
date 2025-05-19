package com.ssafy.feature.community.ui

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.data.auth.provider.AuthLocalDataSource
import com.ssafy.data.local.PreferencesManager

import com.ssafy.di.navigation.Navigator

import com.ssafy.feature.community.CommunityViewModel
import com.ssafy.feature.community.adapter.CommunityAdapter
import com.ssafy.feature.databinding.FragmentCommunityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "CommunityFragment_싸피"

@AndroidEntryPoint
class CommunityFragment : Fragment() {

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var authLocalDataSource : AuthLocalDataSource


    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CommunityViewModel by activityViewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CommunityAdapter
    private lateinit var userAuthor: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userAuthor= authLocalDataSource.getUserId().toString()
        initUI(); //UI 초기화
        Log.d(TAG, "onViewCreated: ")
    }

    private fun initUI() {

        navigator.show()
        viewModel.getBoard() //list api 호출


        binding.communityCreatePost.setOnClickListener {
            navigator.toCommunityEdit()
        } // 글쓰는 버튼

        recyclerView = binding.communityRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CommunityAdapter(userAuthor) { post_id, flag->
            navigator.toCommunityBoard(post_id, flag)
        }
        recyclerView.adapter = adapter


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.boardList.collectLatest {
                    Log.d("DEBUG", "boardList updated: $it")
                    adapter.submitList(it)
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

