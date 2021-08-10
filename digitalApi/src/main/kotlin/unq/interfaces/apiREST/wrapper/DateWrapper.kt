package unq.interfaces.apiREST.wrapper

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale


data class DateWrapper(val year: Int, val month: Int, val day: Int) {
    constructor(date: LocalDateTime) : this(date.year, date.monthValue, date.dayOfMonth)


}
