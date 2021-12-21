package eu.hansolo.fx.dateranger;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;


public class Records {
    public record DateCell(LocalDate date, int x, int y) {
        public DayOfWeek getDayOfWeek() { return date.getDayOfWeek(); }

        public int getDayOfMonth() { return date.getDayOfMonth(); }

        public Month getMonth() { return date.getMonth(); }
        public int getMonthValue() { return date.getMonthValue(); }

        public int getYear() { return date.getYear(); }
    }
}
