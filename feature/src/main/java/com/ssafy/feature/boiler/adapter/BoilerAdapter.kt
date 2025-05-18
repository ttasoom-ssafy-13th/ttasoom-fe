package com.ssafy.feature.boiler.adapter

import android.app.Dialog
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.domain.boiler.model.Boiler
import com.ssafy.feature.R
import com.ssafy.feature.databinding.DialogBoilerDetailBinding
import com.ssafy.feature.databinding.ItemBoilerBinding

class BoilerAdapter : RecyclerView.Adapter<BoilerAdapter.BoilerViewHolder>() {

    private val items = mutableListOf<Boiler>()

    fun submitList(newItems: List<Boiler>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoilerViewHolder {
        val binding = ItemBoilerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BoilerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BoilerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class BoilerViewHolder(private val binding: ItemBoilerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Boiler) {
            binding.tvCompany.text = item.companyName
            binding.tvProduct.text = item.productName
            Log.d("BoilerAdapter", "회사명: ${item.companyName}, 제품명: ${item.productName}")

            // 이미지 로딩 (비어 있으면 기본 이미지 사용)
            val imageUrl = item.imageUrl
            if (imageUrl.isNullOrBlank()) {
                binding.imgBoiler.setImageResource(R.drawable.ic_launcher_foreground)
            } else {
                Glide.with(binding.imgBoiler.context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(binding.imgBoiler)
            }

            // 아이템 클릭 시 상세 다이얼로그 표시
            binding.root.setOnClickListener {
                showDetailDialog(item)
            }
        }

        private fun showDetailDialog(boiler: Boiler) {
            val context = binding.root.context
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            
            val dialogBinding = DialogBoilerDetailBinding.inflate(LayoutInflater.from(context))
            dialog.setContentView(dialogBinding.root)

            // 다이얼로그 크기 설정
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            // 데이터 바인딩
            dialogBinding.apply {
                tvCompanyDetail.text = boiler.companyName
                tvProductDetail.text = boiler.productName
                tvCertificationDetail.text = boiler.certificationType
                tvCirculationDetail.text = boiler.circulationType
                tvFuelDetail.text = boiler.fuelType

                // 이미지 로딩
                if (boiler.imageUrl.isNullOrBlank()) {
                    imgBoilerDetail.setImageResource(R.drawable.ic_launcher_foreground)
                } else {
                    Glide.with(context)
                        .load(boiler.imageUrl)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(imgBoilerDetail)
                }

                // 확인 버튼 클릭 시 다이얼로그 닫기
                btnConfirm.setOnClickListener {
                    dialog.dismiss()
                }
            }

            dialog.show()
        }
    }
}

