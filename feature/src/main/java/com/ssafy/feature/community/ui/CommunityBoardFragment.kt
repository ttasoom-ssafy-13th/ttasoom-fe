package com.ssafy.feature.community.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.di.navigation.Navigator
import com.ssafy.domain.community.model.Board
import com.ssafy.feature.LikedSharedPref
import com.ssafy.feature.R
import com.ssafy.feature.community.BoardViewModel
import com.ssafy.feature.community.adapter.CommentAdapter
import com.ssafy.feature.databinding.FragmentCommunityBoardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
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
    private lateinit var adapter: CommentAdapter
    private  var isLike = false
    private var currentCommentId : String? = null

    private lateinit var post_id: String //게시글 아이디.
    private lateinit var prefManager : LikedSharedPref

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

        viewModel.getBoardById() // 게시글 api
        viewModel.getComments() //댓글 api

        prefManager = LikedSharedPref(requireContext())
        isLike= prefManager.getFlagForId(post_id)
        initUi()
        initEvent()


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.commentsList.collectLatest { comments ->
                    adapter.submitList(comments)
                }
            }
        }

    }

    private fun initUi() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.board.collectLatest { board ->
                    binding.communityTitle.text=board.title
                    binding.communityUser.text=board.author
                    binding.communityDate.text=board.created_at
                    binding.communityContent.text=board.content
                    binding.communityCommentCnt.text=board.comment_count.toString()
                    binding.communityHeartCnt.text=board.likedUsers.size.toString()
                    if(isLike)binding.communityHeart.setImageResource(R.drawable.ic_heart_click)
                }
            }
        }


        Log.d(TAG, "initUi: ${isLike}")

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.likeCnt.collectLatest {
                    binding.communityHeartCnt.text = viewModel.likeCnt.value.toString()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.commentCnt.collectLatest {
                    binding.communityCommentCnt.text=viewModel.commentCnt.value.toString()
                }
            }

        }


        recyclerView = binding.communityCommentRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = CommentAdapter(
            btnListener = { view, comment_id->
                showEditDeletePopupComment(view,comment_id)
            }
        )

        recyclerView.adapter = adapter

    }

   private fun  initEvent(){

        binding.communityEditOrDeleteBtn.setOnClickListener {
            showEditDeletePopup(it)
        }//게시글 삭제 및 수정 버튼

        binding.communityCommentSendBtn.setOnClickListener {
            val commentText = binding.communityCommentEditText.text.toString()

            if(currentCommentId==null){
                viewModel.postComment(commentText)
            }else{
                viewModel.putComment(currentCommentId!!, commentText)
                currentCommentId=null
            }

            binding.communityCommentEditText.setText("")
            // 키보드 숨기기
            val imm = binding.communityCommentEditText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.communityCommentEditText.windowToken, 0)
        }//댓 달기

       binding.communityHeart.setOnClickListener{
           isLike = !isLike

           if(isLike){
               prefManager.setFlagForId(post_id, true)
               binding.communityHeart.setImageResource(R.drawable.ic_heart_click)
           }else{
               prefManager.setFlagForId(post_id, false)
               binding.communityHeart.setImageResource(R.drawable.ic_heart)
           }

           viewModel.postLikeEmoji()
       }// 이모티콘 누르기


       binding.communityCommentEditText.post{
           showKeyboard(binding.communityCommentEditText)
       }
       binding.communityCommentEditText.setOnFocusChangeListener { _, hasFocus ->
//           if (!hasFocus) {
//               currentCommentId = null
//           }
       } // 키보드 닫으면 currentCommentId ==null




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

    private fun showEditDeletePopupComment(anchorView: View,comment_id : String) {

        val popup = PopupMenu(requireContext(), anchorView)
        popup.menuInflater.inflate(R.menu.edit_comment_menu, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_edit -> {
                    currentCommentId=comment_id
                    showKeyboard(binding.communityCommentEditText)
                    true
                }

                R.id.menu_delete -> {
                    viewModel.deleteComment(comment_id)
                    true
                }

                else -> false
            }
        }

        popup.show()
    }

    fun showKeyboard(view: View) {
        view.post {
            view.requestFocus()
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}