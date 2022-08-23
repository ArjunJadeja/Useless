package com.arjun.useless

import android.media.SoundPool
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import com.arjun.useless.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: GameViewModel by viewModels()

    private val soundPool = SoundPool.Builder().setMaxStreams(13).build()
    private var clickSound = R.integer.zero
    private var finishSound = R.integer.zero

    private var clickCount = 0
    private var topScore = 0

    private var cardWidth = R.integer.zero
    private var cardHeight = R.integer.zero
    private var buttonWidth = R.integer.zero
    private var buttonHeight = R.integer.zero

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        clickSound = soundPool.load(this, R.raw.click_sound, 1)
        finishSound = soundPool.load(this, R.raw.finish_sound, 1)

        binding.gameViewCard.doOnLayout {
            cardWidth = it.width
            cardHeight = it.height
        }
        binding.clickButton.doOnLayout {
            buttonWidth = it.width
            buttonHeight = it.height
        }

        viewModel.clickCount.observe(this) {
            binding.clickCount.text = "Click Count : $it"
        }

        viewModel.getTopScore.observe(this) {
            binding.topScore.text = "Top Score : $it"
            topScore = it
        }

        binding.clickButton.setOnClickListener {
            playClickSound()
            clickCount++
            viewModel.setCount(clickCount)
            if (clickCount > topScore) {
                viewModel.setTopScore(clickCount)
            }
            setMargins(
                Random.nextInt(16, cardWidth - buttonWidth - 16),
                Random.nextInt(16, cardHeight - buttonHeight - 16)
            )
        }

        binding.gameStatusButton.setOnClickListener {
            if (binding.gameStatusButton.text == "Start") {
                startGame()
                binding.gameStatusButton.text = "Finish"
                binding.gameStatusButton.setStrokeColorResource(R.color.red)
                binding.gameStatusButton.setTextColor(resources.getColor(R.color.red))
            } else if (binding.gameStatusButton.text == "Finish") {
                binding.gameStatusButton.text = "Start"
                binding.gameStatusButton.setStrokeColorResource(R.color.green)
                binding.gameStatusButton.setTextColor(resources.getColor(R.color.green))
                playFinishSound()
                showResult()
            }
        }

        binding.thankYouButton.setOnClickListener {
            clickCount = 0
            viewModel.setCount(0)
            hideResult()
        }

        binding.exitAppButton.setOnClickListener {
            finishAffinity()
        }
    }

    private fun setMargins(left: Int, top: Int) {
        val param = binding.clickButton.layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(left, top, 0, 0)
        binding.clickButton.layoutParams = param
    }

    private fun startGame() {
        setMargins(
            (cardWidth / 2),
            (cardHeight / 2)
        )
        binding.clickButton.visibility = View.VISIBLE
    }

    private fun playClickSound() {
        soundPool.play(clickSound, 1f, 1f, 1, 0, 1f)
    }

    private fun playFinishSound() {
        soundPool.play(finishSound, 1f, 1f, 1, 0, 1f)
    }

    private fun showResult() {
        binding.resultText.text = "Congratulations!\n$clickCount Clicks"
        binding.gameStatusButton.visibility = View.GONE
        binding.gameViewCard.visibility = View.GONE
        binding.topScoreCard.visibility = View.GONE
        binding.ClickCountCard.visibility = View.GONE
        binding.clickButton.visibility = View.GONE
        binding.resultCard.visibility = View.VISIBLE
    }

    private fun hideResult() {
        binding.resultCard.visibility = View.GONE
        binding.gameStatusButton.visibility = View.VISIBLE
        binding.gameViewCard.visibility = View.VISIBLE
        binding.topScoreCard.visibility = View.VISIBLE
        binding.ClickCountCard.visibility = View.VISIBLE
    }
}