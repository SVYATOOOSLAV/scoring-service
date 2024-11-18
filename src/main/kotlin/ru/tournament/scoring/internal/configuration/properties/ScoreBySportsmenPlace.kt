@file:Suppress("MagicNumber")

package ru.tournament.scoring.internal.configuration.properties

fun getPlaceMap(): Map<Int, Int> {
    return mapOf(
        1 to 10,
        2 to 9,
        3 to 8,
        4 to 7,
        5 to 6,
        7 to 4,
        8 to 3,
        9 to 2,
        10 to 1
    )
}

fun getPlaceMapForOfficial(): Map<Int, Int> {
    return mapOf(
        1 to 5,
        2 to 4,
        3 to 3,
        4 to 2,
        5 to 1,
        7 to 1,
        8 to 1,
        9 to 1,
        10 to 1
    )
}
