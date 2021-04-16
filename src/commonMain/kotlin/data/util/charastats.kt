package data.util

import data.entity.CharaStats
import kotlin.math.roundToInt

/** Calculates the percentage of am enemy character hitting this character. */
fun CharaStats.enemyHit(
    enemyAccuracy: Int = 100,
    enemyLuck: Int = 50,
    levelDifference: Int = 0,
    extraAccuracy: Int = 0,
    extraEvasion: Int = 0
): Float = 10 +
        ((enemyAccuracy.toFloat() / (enemyAccuracy + evasion + 2) * 100)) +
        ((luck - enemyLuck - levelDifference) / 1000f) +
        extraAccuracy - extraEvasion

/** @see enemyHit */
fun CharaStats.enemyHitAgainst(
    other: CharaStats,
    levelDifference: Int = 0,
    extraAccuracy: Int = 0,
    extraEvasion: Int = 0
) = enemyHit(
    enemyAccuracy = other.accuracy,
    enemyLuck = other.luck,
    levelDifference = levelDifference,
    extraAccuracy = extraAccuracy,
    extraEvasion = extraEvasion
)

/** Calculates this character's effective health. */
fun CharaStats.effectiveHealth(
    enemyHit: Float = enemyHit()
): Int = (health / (enemyHit / 100))
    .roundToInt()
