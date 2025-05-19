package com.ssafy.feature.community.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
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
import com.ssafy.data.auth.provider.AuthLocalDataSource
import com.ssafy.data.local.PreferencesManager
import com.ssafy.di.navigation.Navigator
import com.ssafy.domain.community.model.Board
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
    private var currentCommentId: String? = null

    private lateinit var post_id: String //게시글 아이디.
    private var isHeart : Boolean = false
 

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
        isHeart= arguments?.getBoolean("flag") == true
        viewModel.post_id = post_id //viewModel에 post_id 넘겨주기
        navigator.hide()
        requireActivity().window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )

        viewModel.getBoardById() // 게시글 api
        viewModel.getComments() //댓글 api

        initUi()
        initEvent()

        viewLifecycleOwnerLiveData.observe(viewLifecycleOwner) { lifecycleOwner ->
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.commentsList.collectLatest { comments ->
                        adapter.submitList(comments)
                    }
                }
            }
        }


    }

    @SuppressLint("SetTextI18n")
    private fun initUi() {
        Log.d(TAG, "initUi: ${isHeart}")

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.board.collectLatest { board ->
                binding.communityEditOrDeleteBtn.visibility=if(board.author=="sungjun@gmail.com")View.VISIBLE else View.INVISIBLE

                binding.communityTitle.text = board.title
                binding.communityUser.text = board.author
                binding.communityDate.text = board.created_at
                binding.communityContent.text = board.content
                binding.communityCommentCnt.text = board.comment_count.toString()
                binding.communityHeartCnt.text = board.likedUsers.size.toString()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.likeCnt.collectLatest {

                Log.d(TAG, "initUi: ${viewModel.likeCnt.value}")
                binding.communityHeartCnt.text = viewModel.likeCnt.value.toString()
                if(isHeart)
                    binding.communityHeart.setImageResource(R.drawable.ic_heart_click)
                else
                    binding.communityHeart.setImageResource(R.drawable.ic_heart)

            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.commentCnt.collectLatest {
                binding.communityCommentCnt.text = viewModel.commentCnt.value.toString()
            }
        }


        recyclerView = binding.communityCommentRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = CommentAdapter(
            btnListener = { view, comment_id,comment ->
                showEditDeletePopupComment(view, comment_id,comment)
            }
        )

        recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.commentsList.collectLatest {
                adapter.submitList(it)
            }
        }

    }

    private fun initEvent() {

        binding.communityEditOrDeleteBtn.setOnClickListener {
            showEditDeletePopup(it)
        }//게시글 삭제 및 수정 버튼

        binding.communityCommentSendBtn.setOnClickListener {
            val commentText = binding.communityCommentEditText.text.toString().trim()
            if(commentText.isBlank()){
                Toast.makeText(requireContext(),"텍스트를 입력해주세요",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (currentCommentId == null) {
                viewModel.postComment(commentText)
                Toast.makeText(requireContext(),"댓글 등록 완료",Toast.LENGTH_SHORT).show()
            } else {
                viewModel.putComment(currentCommentId!!, commentText)
                currentCommentId = null
                Toast.makeText(requireContext(),"댓글 수정 완료",Toast.LENGTH_SHORT).show()
            }

            binding.communityCommentEditText.setText("")
            // 키보드 숨기기
            val imm =
                binding.communityCommentEditText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.communityCommentEditText.windowToken, 0)
        }//댓 달기

        binding.communityHeart.setOnClickListener {

            isHeart = !isHeart

            if(viewModel.isUpdate)
                viewModel.postLikeEmoji()
            else
                Toast.makeText(requireContext(),"UI 생성 중...",Toast.LENGTH_SHORT).show()


            it.animate()
                .scaleX(1.5f)
                .scaleY(1.5f)
                .setDuration(150)
                .withEndAction {
                    it.animate().scaleX(1f).scaleY(1f).setDuration(150).start()
                }
                .start()

        }// 이모티콘 누르기


        binding.communityCommentEditText.post {
            showKeyboard(binding.communityCommentEditText,"")
        }

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
                        binding.communityTitle.text.toString(),
                        binding.communityContent.text.toString()
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

    private fun showEditDeletePopupComment(anchorView: View, comment_id: String,comment : String) {

        val popup = PopupMenu(requireContext(), anchorView)
        popup.menuInflater.inflate(R.menu.edit_comment_menu, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_edit -> {
                    currentCommentId = comment_id
                    showKeyboard(binding.communityCommentEditText,comment)
                    true
                }

                R.id.menu_delete -> {
                    viewModel.deleteComment(comment_id)
                    Toast.makeText(requireContext(),"댓글 삭제 완료",Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }

        popup.show()
    }

    private fun showKeyboard(view: View,comment :String) {
        if(currentCommentId!=null)binding.communityCommentEditText.setText(comment)
        view.requestFocus()
        view.post {
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }


    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        val board = viewModel.board.value.copy()
        binding.communityTitle.text = board.title
        binding.communityUser.text = board.author
        binding.communityDate.text = board.created_at
        binding.communityContent.text = board.content
        binding.communityCommentCnt.text = board.comment_count.toString()
        binding.communityHeartCnt.text = board.likedUsers.size.toString()

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}