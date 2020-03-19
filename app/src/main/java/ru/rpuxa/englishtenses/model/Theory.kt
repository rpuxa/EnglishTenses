package ru.rpuxa.englishtenses.model

import androidx.annotation.StringRes
import ru.rpuxa.englishtenses.R

enum class Theory(
    tense: Tense,
    block: TheoryBuilder.() -> Unit
) {
    PRESENT_PERFECT(Tense.PRESENT_PERFECT, {
        usage(R.string.present_perfect_usage1) {
            text(R.string.present_perfect_usage1_text1)
            example("And I’ve seen ‘Buddy’ and I’ve seen ‘Starlight Express’ in London. And I want to see ‘Phantom of the Opera’ next.")
            example("We’re going to Wagamama’s for dinner tonight. I’ve been there a couple of times before.")
            text(R.string.present_perfect_usage1_text2)
            example("We haven’t met before, have we?")
            example("They’ve sold 110 so far.")
            text(R.string.present_perfect_usage1_text3)
            example("It was the worst performance we have ever seen.")
            example("Have you ever tried to write your name and address with your left hand?")
            example("She’s never said sorry for what she did.")
            text(R.string.present_perfect_usage1_text4)
            example("I felt the happiest I have ever felt. My first Olympic final; the bronze medal; European record of 9.97 seconds.")
            example("The dome of the Blue Mosque at Isfahan is the most beautiful building I have ever seen.")
            example("It was the best decision I have ever made in my life.")
            example("It’s the worst sports programme I have ever seen and the first I have ever turned off.")
            text(R.string.present_perfect_usage1_text5)
            example("That’s the first time I’ve seen you get angry.")
        }
    })

    ;

    val tenseCode: Int
    val usages: List<TheoryItem>

    init {
        tenseCode = tense.code
        val builder = TheoryBuilder()
        builder.block()
        usages = builder.build()
    }

    companion object {
        fun byTense(tenseCode: Int): Theory = values().first { it.tenseCode == tenseCode }
    }
}


sealed class TheoryItem()

class Text(@StringRes val text: Int) : TheoryItem()
class Example(val text: String) : TheoryItem()
class Usage(
    @StringRes
    val name: Int,
    val number: Int
) : TheoryItem()

private class TheoryBuilder {
    private val items = ArrayList<TheoryItem>()

    fun usage(
        @StringRes
        name: Int,
        block: UsageBuilder.() -> Unit
    ) {
        val builder = UsageBuilder()
        builder.block()
        val (usage, items) = builder.build(name, items.size + 1)
        this.items += usage
        this.items += items
    }

    fun build(): List<TheoryItem> = items
}

private class UsageBuilder {
    private val list = ArrayList<TheoryItem>()

    fun text(@StringRes text: Int) {
        list += Text(text)
    }

    fun example(text: String) {
        list += Example(text)
    }

    fun build(@StringRes name: Int, number: Int) = Usage(
        name,
        number
    ) to list
}

private class UsageItemsBuilder {

}

