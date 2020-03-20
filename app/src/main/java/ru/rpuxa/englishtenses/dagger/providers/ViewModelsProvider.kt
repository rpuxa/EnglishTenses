package ru.rpuxa.englishtenses.dagger.providers

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.rpuxa.englishtenses.dagger.ViewModelKey
import ru.rpuxa.englishtenses.viewmodel.*

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
}