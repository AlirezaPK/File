package ir.kodato.file.core.util

enum class Themes(val title: String) {
    LIGHT("Light Theme"),
    DARK("Dark Theme"),
    SYSTEM("System Default");

    companion object {
        fun getList(): List<String> {
            return entries.map { it.title }
        }
    }
}

enum class SortBy{
    NAME_ASC,
    NAME_DESC
}