fun main(args: Array<String>) {
   import java.io.File
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

fun main(args: Array<String>) {
    if (args.size != 3) {
        println("Usage: <filename> <sourceEncoding> <targetEncoding>")
        return
    }

    val filename = args[0]
    val sourceEncodingName = args[1] //
    val targetEncodingName = args[2] //

    // Проверка существования файла
    val file = File(filename)
    if (!file.exists() || !file.isFile) {
        println("File does not exist or is not a valid file.")
        return
    }

    // Проверка кодировок
    val availableEncodings = Charset.availableCharsets()
    val sourceEncoding = availableEncodings[sourceEncodingName]
    val targetEncoding = availableEncodings[targetEncodingName]

    if (sourceEncoding == null || targetEncoding == null) {
        println("Invalid encoding names. Available encodings: ${availableEncodings.keys}")
        return
    }

    // Чтение содержимого файла в исходной кодировке
    val content = file.readText(sourceEncoding)

    // Сохранение содержимого в целевой кодировке
    val targetFileName = "${file.nameWithoutExtension}_converted.${file.extension}"
    File(targetFileName).writeText(content, targetEncoding)

    println("File converted successfully to $targetFileName")
}
}
