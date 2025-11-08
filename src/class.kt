class Destination(val name: String, val singleFare: Double, val returnFare: Double) {
    var totalTakings: Double = 0.0
}

class Ticket(
    val origin: String,
    val destination: String,
    val ticketType: String, // "Single" or "Return"
    val price: Double
) {

    class TicketMachine(val originStation: String) {
    }
    private val destinations = listOf(
        Destination("London", 25.00, 45.00),
        Destination("Manchester", 30.00, 55.00),
        Destination("Birmingham", 20.00, 35.00),
        Destination("Liverpool", 28.00, 50.00),
        Destination("Leeds", 27.50, 49.00)
    )
}
