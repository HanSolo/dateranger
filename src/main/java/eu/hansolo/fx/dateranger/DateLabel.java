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
    private static final PseudoClass     SELECTED_CLASS = PseudoClass.getPseudoClass("selected");
    private static final PseudoClass     START_CLASS    = PseudoClass.getPseudoClass("start");
    private static final PseudoClass     END_CLASS      = PseudoClass.getPseudoClass("end");
    private static final PseudoClass     RANGE          = PseudoClass.getPseudoClass("range");
    private              BooleanProperty today;
    private              BooleanProperty saturday;
    private              BooleanProperty sunday;
    private              BooleanProperty selected;
    private              BooleanProperty start;
    private              BooleanProperty end;
    private              BooleanProperty range;


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
        selected = new BooleanPropertyBase(false) {
            @Override protected void invalidated() { pseudoClassStateChanged(SELECTED_CLASS, get()); }
            @Override public Object getBean() { return DateLabel.this; }
            @Override public String getName() { return "selected"; }
        };
        start    = new BooleanPropertyBase(false) {
            @Override protected void invalidated() { pseudoClassStateChanged(START_CLASS, get()); }
            @Override public Object getBean() { return DateLabel.this; }
            @Override public String getName() { return "start"; }
        };
        end      = new BooleanPropertyBase(false) {
            @Override protected void invalidated() { pseudoClassStateChanged(END_CLASS, get()); }
            @Override public Object getBean() { return DateLabel.this; }
            @Override public String getName() { return "end"; }
        };
        range    = new BooleanPropertyBase(false) {
            @Override protected void invalidated() { pseudoClassStateChanged(RANGE, get()); }
            @Override public Object getBean() { return DateLabel.this; }
            @Override public String getName() { return "range"; }
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

    public boolean isSelected() { return selected.get(); }
    public void setSelected(final boolean selected) { this.selected.set(selected); }
    public BooleanProperty selectedProperty() { return selected; }

    public boolean isStart() { return start.get(); }
    public void setStart(final boolean start) { this.start.set(start); }
    public BooleanProperty startProperty() { return start; }

    public boolean isEnd() { return end.get(); }
    public void setEnd(final boolean end) { this.end.set(end); }
    public BooleanProperty endProperty() { return end; }

    public boolean isRange() { return range.get(); }
    public void setRange(final boolean range) { this.range.set(range); }
    public BooleanProperty rangeProperty() { return range; }
}
