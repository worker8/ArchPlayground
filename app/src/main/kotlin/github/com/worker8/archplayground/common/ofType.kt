package github.com.worker8.archplayground.common

inline fun <reified T> Any.ofType(block: (T) -> Unit) {
    if (this is T) {
        block(this as T)
    }
}
