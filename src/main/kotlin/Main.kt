fun main(args: Array<String>) {
    var person :Person = Person("")
    do {
        var purchase: Command = readCommand()
        if (purchase.isValid()) {
            when (purchase) {
                is AddContactWithPhone -> if (true){
                    println(purchase.contact.toString())
                    person = purchase.contact}
                is AddContactWithEmail -> if (true){
                    println(purchase.contact.toString())
                    person = purchase.contact
                }
                is HelpCommand -> purchase.help()
                is Exit -> break
                is Show -> if (person.name!=""){
                    println(person.toString())
                } else {
                    println("Not initialized")
                }
                else -> println("else")

            }
        } else {
            when (purchase) {
                is HelpCommand -> purchase.help()
                else -> println("Wrong entering")

            }
        }
    } while (Exit().isValid())

}

fun readCommand(): Command {
    var command: Command
    println("\nEnter command")
    var mainLine = readlnOrNull().toString()

    var splitLine = mainLine.split(" ")
    if (splitLine.get(0) == "add" && splitLine.get(2) == "phone") {
        command = AddContactWithPhone(mainLine)
        return command
    }
    if (splitLine.get(0) == "add" && splitLine.get(2) == "email") {
        command = AddContactWithEmail(mainLine)
        return command
    }
    if (splitLine.get(0) == "help") {
        command = HelpCommand()
        return command
    }
    if (splitLine.get(0) == "exit") {
        command = Exit()
        return command

    }
    if (splitLine.get(0) == "show"){
        command = Show()
        return command
    }

    command = HelpCommand()
    return command
}

sealed interface Command {
    fun isValid(): Boolean

}

class AddContactWithEmail(val line: String) : Command {

    var contact: Person = Person("")
    override fun isValid(): Boolean {
        val myLine = line.split(" ")
        val regexName = """([a-zA-Z])+""".toRegex()
        if (regexName.matches(myLine.get(1))) {
            if (isEmail(line)) {
                return true
            }

        }
        return false
    }

    fun isEmail(line: String): Boolean {
        val myLine = line.split(" ")
        val regexEmail = """([a-zA-Z0-9])+\@([a-z])+\.([a-z])+""".toRegex()
        if (regexEmail.matches(myLine.get(3)) && myLine.get(2) == "email") {
            contact = Person(myLine.get(1))
            contact.email = myLine.get(3)
            return true
        }
        return false
    }


}

class AddContactWithPhone(val line: String) : Command {

    var contact: Person = Person("")
    override fun isValid(): Boolean {
        val myLine = line.split(" ")
        val regexName = """([a-zA-Z])+""".toRegex()
        if (regexName.matches(myLine.get(1))) {
            if (isPhone(line)) {
                return true
            }

        }
        return false
    }


    fun isPhone(line: String): Boolean {
        val myLine = line.split(" ")
        val regex = """([+, 0-9])+""".toRegex()
        if (regex.matches(myLine.get(3)) && myLine.get(2) == "phone") {
            contact = Person(myLine.get(1))
            contact.phone = myLine.get(3)
            return true
        }
        return false
    }

}

class HelpCommand : Command {
    override fun isValid(): Boolean {
        return true
    }

    fun help() {
        println(
            "Command 'add' : Adding new contact with name and phone number or email adress.\n" +
                    "EXAMPLE: add John phone +7895646 or add John email myEmail@mail.ru \n" +
                    "Command 'help' : Showing available commands and their purpose\n" +
                    "Command 'show' : Show last added contact\n" +
                    "Command 'exit' :Exiting from app\n"
        )
    }

}

class Exit : Command {

    override fun isValid(): Boolean {
        return true
    }

}

data class Person(val name: String) {
    var phone: String = ""
    var email: String = ""

    override fun toString(): String {
        if (phone != "") {
            return "$name $phone"
        }
        if (email != "") {
            return "$name $email"
        }
        return super.toString()
    }
}

class Show: Command{

    override fun isValid(): Boolean {
        return true

    }

}