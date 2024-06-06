fun main(args: Array<String>) {

    do {
        println("Choose command")
        val command = readlnOrNull()
        if(command=="add"){
            println("Enter name:")
            val name = readlnOrNull()
            println("Enter phone number or Email")
            val phoneOrEmail = readlnOrNull()
            println(addPerson(name.toString(), phoneOrEmail.toString()))

        }
        if (command=="help"){
            println("Command 'add' : Adding new contact with name and phone number or email adress.\n" +
                    "Command 'help' : Showing available commands and their purpose\n" +
                    "Command 'exit' :Exiting from app\n")

        }

    } while (command!="exit")


}

fun addPerson(name:String, phoneOrEmail:String): String {

    val regexName = """([a-zA-Z])+""".toRegex()
    val regex = """([+, 0-9])+""".toRegex()
    val regexEmail = """([a-zA-Z0-9])+\@([a-z])+\.([a-z])+""".toRegex()
    if (regexName.matches(name)&&regex.matches(phoneOrEmail))
    return "$name $phoneOrEmail"
    if (regexName.matches(name)&&regexEmail.matches(phoneOrEmail))
        return "$name $phoneOrEmail"

    return "wrong entering"
}