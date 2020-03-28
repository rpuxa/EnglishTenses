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
            example("I did a lot of travelling when I was younger.(or I used to do a lot of travelling when I was younger.)")
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


    FUTURE_SIMPLE(Tense.FUTURE_SIMPLE, {
        usage(R.string.future_simple_usage1) {
            text(R.string.future_simple_usage1_text1)
            example("There will be strong winds tomorrow in the south of the country.")
            example("The year 2025 will be the four-hundredth anniversary of the founding of the university.")
            example("We shall need an extra bedroom when the new baby arrives.")
        }
        usage(R.string.future_simple_usage2) {
            text(R.string.future_simple_usage2_text1)
            example("A: Which size do you want? Medium or large?\nB: I’ll have large.")
            example("Wait. I’ll open the door for you.")
            example("I shall contact you again when I have further information.")
        }
        usage(R.string.future_simple_usage3) {
            text(R.string.future_simple_usage3_text1)
            example("We shall never forget the holiday we had in Vietnam.")
            text(R.string.future_simple_usage3_text2)
            example("It’s getting late. Shall we go home?")
            example("Shall I invite Louisa and Jill to the party?")
        }
    }),

    PAST_CONTINUOUS(Tense.PAST_CONTINUOUS, {
        usage(R.string.past_continuous_usage1) {
            text(R.string.past_continuous_usage1_text1)
            example("A: Where was Donna last night?\nB: I’m not sure. I think she was visiting her family.")
            example("I remember that night. You were wearing that red dress.")
        }
        usage(R.string.past_continuous_usage2) {
            text(R.string.past_continuous_usage2_text1)
            example("Lisa was cycling to school when she saw the accident.")
            example("What were you thinking about when you won the race?")
        }
        usage(R.string.past_continuous_usage3) {
            text(R.string.past_continuous_usage3_text1)
            example("A: I can’t believe you met Fran and Dave in Portugal.\nB: It was funny. They were staying in the hotel next to ours.")
            example("I didn’t make the meeting last week; I was travelling to Rome.")
        }
        usage(R.string.past_continuous_usage4) {
            text(R.string.past_continuous_usage4_text1)
            example("She was feeding her neighbours’ cat every morning while they were on holiday. Then one morning, it was gone.")
            example("The neighbours were making so much noise, night after night. We had to complain eventually.")
            text(R.string.past_continuous_usage4_text2)
            example("We were always spending so much time in traffic. That’s the main reason why we decided to move to the country and work from home.")
            example("My boss was constantly phoning me in my last job. I hated it.")
            example("She was forever losing her keys.")
        }
        usage(R.string.past_continuous_usage5) {
            text(R.string.past_continuous_usage5_text1)
            example("We were cooking all morning because we had 15 people coming for lunch.")
            example("Lots of us were working at the office on Saturday because we had to finish the project by Monday.")
        }
    }),

    PRESENT_CONTINUOUS(Tense.PRESENT_CONTINUOUS, {
        usage(R.string.present_continuous_usage1) {
            text(R.string.present_continuous_usage1_text1)
            example("A: What time’s dinner?\nB: I’m cooking now so it’ll be ready in about half an hour.")
            example("She’s pressing the button but nothing is happening.")
        }
        usage(R.string.present_continuous_usage2) {
            text(R.string.present_continuous_usage2_text1)
            example("Her mother’s living with her at the moment. She’s just come out of hospital.")
            example("Who’s looking after the children while you’re here?")
        }
        usage(R.string.present_continuous_usage3) {
            text(R.string.present_continuous_usage3_text1)
            example("I’m not drinking much coffee these days. I’m trying to cut down.")
            example("She’s working a lot in London at the moment.")
        }
        usage(R.string.present_continuous_usage4) {
            text(R.string.present_continuous_usage4_text1)
            example("They’re building a new stand at the football ground.")
            example("Maria, 37, is getting better and doctors are optimistic she will make a full recovery.")
            example("Recent evidence suggests that the economic situation is improving.")
        }
        usage(R.string.present_continuous_usage5) {
            text(R.string.present_continuous_usage5_text1)
            example("My wife, she’s always throwing things out. I like to keep everything.")
            example("I’m constantly spilling things.")
        }
        usage(R.string.present_continuous_usage6) {
            text(R.string.present_continuous_usage6_text1)
            example("We’re moving to Cambridge in July.")
            example("Sarah isn’t taking Rory to football training later. She hasn’t got the car tonight.")
            example("Aren’t you playing tennis on Saturday?")
        }
    }),

    FUTURE_CONTINUOUS(Tense.FUTURE_CONTINUOUS, {
        usage(R.string.future_continuous_usage1) {
            text(R.string.future_continuous_usage1_text1)
            example("This time next week, I’ll be taking photographs with my new camera.")
            example("I’ll post your letter for you. I’ll be passing a post-box.")
            example("Next week they will be flying to Australia from Saudi Arabia.")
            example("She will not be working on Tuesday.")
            example("Unfortunately we won’t be attending the wedding.")
        }
    }),

    PAST_PERFECT(Tense.PAST_PERFECT, {
        usage(R.string.past_perfect_usage1) {
            text(R.string.past_perfect_usage1_text1)
            example("I’d seen all of Elvis Presley’s movies by the time I was 20!")
        }
        usage(R.string.past_perfect_usage2) {
            text(R.string.past_perfect_usage2_text1)
            example("“Mr Hammond drove through a red light.”")
            example("The policeman said Mr Hammond had driven through a red light.")
            example("No one told me that the shop had closed.")
            example("I phoned Katie and she said the kids had had a day off school so she’d taken them ice skating.")
            text(R.string.past_perfect_usage2_text2)
            example("My Dad was really angry because he heard I hadn’t come home until 3 am!")
            example("I saw she’d bought the DVD so I asked if I could borrow it.")
            example("The doctor felt my mother had got worse since last week.")
        }
        usage(R.string.past_perfect_usage3) {
            text(R.string.past_perfect_usage3_text1)
            example("A: Are you going anywhere today?\nB: I had planned to go to the beach but look at the rain!")
            example("I’m very happy working as an engineer but I had wanted to be an actor when I was younger.")
        }
        usage(R.string.past_perfect_usage4) {
            text(R.string.past_perfect_usage4_text1)
            example("I would have helped to paint the house if you’d asked me.")
            example("Sarah couldn’t come with us to the cinema. She would have loved it if she had been there")
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
    }),


    FUTURE_PERFECT(Tense.FUTURE_PERFECT, {
        usage(R.string.future_perfect_usage1) {
            text(R.string.future_perfect_usage1_text1)
            example("Do you think she’ll have seen the doctor by four o’clock?")
            example("Next month my parents will have been together for thirty years.")
            example("At the end of this month, they will have been in their house for one year.")
            example("Next month I will have worked for the company for six years.")
            example("I think they’ll have got there by six o’clock.")
            example("Won’t she have retired by the end of the year?")
        }
    }),

    PAST_PERFECT_CONTINUOUS(Tense.PAST_PERFECT_CONTINUOUS, {
        usage(R.string.past_perfect_continuous_usage1) {
            text(R.string.past_perfect_continuous_usage1_text1)
            example("It was so difficult to get up last Monday for school. I had been working on my essays the night before and I was very tired.")
            example("A: Why did you decide to go travelling for a year?\nB: Well, I’d been reading an amazing book about a woman who rode a horse around South America. I was just halfway through the book when I decided I had to go travelling and that was it. I just took a year out of work and went.")
            text(R.string.past_perfect_continuous_usage1_text2)
            example("It had been raining and the ground was still wet.")
        }
    }),

    PRESENT_PERFECT_CONTINUOUS(Tense.PRESENT_PERFECT_CONTINUOUS, {
        usage(R.string.present_perfect_continuous_usage1) {
            text(R.string.present_perfect_continuous_usage1_text1)
            example("I’ve just been cleaning the car.")
            example("It’s been snowing.")
            example("What have you been buying?")
        }
        usage(R.string.present_perfect_continuous_usage2) {
            text(R.string.present_perfect_continuous_usage2_text1)
            example("I’ve been reading your book – it’s great.")
            example("He’s been living in the village since 1995.")
            example("She has been writing her autobiography since 1987.")

        }
        usage(R.string.present_perfect_continuous_usage3) {
            text(R.string.present_perfect_continuous_usage3_text1)
            example("I’ve been going to Spain on holiday every year since 1987.")
            example("I haven’t been eating much lunch lately. I’ve been going to the gym at lunchtimes.")
            example("She’s been playing tennis on and off for three years.")

        }
        usage(R.string.present_perfect_continuous_usage4) {
            text(R.string.present_perfect_continuous_usage4_text1)
            example("A: How long have you been waiting for me?\nB: About ten minutes. Not too long.")
        }
    }),

    FUTURE_PERFECT_CONTINUOUS(Tense.FUTURE_PERFECT_CONTINUOUS, {
        usage(R.string.future_perfect_continuous_usage1) {
            text(R.string.future_perfect_continuous_usage1_text1)
            example("In September the head teacher will have been teaching at the school for 20 years.")
            example("In September, she will have been living in France for a year.")
            example("I will have been studying English for three years by the end of this course.")
            example("We’re late. I think they’ll have been waiting for us. We’d better go.")
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


sealed class TheoryItem

class Text(@StringRes val text: Int) : TheoryItem()
class Example(val text: String) : TheoryItem()
class Usage(
    @StringRes
    val name: Int,
    val number: Int
) : TheoryItem()

private class TheoryBuilder {
    private var n = 0
    private val items = ArrayList<TheoryItem>()

    fun usage(
        @StringRes
        name: Int,
        block: UsageBuilder.() -> Unit
    ) {
        val builder = UsageBuilder()
        builder.block()
        val (usage, items) = builder.build(name, ++n)
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