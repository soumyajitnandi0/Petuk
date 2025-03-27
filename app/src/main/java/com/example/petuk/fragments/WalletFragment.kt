package com.example.petuk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petuk.R
import com.example.petuk.adapters.RewardHistoryAdapter
import com.example.petuk.repository.RewardRepository

class WalletFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: LinearLayout
    private lateinit var tvRewards: TextView
    private lateinit var btnRedeem: Button
    private lateinit var rewardRepository: RewardRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rewardRepository = RewardRepository(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_wallet, container, false)

        // Initialize views
        tvRewards = view.findViewById(R.id.tv_rewards)
        recyclerView = view.findViewById(R.id.recycler_rewards_history)
        emptyView = view.findViewById(R.id.empty_state)
        btnRedeem = view.findViewById(R.id.btn_redeem)

        // Setup recycler view
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Setup redeem button
        btnRedeem.setOnClickListener {
            // In a real app, implement redemption logic
            // For now, show a toast message
            android.widget.Toast.makeText(
                requireContext(),
                "Redemption feature coming soon!",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        updateRewardInfo()
    }

    private fun updateRewardInfo() {
        // Get total rewards
        val totalRewards = rewardRepository.getTotalRewards()
        tvRewards.text = "₹${totalRewards.toInt()}"

        // Get reward history
        val rewardHistory = rewardRepository.getRewardHistory()

        // Update UI based on whether there's history
        if (rewardHistory.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
            btnRedeem.isEnabled = totalRewards > 0
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
            btnRedeem.isEnabled = true

            // Set adapter
            val adapter = RewardHistoryAdapter(rewardHistory)
            recyclerView.adapter = adapter
        }
    }
}