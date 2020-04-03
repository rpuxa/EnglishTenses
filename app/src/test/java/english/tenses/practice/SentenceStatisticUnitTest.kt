package english.tenses.practice

import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test
import english.tenses.practice.model.logic.SentenceStatistic
import english.tenses.practice.model.db.dao.CorrectnessStatisticDao
import english.tenses.practice.model.db.dao.LearnedSentencesDao

class SentenceStatisticUnitTest {

    val learnedSentencesDao: LearnedSentencesDao = mockk {
        coEvery { getAll() } returns emptyList()

    }
    val c: CorrectnessStatisticDao = mockk()

    val sentenceStatistic =
        SentenceStatistic(
            learnedSentencesDao,
            c
        )

    @Test
    fun `check probabilities`() {
        val tenses = Array(12) { it }.toSet()
        val sizes = listOf(100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200)
        val tensesUsed = List(12) { HashSet<Int>() }

        val tensesCount = IntArray(12)

        repeat(100_000) {
            val (tenseCode, id) = sentenceStatistic.nextSentence(
                tenses,
                sizes
            )
            if (id in tensesUsed[tenseCode]) Assert.fail(it.toString())
            tensesUsed[tenseCode].add(id)
            if (tensesUsed[tenseCode].size == sizes[tenseCode]) {
                tensesUsed[tenseCode].clear()
            }
            tensesCount[tenseCode]++
        }

        println(tensesCount.joinToString(", "))
    }
}