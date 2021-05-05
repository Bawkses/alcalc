package com.github.bawkses.alcalc.util.string

private external class SprintfJS {
    fun vsprintf(
        format: String,
        arguments: Array<out Any?>
    ): String
}

@JsModule("sprintf-js")
@JsNonModule
private external val sprintfJS: SprintfJS

fun format(
    format: String,
    vararg args: Any?
): String = sprintfJS.vsprintf(
    format,
    args
)

fun String.format(
    vararg args: Any?
): String = format(
    format = this,
    args = args
)
