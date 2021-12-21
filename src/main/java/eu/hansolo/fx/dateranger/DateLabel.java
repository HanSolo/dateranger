package eu.hansolo.fx.dateranger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.css.PseudoClass;
import javafx.scene.control.Label;

import java.time.LocalDate;


public class DateLabel extends Label {
    private static final PseudoClass     TODAY_CLASS    = PseudoClass.getPseudoClass("today");
    private static final PseudoClass     SATURDAY_CLASS = PseudoClass.getPseudoClass("saturday");
    private static final PseudoClass     SUNDAY_CLASS   = PseudoClass.getPseudoClass("sunday");
    private              BooleanProperty today;
    private              BooleanProperty saturday;
    private              BooleanProperty sunday;


    public DateLabel() {
        super();
        init();
    }
    public DateLabel(final String text) {
        super(text);
        init();
    }
    public DateLabel(final LocalDate date) {
        super(Integer.toString(date.getDayOfMonth()));
        init();
    }


    private void init() {
        getStyleClass().addAll("date-ranger", "date-label");

        today    = new BooleanPropertyBase(false) {
            @Override protected void invalidated() { pseudoClassStateChanged(TODAY_CLASS, get()); }
            @Override public Object getBean() { return DateLabel.this; }
            @Override public String getName() { return "today"; }
        };
        saturday = new BooleanPropertyBase(false) {
            @Override protected void invalidated() { pseudoClassStateChanged(SATURDAY_CLASS, get()); }
            @Override public Object getBean() { return DateLabel.this; }
            @Override public String getName() { return "saturday"; }
        };
        sunday   = new BooleanPropertyBase(false) {
            @Override protected void invalidated() { pseudoClassStateChanged(SUNDAY_CLASS, get()); }
            @Override public Object getBean() { return DateLabel.this; }
            @Override public String getName() { return "sunday"; }
        };
    }


    public boolean isToday() { return today.get(); }
    public void setToday(final boolean today) { this.today.set(today); }
    public BooleanProperty todayProperty() { return today; }

    public boolean isSaturday() { return saturday.get(); }
    public void setSaturday(final boolean saturday) { this.saturday.set(saturday); }
    public BooleanProperty saturdayProperty() { return saturday; }

    public boolean isSunday() { return sunday.get(); }
    public void setSunday(final boolean sunday) { this.sunday.set(sunday); }
    public BooleanProperty sundayProperty() { return sunday; }
}
