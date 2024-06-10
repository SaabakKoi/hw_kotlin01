fun main(args: Array<String>) {

    do {
        println("Choose command")
        val command = readlnOrNull()
        val splitCommand = command.toString().split(" ")
        if (splitCommand.get(0) == "add") {
            println(addContact(command))
        }
        if (splitCommand.get(0) == "help") {
            println(
                "Command 'add' : Adding new contact with name and phone number or email adress.\n" +
                        "EXAMPLE: add John phone +7895646 or add John email myEmail@mail.ru \n" +
                        "Command 'help' : Showing available commands and their purpose\n" +
                        "Command 'exit' :Exiting from app\n"
            )

        }

    } while (command != "exit")


}

fun addContact(line: String?): String {
    val myLine = line.toString().split(" ")
    val regexName = """([a-zA-Z])+""".toRegex()
    val regex = """([+, 0-9])+""".toRegex()
    val regexEmail = """([a-zA-Z0-9])+\@([a-z])+\.([a-z])+""".toRegex()
    if (regexName.matches(myLine.get(1))) {
        if (regex.matches(myLine.get(3))&&myLine.get(2)=="phone") {
            val name = myLine.get(1)
            val phone = myLine.get(3)
            return "$name $phone"
        }
        if (regexEmail.matches(myLine.get(3))&&myLine.get(2)=="email") {
            val name = myLine.get(1)
            val email = myLine.get(3)
            return "$name $email"
        }
    }
    return "wrong entering"
}