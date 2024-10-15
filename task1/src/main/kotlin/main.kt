import java.io.File
import java.util.*

/*
 * Объекты этого класса создаются из строки, но хранят внутри помимо строки
 * ещё и целочисленное значение соответствующего адреса. Например, для адреса
 * 127.0.0.1 должно храниться число 1 + 0 * 2^8 + 0 * 2^16 + 127 * 2^24 = 2130706433.
 */

data class IPv4Addr(val str: String) : Comparable<IPv4Addr> {
    internal val intValue: UInt = ipstr2int()

    private fun ipstr2int(): UInt {
        return str.split(".").map { it.toUInt() }
            .let { (b1, b2, b3, b4) -> (b1 shl 24) or (b2 shl 16) or (b3 shl 8) or b4 }
    }

    // Благодаря этому методу мы можем сравнивать два значения IPv4Addr
    override fun compareTo(other: IPv4Addr): Int {
        return intValue.compareTo(other.intValue)
    }

    override fun toString(): String {
        return str
    }
}

data class IPRange(val ipFrom: IPv4Addr, val ipTo: IPv4Addr) {
    override fun toString(): String {
        return "$ipFrom,$ipTo"
    }
}

data class IPLookupArgs(val ipsFile: String, val iprsFiles: List<String>)

// Необходимо заменить на более подходящий тип (коллекцию), позволяющий
// эффективно искать диапазон по заданному IP-адресу
typealias IPRangesDatabase = SortedSet<IPRange>

fun parseArgs(args: Array<String>): IPLookupArgs? {
    if (args.isEmpty()) return null
    val ipsFile = args[0]
    val iprsFiles = args.drop(1)
    return IPLookupArgs(ipsFile, iprsFiles) //
}

fun loadQuery(filename: String): List<IPv4Addr> {
    return File(filename).readLines().map { IPv4Addr(it.trim()) }
}

fun loadRanges(filenames: List<String>): IPRangesDatabase {
    val ranges = sortedSetOf<IPRange>()
    for (filename in filenames) {
        File(filename).readLines().forEach { line ->
            val parts = line.split(",").map { it.trim() }
            if (parts.size == 2) {
                val ipFrom = IPv4Addr(parts[0])
                val ipTo = IPv4Addr(parts[1])
                ranges.add(IPRange(ipFrom, ipTo))
            }
        }
    }
    return ranges
}

fun findRange(
    ranges: IPRangesDatabase,
    query: IPv4Addr,
): IPRange? {
    return ranges.find { query >= it.ipFrom && query <= it.ipTo }
}


fun main(args: Array<String>) {
    val ipLookupArgs = parseArgs(args) ?: return
    val queries = loadQuery(ipLookupArgs.ipsFile)
    val ranges = loadRanges(ipLookupArgs.iprsFiles)
    val outputFileName = "${ipLookupArgs.ipsFile.substringBeforeLast('.')}.out"
    File(outputFileName).bufferedWriter().use { writer ->
        queries.forEach { ip ->
            val range = findRange(ranges, ip)
            val message = if (range != null) {
                "$ip: YES (${range.ipFrom} - ${range.ipTo})"
            } else {
                "$ip: NO"
            }
            writer.write(message)
            writer.newLine()
        }
    }
}
