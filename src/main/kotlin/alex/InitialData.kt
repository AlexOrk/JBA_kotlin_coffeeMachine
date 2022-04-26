package alex

object InitialData {
    private var data = this::class.java.getResource("/data.yml")!!.readText()
    fun receiveData(name: String) = data.substringAfter("${name}: ").substringBefore("\n").toInt()
}