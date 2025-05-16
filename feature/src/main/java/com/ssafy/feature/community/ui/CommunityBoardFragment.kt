package com.ssafy.feature.community.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.di.navigation.Navigator
import com.ssafy.domain.community.model.Board
import com.ssafy.domain.community.model.Comment
import com.ssafy.feature.R
import com.ssafy.feature.community.BoardViewModel
import com.ssafy.feature.community.CommunityViewModel
import com.ssafy.feature.community.adapter.BoardAdapter
import com.ssafy.feature.community.adapter.CommunityAdapter
import com.ssafy.feature.databinding.FragmentCommunityBoardBinding
import com.ssafy.feature.databinding.FragmentCommunityEditBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

private const val TAG = "CommunityBoardFragment_싸피"

@AndroidEntryPoint
class CommunityBoardFragment : Fragment() {


    private var _binding: FragmentCommunityBoardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BoardViewModel by activityViewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BoardAdapter

    private lateinit var post_id: String //게시글 아이디.

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        post_id = arguments?.getString("post_id").toString() // bundle에서 id획득
        viewModel.post_id = post_id //viewModel에 post_id 넘겨주기
        navigator.hide()
        initUi()
    }

    private fun initUi() {

        recyclerView = binding.communityCommentRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getBoardById() // 게시글 api
        viewModel.getComments() //댓글 api

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.board.collectLatest { board ->
                    adapter = BoardAdapter(
                        board,
                        mutableListOf(),
                        clickLikeListener = {
                            viewModel.postLikeEmoji()
                        },
                        editListener = { comment_id, new_comment ->
                            viewModel.putComment(comment_id, new_comment)
                        },
                        removeListener = { comment_id ->
                            viewModel.deleteComment(comment_id)
                        })

                    recyclerView.adapter = adapter
                }
            }
        } //게시글 부터 recyclerview adapter에 붙여놓기

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.commentsList.collectLatest { comments ->
                    if (::adapter.isInitialized) {
                        adapter.updateComments(comments)
                    }
                }
            }
        }  //commentList부분 관찰


        binding.communityEditOrDeleteBtn.setOnClickListener {
            showEditDeletePopup(it)
        }//게시글 삭제 및 수정 버튼

        binding.communityCommentSendBtn.setOnClickListener {

            val commentText = binding.communityCommentEditText.text.toString()
            viewModel.postComment(commentText)
            binding.communityCommentEditText.setText("")
        }//댓 달기
    }


    private fun showEditDeletePopup(anchorView: View) {
        val popup = PopupMenu(requireContext(), anchorView)
        popup.menuInflater.inflate(R.menu.edit_delete_menu, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_edit -> {
                    navigator.toCommunityEdit(
                        true,
                        post_id,
                        adapter.getBoardTitle(),
                        adapter.getBoardContent()
                    )
                    true
                }

                R.id.menu_delete -> {
                    Log.d(TAG, "showEditDeletePopup: ${post_id}")
                    Toast.makeText(requireContext(), "삭제 완료", Toast.LENGTH_SHORT)
                        .show()//추후 알람창 띄울 예정
                    viewModel.deleteBoardById()
                    navigator.toPrev()
                    true
                }

                else -> false
            }
        }

        popup.show()
    }// 본인 아이디일 때 삭제 및 수정 하는 버튼.


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}