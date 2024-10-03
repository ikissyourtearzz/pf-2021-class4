/*
 * Объекты этого класса создаются из строки, но хранят внутри помимо строки
 * ещё и целочисленное значение соответствующего адреса. Например, для адреса
 * 127.0.0.1 должно храниться число 1 + 0 * 2^8 + 0 * 2^16 + 127 * 2^24 = 2130706433.
 */
data class IPv4Addr(val str: String) : Comparable<IPv4Addr> {
    internal val intValue = ipstr2int()

    private fun ipstr2int(): UInt {
        TODO("Not yet implemented")
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
typealias IPRangesDatabase = Any

fun parseArgs(args: Array<String>): IPLookupArgs? {
    TODO()
}

fun loadQuery(filename: String): List<IPv4Addr> {
    TODO("Not yet implemented")
}

fun loadRanges(filenames: List<String>): IPRangesDatabase {
    TODO("Not yet implemented")
}

fun findRange(
    ranges: IPRangesDatabase,
    query: IPv4Addr,
): IPRange? {
    TODO("Not yet implemented")
}

fun main(args: Array<String>) {
    val ipLookupArgs = parseArgs(args) ?: return
    val queries = loadQuery(ipLookupArgs.ipsFile)
    val ranges = loadRanges(ipLookupArgs.iprsFiles)
    queries.forEach { ip ->
        print("$ip: ")
        val range = findRange(ranges, ip)
        TODO()
    }
}
