package com.example.sopt_1.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sopt_1.data.RepositoryInfo
import com.example.sopt_1.databinding.ItemRepositoryBinding

class RepositoryListAdapter : RecyclerView.Adapter<RepositoryListAdapter.RepositoryViewHolder>() {

    /* 2. Adapter 는 ViewHolder 로 변경할 Data 를 가지고 있어야함 */
    val userList = mutableListOf<RepositoryInfo>()

    // 3. Adapter 는 아이템마다 ViewHolder를 만드는 방법을 정의
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding = ItemRepositoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RepositoryViewHolder(binding)
    }

    // 4. Adapter 는 전체 아이템의 수를 알아야 함
    override fun getItemCount(): Int = userList.size

    // 5. Adapter 는 ViewHolder 에 Data 를 전달하는 방법을 정의
    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.onBind(userList[position])
    }

    class RepositoryViewHolder(
        private val binding: ItemRepositoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(repositoryInfo: RepositoryInfo) {
            binding.repoData = repositoryInfo
        }
    }
}