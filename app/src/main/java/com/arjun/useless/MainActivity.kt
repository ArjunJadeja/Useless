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
    private var soundOne = R.integer.zero
    private var soundTwo = R.integer.zero
    private var soundThree = R.integer.zero
    private var soundFour = R.integer.zero
    private var soundFive = R.integer.zero
    private var soundSix = R.integer.zero
    private var soundSeven = R.integer.zero
    private var soundEight = R.integer.zero
    private var soundNine = R.integer.zero
    private var soundTen = R.integer.zero
    private var soundEleven = R.integer.zero
    private var soundTwelve = R.integer.zero

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

        soundOne = soundPool.load(this, R.raw.sound_one, 1)
        soundTwo = soundPool.load(this, R.raw.sound_two, 1)
        soundThree = soundPool.load(this, R.raw.sound_three, 1)
        soundFour = soundPool.load(this, R.raw.sound_four, 1)
        soundFive = soundPool.load(this, R.raw.sound_five, 1)
        soundSix = soundPool.load(this, R.raw.sound_six, 1)
        soundSeven = soundPool.load(this, R.raw.sound_seven, 1)
        soundEight = soundPool.load(this, R.raw.sound_eight, 1)
        soundNine = soundPool.load(this, R.raw.sound_nine, 1)
        soundTen = soundPool.load(this, R.raw.sound_ten, 1)
        soundEleven = soundPool.load(this, R.raw.sound_eleven, 1)
        soundTwelve = soundPool.load(this, R.raw.sound_twelve, 1)

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
            playSound()
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

    private fun playSound() {
        when ((0..11).random()) {
            0 -> soundPool.play(soundOne, 1f, 1f, 1, 0, 1f)
            1 -> soundPool.play(soundTwo, 1f, 1f, 1, 0, 1f)
            2 -> soundPool.play(soundThree, 1f, 1f, 1, 0, 1f)
            3 -> soundPool.play(soundFour, 1f, 1f, 1, 0, 1f)
            4 -> soundPool.play(soundFive, 1f, 1f, 1, 0, 1f)
            5 -> soundPool.play(soundSix, 1f, 1f, 1, 0, 1f)
            6 -> soundPool.play(soundSeven, 1f, 1f, 1, 0, 1f)
            7 -> soundPool.play(soundEight, 1f, 1f, 1, 0, 1f)
            8 -> soundPool.play(soundNine, 1f, 1f, 1, 0, 1f)
            9 -> soundPool.play(soundTen, 1f, 1f, 1, 0, 1f)
            10 -> soundPool.play(soundEleven, 1f, 1f, 1, 0, 1f)
            11 -> soundPool.play(soundTwelve, 1f, 1f, 1, 0, 1f)
        }
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