package ir.kodato.file.core.util

fun getExtension(fileName: String): String? {
    val regex = Regex("""\.docx\.enc|\.pdf\.enc""")
    val matchResult = regex.find(fileName)
    return matchResult?.value
}