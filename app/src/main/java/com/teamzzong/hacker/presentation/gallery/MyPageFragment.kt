package com.teamzzong.hacker.presentation.gallery

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.fragment.app.commit
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.tabs.TabLayout
import com.skydoves.balloon.*
import com.teamzzong.hacker.R
import com.teamzzong.hacker.databinding.FragmentMyPageBinding
import com.teamzzong.hacker.presentation.gallery.content.gallery.GalleryFragment
import com.teamzzong.hacker.presentation.gallery.content.ranking.RankingFragment
import com.teamzzong.hacker.ui.base.BindingFragment
import com.teamzzong.hacker.ui.context.colorOf
import com.teamzzong.hacker.ui.view.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val notificationBalloon by balloon<MyPageNotiBalloonFactory>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.commit {
            replace(R.id.container_mypage_content, GalleryFragment())
        }
        binding.imgMypageNoti.setOnSingleClickListener {
            binding.imgMypageNoti.showAlignBottom(notificationBalloon)
        }
        binding.tablayoutMypage.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    childFragmentManager.commit {
                        replace(R.id.container_mypage_content, fragmentOf(tab?.position))
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
                override fun onTabReselected(tab: TabLayout.Tab?) = Unit
            }
        )
    }

    private fun fragmentOf(position: Int?) = when (position) {
        0 -> GalleryFragment()
        1 -> RankingFragment()
        else -> throw IllegalStateException("MyPageFragment: Invalidate Position: $position")
    }
}

class MyPageNotiBalloonFactory : Balloon.Factory() {
    override fun create(context: Context, lifecycle: LifecycleOwner?): Balloon {
        return Balloon.Builder(context)
            .setWidth(BalloonSizeSpec.WRAP)
            .setHeight(BalloonSizeSpec.WRAP)
            .setPaddingHorizontal(16)
            .setPaddingVertical(14)
            .setCornerRadius(16f)
            .setMarginRight(24)
            .setMarginTop(18)
            .setBalloonAnimation(BalloonAnimation.FADE)
            .setIsVisibleArrow(false)
            .setText("랭킹과 머리카락은 00:00시 정각에\n업데이트 됩니다.")
            .setTextGravity(Gravity.START)
            .setTextColorResource(R.color.hacker_white)
            .setTextTypeface(R.font.hacker_font_bold)
            .setTextSize(16f)
            .setBackgroundColor(context.colorOf(R.color.hacker_black))
            .setLifecycleOwner(lifecycle)
            .setAutoDismissDuration(1500L)
            .build()
    }
}
