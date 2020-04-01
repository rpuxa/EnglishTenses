package english.tenses.practice.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import english.tenses.practice.R
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity<MainActivity>()
        finish()
    }
}
