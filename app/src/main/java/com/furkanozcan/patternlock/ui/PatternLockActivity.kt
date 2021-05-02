package com.furkanozcan.patternlock.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.furkanozcan.patternlock.R
import com.furkanozcan.patternlock.databinding.ActivityPatternLockBinding
import com.furkanozcan.patternlock.ui.component.patternlockview.PatternViewState
import com.furkanozcan.patternlock.ui.component.patternlockview.PatternViewStageState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PatternLockActivity : AppCompatActivity() {

    private val viewModel by viewModels<PatternLockViewModel>()

    private lateinit var binding: ActivityPatternLockBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPatternLockBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.clearTextButton.setOnClickListener {
            viewModel.updateViewState(PatternViewState.Initial)
        }

        binding.stageButton.setOnClickListener {
            when (binding.patternLockView.stageState) {
                PatternViewStageState.FIRST -> {
                    viewModel.updateViewState(PatternViewState.Initial)

                    binding.patternLockView.stageState = PatternViewStageState.SECOND
                    binding.stageButton.text = getString(R.string.stage_button_confirm)
                    binding.tvSubTitle.isInvisible = true
                }
                PatternViewStageState.SECOND -> {
                    AlertDialog.Builder(this).apply {
                        setMessage(R.string.alert_dialog_confirm_message)
                        setPositiveButton(
                            R.string.alert_dialog_positive_button
                        ) { _, _ -> }
                    }.show()
                }
            }
        }

        binding.patternLockView.setOnChangeStateListener { state ->
            viewModel.updateViewState(state)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.viewState.collect { patternViewState ->
                when (patternViewState) {
                    is PatternViewState.Initial -> {
                        binding.patternLockView.reset()
                        binding.stageButton.isEnabled = false
                        binding.clearTextButton.isVisible = false
                        binding.tvMessage.run {
                            text = if (binding.patternLockView.stageState == PatternViewStageState.FIRST) {
                                getString(R.string.initial_message_first_stage)
                            } else {
                                getString(R.string.initial_message_second_stage)
                            }
                            setTextColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.message_text_default_color
                                )
                            )
                        }
                    }
                    is PatternViewState.Started -> {
                        binding.tvMessage.run {
                            text = getString(R.string.started_message)
                            setTextColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.message_text_default_color
                                )
                            )
                        }
                    }
                    is PatternViewState.Success -> {
                        binding.stageButton.isEnabled = true
                        binding.tvMessage.run {
                            text = if (binding.patternLockView.stageState == PatternViewStageState.FIRST) {
                                getString(R.string.success_message_first_stage)
                            } else {
                                getString(R.string.success_message_second_stage)
                            }
                            setTextColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.message_text_default_color
                                )
                            )
                        }
                        binding.clearTextButton.isVisible =
                            binding.patternLockView.stageState == PatternViewStageState.FIRST
                    }
                    is PatternViewState.Error -> {
                        binding.tvMessage.run {
                            text = if (binding.patternLockView.stageState == PatternViewStageState.FIRST) {
                                getString(R.string.error_message_first_stage)
                            } else {
                                getString(R.string.error_message_second_stage)
                            }
                            setTextColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.message_text_error_color
                                )
                            )
                        }
                        binding.clearTextButton.isVisible =
                            binding.patternLockView.stageState == PatternViewStageState.FIRST
                    }
                }
            }
        }
    }
}