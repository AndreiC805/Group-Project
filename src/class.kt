import java.util.Scanner

// Represents a destination and its fares
class Destination(val name: String, val singleFare: Double, val returnFare: Double) {
    var totalTakings: Double = 0.0
}

// Represents a ticket
class Ticket(
    val origin: String,
    val destination: String,
    val ticketType: String, // "Single" or "Return"
    val price: Double
) {
    fun printTicket() {
        println("***")
        println("$origin to $destination")
        println("Price: £${"%.2f".format(price)} [$ticketType]")
        println("***")
    }
}

// Represents the main ticket machine
class TicketMachine(val originStation: String) {
    private val destinations = listOf(
        Destination("London", 25.00, 45.00),
        Destination("Manchester", 30.00, 55.00),
        Destination("Birmingham", 20.00, 35.00),
        Destination("Liverpool", 28.00, 50.00),
        Destination("Leeds", 27.50, 49.00)
    )

    private var insertedAmount: Double = 0.0
    private var totalTakings: Double = 0.0

    private val scanner = Scanner(System.`in`)

    fun start() {
        println("Welcome to the Train Ticket Machine at $originStation Station!")

        while (true) {
            println("\n--- Main Menu ---")
            println("1. Search for ticket")
            println("2. Insert money")
            println("3. Buy ticket")
            println("4. Show total station takings")
            println("5. Exit")
            print("Choose an option: ")

            when (scanner.nextLine()) {
                "1" -> searchTicket()
                "2" -> insertMoney()
                "3" -> buyTicket()
                "4" -> showTakings()
                "5" -> {
                    println("Thank you for using the Train Ticket Machine. Goodbye!")
                    return
                }
                else -> println("Invalid option, please try again.")
            }
        }
    }

    private var selectedDestination: Destination? = null
    private var selectedTicketType: String? = null
    private var selectedPrice: Double = 0.0

    // (a) Search for a ticket
    private fun searchTicket() {
        println("\nAvailable destinations:")
        destinations.forEachIndexed { index, dest ->
            println("${index + 1}. ${dest.name} (Single £${dest.singleFare}, Return £${dest.returnFare})")
        }

        print("Enter the number of your destination: ")
        val choice = scanner.nextLine().toIntOrNull()

        if (choice == null || choice !in 1..destinations.size) {
            println("Invalid selection.")
            return
        }

        selectedDestination = destinations[choice - 1]

        print("Enter ticket type (Single/Return): ")
        val type = scanner.nextLine().trim().capitalize()

        if (type != "Single" && type != "Return") {
            println("Invalid ticket type.")
            return
        }

        selectedTicketType = type
        selectedPrice = if (type == "Single") selectedDestination!!.singleFare else selectedDestination!!.returnFare

        println("You selected: ${selectedDestination!!.name} - $type ticket (£${"%.2f".format(selectedPrice)})")
    }

    // (b) Insert money
    private fun insertMoney() {
        print("Enter amount to insert (£): ")
        val amount = scanner.nextLine().toDoubleOrNull()
        if (amount == null || amount <= 0) {
            println("Invalid amount.")
            return
        }
        insertedAmount += amount
        println("You have inserted a total of £${"%.2f".format(insertedAmount)}")
    }

    // (c) Buy ticket
    private fun buyTicket() {
        if (selectedDestination == null || selectedTicketType == null) {
            println("Please search for a ticket first.")
            return
        }

        if (insertedAmount < selectedPrice) {
            println("Not enough money inserted. Please insert at least £${"%.2f".format(selectedPrice - insertedAmount)} more.")
            return
        }

        // Deduct money and issue ticket
        insertedAmount -= selectedPrice
        totalTakings += selectedPrice
        selectedDestination!!.totalTakings += selectedPrice

        val ticket = Ticket(originStation, selectedDestination!!.name, selectedTicketType!!, selectedPrice)
        println("\nTicket purchased successfully!")
        ticket.printTicket()

        println("Remaining balance: £${"%.2f".format(insertedAmount)}")
    }

    // Show total takings for this station
    private fun showTakings() {
        println("\nTotal takings at $originStation station: £${"%.2f".format(totalTakings)}")
        println("Takings per destination:")
        destinations.forEach {
            println(" - ${it.name}: £${"%.2f".format(it.totalTakings)}")
        }
    }
}




