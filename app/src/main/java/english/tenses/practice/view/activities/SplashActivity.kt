package english.tenses.practice.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import english.tenses.practice.R
import english.tenses.practice.model.RemoteSentenceLoader
import english.tenses.practice.view.App
import english.tenses.practice.viewModel
import english.tenses.practice.viewmodel.LoadViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.jetbrains.anko.contentView
import org.jetbrains.anko.startActivity
import javax.inject.Inject
import androidx.lifecycle.observe
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
                startActivity<MainActivity>()
                finish()
            } else {
                if (contentView == null) {
                    setContentView(binding.root)
                    binding.progressBar.animationDuration = 200L
                    binding.progressBar.light = true
                }
                binding.progressBar.animateProgress(it)
            }
        }
    }
}
