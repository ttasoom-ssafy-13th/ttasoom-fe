package com.ssafy.feature.community.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.di.navigation.Navigator
import com.ssafy.feature.R
import com.ssafy.feature.community.BoardViewModel
import com.ssafy.feature.community.CommunityViewModel
import com.ssafy.feature.community.adapter.BoardAdapter
import com.ssafy.feature.community.adapter.CommunityAdapter
import com.ssafy.feature.databinding.FragmentCommunityBoardBinding
import com.ssafy.feature.databinding.FragmentCommunityEditBinding
import javax.inject.Inject

class CommunityBoardFragment : Fragment() {


    private var _binding: FragmentCommunityBoardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BoardViewModel by activityViewModels()

    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter: BoardAdapter

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentCommunityBoardBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        recyclerView=binding.communityCommentRv
        recyclerView.layoutManager= LinearLayoutManager(requireContext())
        viewModel.getBoardById()
        val board =viewModel.getBoard()
        val commentList = viewModel.getComments()
        adapter= BoardAdapter(board,commentList){ comment_id ->

        }
        recyclerView.adapter=adapter

        binding.communityEditOrDeleteBtn.setOnClickListener{
            val popup=PopupMenu(requireContext(),it)
            popup.menuInflater.inflate(R.menu.edit_delete_menu,popup.menu)

            popup.setOnMenuItemClickListener { item->
                when (item.itemId) {
                    R.id.menu_edit -> {
                        // 수정 동작
                        true
                    }
                    R.id.menu_delete -> {
                        // 일단 임시 Toast메시지로 한다
                        Toast.makeText(requireContext(),"삭제 완료",Toast.LENGTH_SHORT).show()
                        navigator.toPrev()
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }


    }


}