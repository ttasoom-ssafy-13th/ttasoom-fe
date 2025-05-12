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
import com.ssafy.domain.community.model.Board
import com.ssafy.feature.community.CommunityViewModel
import com.ssafy.feature.community.adapter.CommunityAdapter
import com.ssafy.feature.databinding.FragmentCommunityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment : Fragment() {


    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CommunityViewModel by activityViewModels()

    private lateinit var recyclerView : RecyclerView

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
        initRV(); //recyclerView 초기화
    }


    private fun initRV(){
        recyclerView=binding.communityRv
        recyclerView.layoutManager= LinearLayoutManager(requireContext())
        val adapter= CommunityAdapter{ post_id ->
            //fragmnet넘기는 로직 및 api 호출 로직
        }
        recyclerView.adapter=adapter

        viewModel.getBoard()
        viewModel.boardList.observe(viewLifecycleOwner){
           adapter.submitList(it)
        }

    }
}