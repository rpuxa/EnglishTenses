package english.tenses.practice.dagger.providers

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import english.tenses.practice.dagger.ViewModelKey
import english.tenses.practice.viewmodel.*

@Module
abstract class ViewModelsProvider {

    @Binds
    @IntoMap
    @ViewModelKey(ExerciseViewModel::class)
    abstract fun load(v: ExerciseViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun main(v: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IrregularVerbsViewModel::class)
    abstract fun verbs(v: IrregularVerbsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExamViewModel::class)
    abstract fun exam(v: ExamViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrainingViewModel::class)
    abstract fun training(v: TrainingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AchievementViewModel::class)
    abstract fun achievement(v: AchievementViewModel): ViewModel

   @Binds
    @IntoMap
    @ViewModelKey(RateViewModel::class)
    abstract fun rate(v: RateViewModel): ViewModel


}