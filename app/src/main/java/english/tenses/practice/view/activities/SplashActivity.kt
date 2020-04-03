package english.tenses.practice.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import english.tenses.practice.viewModel
import english.tenses.practice.viewmodel.LoadViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import org.jetbrains.anko.contentView
import org.jetbrains.anko.startActivity
import androidx.lifecycle.observe
import english.tenses.practice.R
import english.tenses.practice.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private val viewModel: LoadViewModel by viewModel()
    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.load()

        viewModel.progress.observe(this) {
            if (it > 0.9999f) {
                if (contentView == null) {
                    start()
                } else {
                    binding.progressBar.animateProgress(1f)
                    Handler().postDelayed({
                        start()
                    }, 600)
                }
            } else {
                if (contentView == null) {
                    setContentView(binding.root)
                    binding.progressBar.animationDuration = 500L
                    binding.progressBar.light = true
                }
                binding.progressBar.animateProgress(it)
            }
        }
    }

    private fun start() {
        startActivity<MainActivity>()
        overridePendingTransition(R.anim.no_anim, R.anim.no_anim)
        finish()
    }
}
