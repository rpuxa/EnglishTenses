package ru.rpuxa.englishtenses.model

import androidx.annotation.StringRes
import ru.rpuxa.englishtenses.R

enum class Theory(
    tense: Tense,
    block: TheoryBuilder.() -> Unit
) {

    PAST_SIMPLE(Tense.PAST_SIMPLE, {
        usage(R.string.past_simple_usage1) {
            text(R.string.past_simple_usage1_text1)
            example("Did you watch that film yesterday?")
            example("He left at the end of November.")
            example("When they were young, they hated meat.")
        }
        usage(R.string.past_simple_usage2) {
            text(R.string.past_simple_usage2_text1)
            example("He fell off his bike and his friends took him to a doctor.")
            example("As children, we played all kinds of games on the street.")
            example("She looked a bit upset.")
            text(R.string.past_simple_usage2_text2)
            example("I did a lot of travelling when I was younger.")
        }
        usage(R.string.past_simple_usage3) {
            text(R.string.past_simple_usage3_text1)
            example("Leonardo Da Vinci painted the Mona Lisa.")

        }
        usage(R.string.past_simple_usage4) {
            text(R.string.past_simple_usage4_text1)
            example("I turned off the light and got into bed")
        }
    }),

    PRESENT_SIMPLE(Tense.PRESENT_SIMPLE, {
        usage(R.string.present_simple_usage1) {
            text(R.string.present_simple_usage1_text1)
            example("Ten times ten makes one hundred. (10 x 10 = 100)")
            example("There is always a holiday on the last Monday in August in the UK.")
            example("Time passes very quickly when you get older.")
            text(R.string.present_simple_usage1_text2)
            example("I really love my job.")
            example("Mrs Clare doesn’t teach me but she teaches my sister.")
            example("Do you live in Glasgow? My cousin lives there too.")
            example("Spiders don’t frighten me.")
            example("Martha does what she wants. No one tells her what to do.")
        }
        usage(R.string.present_simple_usage2) {
            text(R.string.present_simple_usage2_text1)
            example("How do you get to work? Do you get the bus?")
            example("I read every night before I go to sleep.")
            example("We always have a holiday in the summer. We never work in August.")
            example("We usually fly to France when we go. Lorea doesn’t like the ferry. It makes her feel sick.")
        }
        usage(R.string.present_simple_usage3) {
            text(R.string.present_simple_usage3_text1)
            example("You take the train into the city centre and then you take a number five bus. You don’t get off at the museum. You get off at the stop after the museum.")
            example("So what you do is … you read the questions first and then you write down your answers in the box. You don’t write on the question paper.")
        }
        usage(R.string.present_simple_usage4) {
            text(R.string.present_simple_usage4_text1)
            example("Alex doesn’t ring back at midnight … she waits till the morning to ring, and they get annoyed with Liz when she goes on … they know she’s got plenty of money by their standards …")
            text(R.string.present_simple_usage4_text2)
            example("Mwaruwauri Benjani fouls Cahill. Habsi takes the free kick, Caicedo shoots and volleys. O’Brien blocks.")
        }
        usage(R.string.present_simple_usage5) {
            text(R.string.present_simple_usage5_text1)
            example("Do you think that meat is ok to eat? It doesn’t smell very good.")
            example("I don’t like the colour. I think I look terrible.")
        }
        usage(R.string.present_simple_usage6) {
            text(R.string.present_simple_usage6_text1)
            example("I will pay you back, I promise, when I get paid.")
            example("I agree with everything you say.")
            text(R.string.present_simple_usage6_text2)
            example("I attach the original signed copies for your records.")
            example("On behalf of the Society, and particularly those involved in medical work, I write to thank you for your kind gift of £20,000 …")
        }
        usage(R.string.present_simple_usage7) {
            text(R.string.present_simple_usage7_text1)
            example("The lesson starts at 9.30 tomorrow instead of 10.30.")
            example("Lunch is at 12.30. Don’t be late.")
            example("What time do you land?")
            example("They don’t start back to school until next Monday.")
            text(R.string.present_simple_usage7_text2)
            example("The lesson will start at 9.30 tomorrow instead of 10.30.")
        }
        usage(R.string.present_simple_usage8) {
            text(R.string.present_simple_usage8_text1)
            example("I’ll call you when I get there.")
            example("Don’t forget to ring before you go.")
            example("They hope to move in to the new house as soon as they get back from Australia next month.")
        }
        usage(R.string.present_simple_usage9) {
            text(R.string.present_simple_usage9_text1)
            example("Man rescues child from lake")
            example("Taiwanese envoys arrive in China")
        }
    }),

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

        usage(R.string.present_perfect_usage2) {
            text(R.string.present_perfect_usage2_text1)
            example("What’s this? What’s just happened?")
            example("The company employs around 400 staff and has recently opened an office in the UK.")
            example("Niki and John have just come back from a week in Spain.")
        }

        usage(R.string.present_perfect_usage3) {
            text(R.string.present_perfect_usage3_text1)
            example("She’s broken her arm in two places.")
            example("Why haven’t you dressed in something warmer?")
            example("A fire has broken out at a disused hotel on the seafront.")
            example("Your flowers haven’t arrived.")

        }
        usage(R.string.present_perfect_usage4) {
            text(R.string.present_perfect_usage4_text1)
            example("That house on the corner has been empty for three years.")
            example("That house on the corner has been empty since 2006.")

        }
        usage(R.string.present_perfect_usage5) {
            text(R.string.present_perfect_usage5_text1)
            example("How long have you worked there?")

        }
        usage(R.string.present_perfect_usage6) {
            text(R.string.present_perfect_usage6_text1)
            example("Don’t wash up that cup. I haven’t finished my coffee yet.")
            example("Haven’t you done your homework yet?")

        }
        usage(R.string.present_perfect_usage7) {
            text(R.string.present_perfect_usage7_text1)
            example("I’ve already booked my flight home.")
            example("A: Will you go and clean your teeth!\n B: I’ve already cleaned them.")

        }
        usage(R.string.present_perfect_usage8) {
            text(R.string.present_perfect_usage8_text1)
            example("She still hasn’t said sorry to me.")
            example("I feel really tired. I still haven’t recovered from the jet lag.")

        }
        usage(R.string.present_perfect_usage9) {
            text(R.string.present_perfect_usage9_text1)
            example("Charlton Heston has died aged 84, a spokesman for his family has said.")
            text(R.string.present_perfect_usage9_text2)
            example("Have you seen any Arthur Miller plays? I saw a fantastic production of ‘The Crucible’.")
        }
    })

    ;

    val tenseCode = tense.code
    val usages: List<TheoryItem>

    init {
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

