package com.teamzzong.hacker.util

import android.os.Bundle
import com.teamzzong.hacker.R
import com.teamzzong.hacker.databinding.ActivityTestBinding
import com.teamzzong.hacker.presentation.battle.attack.BattleAttackFragment
import com.teamzzong.hacker.ui.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestActivity: BindingActivity<ActivityTestBinding>(R.layout.activity_test) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }
    //TODO: 추후 삭제
    private fun init() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_test, BattleAttackFragment())
            .commit()
    }
}