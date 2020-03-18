package ru.rpuxa.englishtenses.model

enum class Person(val code: Int) {
    UNKNOWN(0),
    FIRST(1), // me or you
    ME(2),
    YOU(3),
    IT(4)
    ;

    companion object {
        operator fun get(code: Int) = Person.values().first { it.code == code }
    }
}