import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;

import org.junit.Test;

public class TestLocalDateTime {
    @Test
    public void test_LocalDate_parse() {
        var text = "2025-03-14";
        LocalDate date = DateTimeFormatter.ISO_LOCAL_DATE.parse(text, LocalDate::from).plusDays(3);
        System.out.println();
        System.out.println(date);

        TemporalAccessor temporal = DateTimeFormatter.ISO_LOCAL_DATE.parse(text);
        int y = temporal.query(TemporalQueries.localDate()).getYear();
        int m = temporal.query(TemporalQueries.localDate()).getMonthValue();
        int d = temporal.query(TemporalQueries.localDate()).getDayOfMonth();

        System.out.println(String.format("%d/%02d/%d", d, m, y));
    }
}
