fun main(args: Array<String>) {
    var person :Person = Person("")
    var setOfPersons :MutableSet<Person> = mutableSetOf()
    do {
        var purchase: Command = readCommand()
        if (purchase.isValid()) {
            when (purchase) {
                is AddContactWithPhone -> if (true){
                    println(purchase.contact.toString())
                    person = purchase.contact
                    setOfPersons.add(person)
                }

                is AddContactWithEmail -> if (true){
                    println(purchase.contact.toString())
                    person = purchase.contact
                    setOfPersons.add(person)
                }
                is AddPhone -> purchase.check(setOfPersons)
                is AddEmail -> purchase.check(setOfPersons)
                is Find -> purchase.findContact(setOfPersons)
                is HelpCommand -> purchase.help()
                is Exit -> break
                is Show -> if (person.name!=""){
//                    println(setOfPersons.toString())
                    purchase.getContact(setOfPersons)
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
    if(splitLine.get(0) == "addphone"){
        command = AddPhone()
        return command
        }
    if (splitLine.get(0) == "addemail") {
        command = AddEmail()
        return command
    }
    if(splitLine.get(0) == "find"){
        command = Find()
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
            contact.listOfEmails.add(myLine.get(3))
            return true
        }
        return false
    }


}

class Find :Command{
    override fun isValid(): Boolean {
        return true
    }

    fun findContact(setOfPersons: MutableSet<Person>){
        println("Please enter phone number or email adress")
        var line: String = readlnOrNull().toString()
        for (person: Person in setOfPersons){
            if (person.search(line))
                println(person)
        }
    }

}

class AddPhone : Command{
    fun addPhone (phone: String, contact: Person){
        contact.listOfPhones.add(phone)

    }

     fun check(setOfPersons: MutableSet<Person>): Boolean {

        if (setOfPersons.isEmpty())
            return false
        else
            println("Enter name")
         var name = readlnOrNull().toString()
         println("Enter phone number")
         var line: String = readlnOrNull().toString()
            for (person : Person in setOfPersons)
                if (person.name == name)
                    addPhone(line, person)

        return true
    }

    override fun isValid(): Boolean {
        return true
    }
}

class AddEmail: Command{
    fun addEmail (email: String, contact: Person ){
        contact.listOfEmails.add(email)
    }

    fun check(setOfPersons: MutableSet<Person>) : Boolean{
        if (setOfPersons.isEmpty())
            return false
        else
            println("Enter name")
        var name = readlnOrNull().toString()
            println("Enter email address")
        var line: String = readlnOrNull().toString()
            for (person : Person in setOfPersons)
                if (person.name == name)

                    addEmail(line, person)
        return true
    }

    override fun isValid(): Boolean {
        return true
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
            contact.listOfPhones.add(myLine.get(3))
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
                    "Command 'addphone' adding phone to contact which was entered \n" +
                    "Command 'addemail' adding email address to contact which was entered\n" +
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
    var listOfPhones = mutableListOf<String>()
    var listOfEmails = mutableListOf<String>()

    override fun toString(): String {

            return "$name $listOfPhones $listOfEmails"
        return super.toString()
    }

    fun search(line : String): Boolean{
        for (num in listOfPhones){
            if (line == num)
                return true
        }
        for (mail in listOfEmails){
            if (line == mail)
                return true
        }
            return false
    }
}

class Show: Command{

    fun getContact(setOfPersons: MutableSet<Person>){
        println("Enter name")
        var name :String = readlnOrNull().toString()
        for (person:Person in setOfPersons){
            if (person.name == name)
                println(person)
        }

    }
    override fun isValid(): Boolean {
        return true

    }

}