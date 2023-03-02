package com.teamzzong.hacker.shared

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.fragment.app.DialogFragment
import com.teamzzong.hacker.shared.databinding.DialogSingleAlertBinding

class SingleAlertDialog : DialogFragment() {
    private var _binding: DialogSingleAlertBinding? = null
    private val binding: DialogSingleAlertBinding
        get() = requireNotNull(_binding)
    private var dialogTitle = ""
    private var listener: OnConfirmClickListener? = null

    fun interface OnConfirmClickListener {
        fun onConfirm()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogSingleAlertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDialogBackground()
        initView()
    }

    private fun initView() {
        binding.btnConfirm.setOnClickListener {
            listener?.onConfirm()
        }
        binding.tvTitle.text = dialogTitle
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setDialogBackground() {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_dialog)
        dialog?.window?.setLayout(306.dp, LayoutParams.WRAP_CONTENT)
    }

    fun setOnConfirmClickListener(block: OnConfirmClickListener) {
        listener = block

    }

    fun setTitle(title: String) {
        dialogTitle = title
    }

    private val Int.dp: Int
        get() = (this * resources.displayMetrics.density + 0.5f).toInt()
}
