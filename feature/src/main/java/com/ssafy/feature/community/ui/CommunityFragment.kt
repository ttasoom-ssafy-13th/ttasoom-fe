package com.ssafy.feature.community.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.di.navigation.Navigator
import com.ssafy.domain.community.model.Board
import com.ssafy.feature.community.CommunityViewModel
import com.ssafy.feature.community.adapter.CommunityAdapter
import com.ssafy.feature.databinding.FragmentCommunityBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CommunityFragment : Fragment() {

    @Inject
    lateinit var navigator: Navigator

    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CommunityViewModel by activityViewModels()

    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : CommunityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentCommunityBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(); //UI 초기화
    }


    private fun initUI(){

        navigator.show()
        
        recyclerView=binding.communityRv
        recyclerView.layoutManager= LinearLayoutManager(requireContext())
        adapter= CommunityAdapter{ post_id ->
            navigator.toCommunityBoard()
            //fragmnet넘기는 로직 및 api 호출 로직
        }
        recyclerView.adapter=adapter

        viewModel.getBoard() //list api 호출
        viewModel.boardList.observe(viewLifecycleOwner){
           adapter.submitList(it)
        }

        binding.communityCreatePost.setOnClickListener{
            navigator.toCommunityEdit()
        } // 글쓰는 버튼

    }

    override fun onResume() {
        super.onResume()
        viewModel.getBoard() // 데이터를 다시 요청
        viewModel.boardList.observe(viewLifecycleOwner) {
            adapter.submitList(it) // 데이터가 갱신될 때마다 UI를 업데이트
        }
    }

}