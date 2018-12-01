package github.com.worker8.archplayground.common

import java.util.regex.Pattern

fun CharSequence.isValidEmail(): Boolean {
    return if (isEmpty()) {
        false
    } else {
        Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+").matcher(this).matches()
    }
}
