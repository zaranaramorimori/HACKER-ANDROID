package com.teamzzong.hacker.presentation.battle.attack

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import com.skydoves.balloon.balloon
import com.skydoves.balloon.showAlignBottom
import com.teamzzong.hacker.R
import com.teamzzong.hacker.databinding.FragmentBattleAttackBinding
import com.teamzzong.hacker.domain.entity.battle.Team
import com.teamzzong.hacker.presentation.gallery.MyPageNotiBalloonFactory
import com.teamzzong.hacker.ui.base.BindingFragment
import com.teamzzong.hacker.ui.fragment.toast
import com.teamzzong.hacker.ui.intent.intArgs
import com.teamzzong.hacker.ui.view.Spacer
import com.teamzzong.hacker.ui.view.itemDecoration
import com.teamzzong.hacker.ui.view.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.DecimalFormat

@AndroidEntryPoint
class BattleAttackFragment :
    BindingFragment<FragmentBattleAttackBinding>(R.layout.fragment_battle_attack) {
    private val notificationBalloon by balloon<MyPageNotiBalloonFactory>()
    private val teamId by intArgs()
    private var memberAdapter: BattleMemberAdapter? = null
    private var teamLogAdapter: BattleOuterLogAdapter? = null
    private val viewModel by viewModels<BattleAttackViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initEvent()
        observe()
    }

    private fun initAdapter() {
        memberAdapter = BattleMemberAdapter()
        binding.rvTeamMember.adapter = memberAdapter
        binding.rvTeamMember.itemDecoration = Spacer {
            horizontal { 5.dp }
        }

        teamLogAdapter = BattleOuterLogAdapter()
        binding.rvTeamLog.adapter = teamLogAdapter
        binding.rvTeamLog.itemDecoration = Spacer {
            vertical { 10.dp }
        }
        viewModel.getAttackTeam(teamId)
    }

    private fun initEvent() {
        binding.ivAttack.setOnClickListener {
            viewModel.postAttackTeam(teamId)
        }

        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.ivAlarm.setOnSingleClickListener {
            binding.ivAlarm.showAlignBottom(notificationBalloon)
        }
    }

    private fun observe() {
        viewModel.logList.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it.isEmpty()) {
                    true -> {
                        binding.ivEmptyLog.isVisible = true
                        binding.rvTeamLog.isVisible = false
                    }
                    else -> {
                        binding.ivEmptyLog.isVisible = false
                        binding.rvTeamLog.isVisible = true
                        teamLogAdapter?.setItems(it)
                    }
                }

            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.memberList.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it.isEmpty()) {
                    true -> {
                        binding.ivEmptyMember.isVisible = true
                        binding.rvTeamMember.isVisible = false
                    }
                    else -> {
                        binding.ivEmptyMember.isVisible = false
                        binding.rvTeamMember.isVisible = true
                        memberAdapter?.submitList(it)
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.teamInfo.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is AttackTeamState.AttackTeamStatus -> teamData(it.team)
                    else -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.attackTeam.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is AttackTeamState.AttackTeamSuccess -> {
                        val dialog = PullHairDialog()
                        dialog.show(parentFragmentManager, "dialog")
                        viewModel.getAttackTeam(teamId)
                    }
                    is AttackTeamState.Failure -> toast(it.message)
                    else -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun teamData(teamInfo: Team) {
        val team = teamInfo.team
        setAttackValid(!(team.couponCount == 0 || teamInfo.isMyTeam))
        val dec = DecimalFormat("#,###")
        binding.apply {
            ivTeamLogo.load(team.imageUrl)
            tvTeamName.text = team.name
            tvTeamCommit.text = "${dec.format(team.commitCount)} 커밋"
            tvTeamHairCount.text = "${team.hairCount} 가닥"
            tvCouponCount.text = "x${team.couponCount}"
            ivHair.load(team.head)
            ivFace.load(team.face)
        }
    }

    private fun setAttackValid(isCheck: Boolean) {
        binding.tvCouponCount.isVisible = isCheck
        binding.ivAttack.apply {
            isSelected = isCheck
            isClickable = isCheck
        }
    }

    override fun onDestroyView() {
        memberAdapter = null
        teamLogAdapter = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance(teamId: Int) =
            BattleAttackFragment().apply {
                arguments = Bundle().apply {
                    putInt("teamId", teamId)
                }
            }
    }
}