package eu.hansolo.fx.dateranger;

import eu.hansolo.fx.dateranger.Records.DateCell;
import javafx.beans.DefaultProperty;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.IntegerPropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * User: hansolo
 * Date: 21.12.21
 * Time: 13:44
 */
@DefaultProperty("children")
public class DateRanger extends Region {
    private static final double                               PREFERRED_WIDTH        = 250;
    private static final double                               PREFERRED_HEIGHT       = 250;
    private static final double                               MINIMUM_WIDTH          = 50;
    private static final double                               MINIMUM_HEIGHT         = 50;
    private static final double                               MAXIMUM_WIDTH          = 1024;
    private static final double                               MAXIMUM_HEIGHT         = 1024;
    private static final double                               H_SPACE                = 0;
    private static final double                               V_SPACE                = 0;
    private static final StyleablePropertyFactory<DateRanger> FACTORY                = new StyleablePropertyFactory<>(Region.getClassCssMetaData());
    private static final CssMetaData<DateRanger, Color>       BACKGROUND_FILL        = FACTORY.createColorCssMetaData("-background-fill", s -> s.backgroundFill, Color.rgb(22, 26, 26), false);
    private static final CssMetaData<DateRanger, Color>       DAY_OF_WEEK_BORDER     = FACTORY.createColorCssMetaData("-day-of-week-border", s -> s.dayOfWeekBorder, Color.rgb(159, 161, 161), false);
    private static final CssMetaData<DateRanger, Color>       DAY_OF_WEEK_FILL       = FACTORY.createColorCssMetaData("-day-of-week-fill", s -> s.dayOfWeekFill, Color.TRANSPARENT, false);
    private static final CssMetaData<DateRanger, Color>       DAY_OF_WEEK_TEXT_FILL  = FACTORY.createColorCssMetaData("-day-of-week-text-fill", s -> s.dayOfWeekTextFill, Color.rgb(159, 161, 161), false);
    private static final CssMetaData<DateRanger, Color>       WEEK_OF_YEAR_BORDER    = FACTORY.createColorCssMetaData("-week-of-year-border", s -> s.weekOfYearBorder, Color.rgb(159, 161, 161), false);
    private static final CssMetaData<DateRanger, Color>       WEEK_OF_YEAR_FILL      = FACTORY.createColorCssMetaData("-week-of-year-fill", s -> s.weekOfYearFill, Color.TRANSPARENT, false);
    private static final CssMetaData<DateRanger, Color>       WEEK_OF_YEAR_TEXT_FILL = FACTORY.createColorCssMetaData("-week-of-year-text-fill", s -> s.weekOfYearTextFill, Color.rgb(159, 161, 161), false);
    private static final CssMetaData<DateRanger, Color>       WEEKDAY_BORDER         = FACTORY.createColorCssMetaData("-weekday-border", s -> s.weekdayBorder, Color.TRANSPARENT, false);
    private static final CssMetaData<DateRanger, Color>       WEEKDAY_FILL           = FACTORY.createColorCssMetaData("-weekday-fill", s -> s.weekdayFill, Color.TRANSPARENT, false);
    private static final CssMetaData<DateRanger, Color>       WEEKDAY_TEXT_FILL      = FACTORY.createColorCssMetaData("-weekday-text-fill", s -> s.weekdayTextFill, Color.WHITE, false);
    private static final CssMetaData<DateRanger, Color>       SATURDAY_BORDER        = FACTORY.createColorCssMetaData("-saturday-border", s -> s.saturdayBorder, Color.TRANSPARENT, false);
    private static final CssMetaData<DateRanger, Color>       SATURDAY_FILL          = FACTORY.createColorCssMetaData("-saturday-fill", s -> s.saturdayFill, Color.TRANSPARENT, false);
    private static final CssMetaData<DateRanger, Color>       SATURDAY_TEXT_FILL     = FACTORY.createColorCssMetaData("-saturday-text-fill", s -> s.saturdayTextFill, Color.rgb(159, 161, 161), false);
    private static final CssMetaData<DateRanger, Color>       SUNDAY_BORDER          = FACTORY.createColorCssMetaData("-sunday-border", s -> s.sundayBorder, Color.TRANSPARENT, false);
    private static final CssMetaData<DateRanger, Color>       SUNDAY_FILL            = FACTORY.createColorCssMetaData("-sunday-fill", s -> s.sundayFill, Color.TRANSPARENT, false);
    private static final CssMetaData<DateRanger, Color>       SUNDAY_TEXT_FILL       = FACTORY.createColorCssMetaData("-sunday-text-fill", s -> s.sundayTextFill, Color.rgb(159, 161, 161), false);
    private static final CssMetaData<DateRanger, Color>       TODAY_BORDER           = FACTORY.createColorCssMetaData("-today-border", s -> s.todayBorder, Color.TRANSPARENT, false);
    private static final CssMetaData<DateRanger, Color>       TODAY_FILL             = FACTORY.createColorCssMetaData("-today-fill", s -> s.todayFill, Color.TRANSPARENT, false);
    private static final CssMetaData<DateRanger, Color>       TODAY_TEXT_FILL        = FACTORY.createColorCssMetaData("-today-text-fill", s -> s.todayTextFill, Color.rgb(92, 164, 246), false);
    private static final CssMetaData<DateRanger, Color>       SELECTED_BORDER        = FACTORY.createColorCssMetaData("-selected-border", s -> s.selectedBorder, Color.TRANSPARENT, false);
    private static final CssMetaData<DateRanger, Color>       SELECTED_FILL          = FACTORY.createColorCssMetaData("-selected-fill", s -> s.selectedFill, Color.rgb(92, 164, 246), false);
    private static final CssMetaData<DateRanger, Color>       SELECTED_TEXT_FILL     = FACTORY.createColorCssMetaData("-selected-text-fill", s -> s.selectedTextFill, Color.WHITE, false);
    private static final CssMetaData<DateRanger, Color>       START_BORDER           = FACTORY.createColorCssMetaData("-start-border", s -> s.startBorder, Color.rgb(255, 127, 2), false);
    private static final CssMetaData<DateRanger, Color>       START_FILL             = FACTORY.createColorCssMetaData("-start-fill", s -> s.startFill, Color.rgb(255, 127, 2, 0.35), false);
    private static final CssMetaData<DateRanger, Color>       START_TEXT_FILL        = FACTORY.createColorCssMetaData("-start-text-fill", s -> s.startTextFill, Color.WHITE, false);
    private static final CssMetaData<DateRanger, Color>       RANGE_BORDER           = FACTORY.createColorCssMetaData("-range-border", s -> s.rangeBorder, Color.rgb(255, 127, 2), false);
    private static final CssMetaData<DateRanger, Color>       RANGE_FILL             = FACTORY.createColorCssMetaData("-range-fill", s -> s.rangeFill, Color.rgb(255, 127, 2, 0.35), false);
    private static final CssMetaData<DateRanger, Color>       RANGE_TEXT_FILL        = FACTORY.createColorCssMetaData("-range-text-fill", s -> s.rangeTextFill, Color.WHITE, false);
    private static final CssMetaData<DateRanger, Color>       END_BORDER             = FACTORY.createColorCssMetaData("-end-border", s -> s.endBorder, Color.rgb(255, 127, 2), false);
    private static final CssMetaData<DateRanger, Color>       END_FILL               = FACTORY.createColorCssMetaData("-end-fill", s -> s.endFill, Color.rgb(255, 127, 2, 0.35), false);
    private static final CssMetaData<DateRanger, Color>       END_TEXT_FILL          = FACTORY.createColorCssMetaData("-end-text-fill", s -> s.endTextFill, Color.WHITE, false);
    private final        StyleableProperty<Color>             backgroundFill;
    private final        StyleableProperty<Color>             dayOfWeekBorder;
    private final        StyleableProperty<Color>             dayOfWeekFill;
    private final        StyleableProperty<Color>             dayOfWeekTextFill;
    private final        StyleableProperty<Color>             weekOfYearBorder;
    private final        StyleableProperty<Color>             weekOfYearFill;
    private final        StyleableProperty<Color>             weekOfYearTextFill;
    private final        StyleableProperty<Color>             weekdayBorder;
    private final        StyleableProperty<Color>             weekdayFill;
    private final        StyleableProperty<Color>             weekdayTextFill;
    private final        StyleableProperty<Color>             saturdayBorder;
    private final        StyleableProperty<Color>             saturdayFill;
    private final        StyleableProperty<Color>             saturdayTextFill;
    private final        StyleableProperty<Color>             sundayBorder;
    private final        StyleableProperty<Color>             sundayFill;
    private final        StyleableProperty<Color>             sundayTextFill;
    private final        StyleableProperty<Color>             todayBorder;
    private final        StyleableProperty<Color>             todayFill;
    private final        StyleableProperty<Color>             todayTextFill;
    private final        StyleableProperty<Color>             selectedBorder;
    private final        StyleableProperty<Color>             selectedFill;
    private final        StyleableProperty<Color>             selectedTextFill;
    private final        StyleableProperty<Color>             startBorder;
    private final        StyleableProperty<Color>             startFill;
    private final        StyleableProperty<Color>             startTextFill;
    private final        StyleableProperty<Color>             rangeBorder;
    private final        StyleableProperty<Color>             rangeFill;
    private final        StyleableProperty<Color>             rangeTextFill;
    private final        StyleableProperty<Color>             endBorder;
    private final        StyleableProperty<Color>             endFill;
    private final        StyleableProperty<Color>             endTextFill;
    private              BooleanBinding                       showing;
    private              String                               userAgentStyleSheet;
    private              double                               width;
    private              double                               height;
    private              GridPane                             pane;
    private              Map<Month, List<DateCell>>           cellMap;
    private              List<ColumnConstraints>              columnConstraints;
    private              List<RowConstraints>                 rowConstraints;
    private              ObjectProperty<Locale>               locale;
    private              IntegerProperty                      currentYear;
    private              ObjectProperty<Month>                currentMonth;
    private              ObjectProperty<LocalDate>            selectedDate;
    private              ObjectProperty<LocalDate>            startDate;
    private              ObjectProperty<LocalDate>            endDate;
    private              AtomicBoolean                        shiftDown;
    private              EventHandler<MouseEvent>             mouseHandler;
    private              EventHandler<KeyEvent>               keyHandler;



    // ******************** Constructors **************************************
    public DateRanger() {
        this(LocalDate.now());
    }
    public DateRanger(final LocalDate date) {
        backgroundFill     = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-background-fill: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return BACKGROUND_FILL; }
            @Override public String getName() { return "backgroundFill"; }
        };
        dayOfWeekBorder    = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-day-of-week-border: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return DAY_OF_WEEK_BORDER; }
            @Override public String getName() { return "dayOfWeekBorder"; }
        };
        dayOfWeekFill      = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-day-of-week-fill: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return DAY_OF_WEEK_FILL; }
            @Override public String getName() { return "dayOfWeekFill"; }
        };
        dayOfWeekTextFill  = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-day-of-week-text-fill: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return DAY_OF_WEEK_TEXT_FILL; }
            @Override public String getName() { return "dayOfWeekTextFill"; }
        };
        weekOfYearBorder   = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-week-of-year-border: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return WEEK_OF_YEAR_BORDER; }
            @Override public String getName() { return "weekOfYearBorder"; }
        };
        weekOfYearFill     = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-week-of-year-fill: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return WEEK_OF_YEAR_FILL; }
            @Override public String getName() { return "weekOfYearFill"; }
        };
        weekOfYearTextFill = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-week-of-year-text-fill: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return WEEK_OF_YEAR_TEXT_FILL; }
            @Override public String getName() { return "weekOfYearTextFill"; }
        };
        weekdayBorder      = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-weekday-border: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return WEEKDAY_BORDER; }
            @Override public String getName() { return "weekdayBorder"; }
        };
        weekdayFill        = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-weekday-fill: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return WEEKDAY_FILL; }
            @Override public String getName() { return "weekdayFill"; }
        };
        weekdayTextFill    = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-weekday-text-fill: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return WEEKDAY_TEXT_FILL; }
            @Override public String getName() { return "weekdayTextFill"; }
        };
        saturdayBorder     = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-saturday-border: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return SATURDAY_BORDER; }
            @Override public String getName() { return "saturdayBorder"; }
        };
        saturdayFill       = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-saturday-fill: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return SATURDAY_FILL; }
            @Override public String getName() { return "saturdayFill"; }
        };
        saturdayTextFill   = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-saturday-text-fill: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return SATURDAY_TEXT_FILL; }
            @Override public String getName() { return "saturdayTextFill"; }
        };
        sundayBorder       = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-sunday-border: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return SUNDAY_BORDER; }
            @Override public String getName() { return "sundayBorder"; }
        };
        sundayFill         = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-sunday-fill: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return SUNDAY_FILL; }
            @Override public String getName() { return "sundayFill"; }
        };
        sundayTextFill     = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-sunday-text-fill: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return SUNDAY_TEXT_FILL; }
            @Override public String getName() { return "sundayTextFill"; }
        };
        todayBorder        = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-today-border: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return TODAY_BORDER; }
            @Override public String getName() { return "todayBorder"; }
        };
        todayFill          = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-today-fill: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return TODAY_FILL; }
            @Override public String getName() { return "todayFill"; }
        };
        todayTextFill      = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-today-text-fill: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return TODAY_TEXT_FILL; }
            @Override public String getName() { return "todayTextFill"; }
        };
        selectedBorder     = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-selected-fill: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return SELECTED_BORDER; }
            @Override public String getName() { return "selectedBorder"; }
        };
        selectedFill       = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-selected-fill: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return SELECTED_FILL; }
            @Override public String getName() { return "selectedFill"; }
        };
        selectedTextFill   = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-selected-text-fill: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return SELECTED_TEXT_FILL; }
            @Override public String getName() { return "selectedTextFill"; }
        };
        startBorder        = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-start-border: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return START_BORDER; }
            @Override public String getName() { return "startBorder"; }
        };
        startFill          = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-start-fill: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return START_FILL; }
            @Override public String getName() { return "startFill"; }
        };
        startTextFill      = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-start-text-fill: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return START_TEXT_FILL; }
            @Override public String getName() { return "startTextFill"; }
        };
        rangeBorder        = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-range-border: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return RANGE_BORDER; }
            @Override public String getName() { return "rangeBorder"; }
        };
        rangeFill          = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-range-fill: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return RANGE_FILL; }
            @Override public String getName() { return "rangeFill"; }
        };
        rangeTextFill      = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-range-text-fill: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return RANGE_TEXT_FILL; }
            @Override public String getName() { return "rangeTextFill"; }
        };
        endBorder          = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-end-border: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return END_BORDER; }
            @Override public String getName() { return "endBorder"; }
        };
        endFill            = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-end-fill: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return END_FILL; }
            @Override public String getName() { return "endFill"; }
        };
        endTextFill        = new StyleableObjectProperty<>(BACKGROUND_FILL.getInitialValue(DateRanger.this)) {
            @Override protected void invalidated() { setStyle("-end-text-fill: " + get().toString().replace("0x", "#") + ";"); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return END_TEXT_FILL; }
            @Override public String getName() { return "endTextFill"; }
        };
        locale             = new ObjectPropertyBase<>(Locale.getDefault()) {
            @Override protected void invalidated() { updateCells(); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public String getName() { return "locale"; }
        };
        currentYear        = new IntegerPropertyBase(date.getYear()) {
            @Override protected void invalidated() { redraw(); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public String getName() { return "currentYear"; }
        };
        currentMonth       = new ObjectPropertyBase<>(date.getMonth()) {
            @Override protected void invalidated() { redraw(); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public String getName() { return "currentMonth"; }
        };
        selectedDate       = new ObjectPropertyBase<>(date) {
            @Override protected void invalidated() { redraw(); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public String getName() { return "selectedDate"; }
        };
        startDate          = new ObjectPropertyBase<>() {
            @Override protected void invalidated() {
                if (null != getEndDate() && null != get() && get().isAfter(getEndDate())) {
                    LocalDate newStartDate = getEndDate();
                    setEndDate(get());
                    set(newStartDate);
                }
                redraw();
            }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public String getName() { return "startDate"; }
        };
        endDate            = new ObjectPropertyBase<>() {
            @Override protected void invalidated() {
                if (null == getStartDate()) {
                    set(null);
                } else {
                    if (null != get() && get().isBefore(getStartDate())) {
                        LocalDate newEndDate = getStartDate();
                        setStartDate(get());
                        setEndDate(newEndDate);
                    }
                }
                redraw();
            }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public String getName() { return "endDate"; }
        };
        shiftDown          = new AtomicBoolean(false);
        mouseHandler       = evt -> onMousePressed(evt);
        keyHandler         = evt -> shiftDown.set(evt.isShiftDown());
        cellMap = new ConcurrentHashMap<>();
        for (Month month : Month.values()) { cellMap.put(month, new LinkedList<>()); }

        columnConstraints = new LinkedList<>();
        for (int x = 0 ; x < 8 ; x++) { columnConstraints.add(new ColumnConstraints()); }

        rowConstraints = new LinkedList<>();
        for (int y = 0 ; y < 7 ; y++) { rowConstraints.add(new RowConstraints()); }

        initGraphics();
        registerListeners();

        updateCells();
    }


    // ******************** Initialization ************************************
    private void initGraphics() {
        if (Double.compare(getPrefWidth(), 0.0) <= 0 || Double.compare(getPrefHeight(), 0.0) <= 0 || Double.compare(getWidth(), 0.0) <= 0 || Double.compare(getHeight(), 0.0) <= 0) {
            if (getPrefWidth() > 0 && getPrefHeight() > 0) {
                setPrefSize(getPrefWidth(), getPrefHeight());
            } else {
                setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
            }
        }

        getStyleClass().add("date-ranger");

        pane = new GridPane();
        pane.setHgap(H_SPACE);
        pane.setVgap(V_SPACE);
        for (int y = 0 ; y < 7 ; y++) {
            for (int x = 0 ; x < 8 ; x++) {
                DateLabel dateLabel = new DateLabel(y == 0 && x > 0 ? DayOfWeek.of(x).getDisplayName(TextStyle.NARROW_STANDALONE, locale.get()) : "");
                GridPane.setHalignment(dateLabel, HPos.CENTER);
                GridPane.setValignment(dateLabel, VPos.CENTER);
                GridPane.setFillWidth(dateLabel, true);
                GridPane.setFillHeight(dateLabel, true);
                pane.add(dateLabel, x, y);
                dateLabel.setOnMousePressed(mouseHandler);
            }
        }

        getChildren().setAll(pane);
    }

    private void registerListeners() {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
        currentMonth.addListener((o, ov, nv) -> {
            if (ov.equals(Month.DECEMBER) && nv.equals(Month.JANUARY)) {
                setCurrentYear(Year.of(getCurrentYear() + 1));
            } else if (ov.equals(Month.JANUARY) && nv.equals(Month.DECEMBER)) {
                setCurrentYear(Year.of(getCurrentYear() - 1));
            }
        });
        if (null != getScene()) {
            setupBinding();
        } else {
            sceneProperty().addListener((o1, ov1, nv1) -> {
                if (null == nv1) { return; }
                if (null != getScene().getWindow()) {
                    setupBinding();
                } else {
                    sceneProperty().get().windowProperty().addListener((o2, ov2, nv2) -> {
                        if (null == nv2) { return; }
                        setupBinding();
                    });
                }
            });
        }
    }

    private void setupBinding() {
        showing = Bindings.createBooleanBinding(() -> {
            if (getScene() != null && getScene().getWindow() != null) {
                return getScene().getWindow().isShowing();
            } else {
                return false;
            }
        }, sceneProperty(), getScene().windowProperty(), getScene().getWindow().showingProperty());

        showing.addListener(o -> {
            if (showing.get()) {
                getScene().getWindow().addEventHandler(KeyEvent.ANY, keyHandler);
            }
        });
    }


    // ******************** Methods *******************************************
    @Override protected double computeMinWidth(final double height) { return MINIMUM_WIDTH; }
    @Override protected double computeMinHeight(final double width)  { return MINIMUM_HEIGHT; }
    @Override protected double computePrefWidth(final double height) { return super.computePrefWidth(height); }
    @Override protected double computePrefHeight(final double width) { return super.computePrefHeight(width); }
    @Override protected double computeMaxWidth(final double height)  { return MAXIMUM_WIDTH; }
    @Override protected double computeMaxHeight(final double width)  { return MAXIMUM_HEIGHT; }

    @Override public ObservableList<Node> getChildren() { return super.getChildren(); }

    public Color getBackgroundFill() { return backgroundFill.getValue(); }
    public void setBackgroundFill(final Color backgroundFill) { this.backgroundFill.setValue(backgroundFill); }
    public StyleableProperty<Color> backgroundFillProperty() { return backgroundFill; }

    public Color getDayOfWeekBorder() { return dayOfWeekBorder.getValue(); }
    public void setDayOfWeekBorder(final Color dayOfWeekBorder) { this.dayOfWeekBorder.setValue(dayOfWeekBorder); }
    public StyleableProperty<Color> dayOfWeekBorderProperty() { return dayOfWeekBorder; }

    public Color getDayOfWeekFill() { return dayOfWeekFill.getValue(); }
    public void setDayOfWeekFill(final Color dayOfWeekFill) { this.dayOfWeekFill.setValue(dayOfWeekFill); }
    public StyleableProperty<Color> dayOfWeekFillProperty() { return dayOfWeekFill; }

    public Color getDayOfWeekTextFill() { return dayOfWeekTextFill.getValue(); }
    public void setDayOfWeekTextFill(final Color dayOfWeekTextFill) { this.dayOfWeekTextFill.setValue(dayOfWeekTextFill); }
    public StyleableProperty<Color> dayOfWeekTextFillProperty() { return dayOfWeekTextFill; }

    public Color getWeekOfYearBorder() { return weekOfYearBorder.getValue(); }
    public void setWeekOfYearBorder(final Color weekOfYearBorder) { this.weekOfYearBorder.setValue(weekOfYearBorder); }
    public StyleableProperty<Color> weekOfYearBorderProperty() { return weekOfYearBorder; }

    public Color getWeekOfYearFill() { return weekOfYearFill.getValue(); }
    public void setWeekOfYearFill(final Color weekOfYearFill) { this.weekOfYearFill.setValue(weekOfYearFill); }
    public StyleableProperty<Color> weekOfYearFillProperty() { return weekOfYearFill; }

    public Color getWeekOfYearTextFill() { return weekOfYearTextFill.getValue(); }
    public void setWeekOfYearTextFill(final Color weekOfYearTextFill) { this.weekOfYearTextFill.setValue(weekOfYearTextFill); }
    public StyleableProperty<Color> weekOfYearTextFillProperty() { return weekOfYearTextFill; }

    public Color getWeekdayBorder() { return weekdayBorder.getValue(); }
    public void setWeekdayBorder(final Color weekdayBorder) { this.weekdayBorder.setValue(weekdayBorder); }
    public StyleableProperty<Color> weekdayBorderProperty() { return weekdayBorder; }

    public Color getWeekdayFill() { return weekdayFill.getValue(); }
    public void setWeekdayFill(final Color weekdayFill) { this.weekdayFill.setValue(weekdayFill); }
    public StyleableProperty<Color> weekdayFillProperty() { return weekdayFill; }

    public Color getWeekdayTextFill() { return weekdayTextFill.getValue(); }
    public void setWeekdayTextFill(final Color weekdayTextFill) { this.weekdayTextFill.setValue(weekdayTextFill); }
    public StyleableProperty<Color> weekdayTextFillProperty() { return weekdayTextFill; }

    public Color getSaturdayBorder() { return saturdayBorder.getValue(); }
    public void setSaturdayBorder(final Color saturdayBorder) { this.saturdayBorder.setValue(saturdayBorder); }
    public StyleableProperty<Color> saturdayBorderProperty() { return saturdayBorder; }

    public Color getSaturdayFill() { return saturdayFill.getValue(); }
    public void setSaturdayFill(final Color saturdayFill) { this.saturdayFill.setValue(saturdayFill); }
    public StyleableProperty<Color> saturdayFillProperty() { return saturdayFill; }

    public Color getSaturdayTextFill() { return saturdayTextFill.getValue(); }
    public void setSaturdayTextFill(final Color saturdayTextFill) { this.saturdayTextFill.setValue(saturdayTextFill); }
    public StyleableProperty<Color> saturdayTextFillProperty() { return saturdayTextFill; }

    public Color getSundayBorder() { return sundayBorder.getValue(); }
    public void setSundayBorder(final Color sundayBorder) { this.sundayBorder.setValue(sundayBorder); }
    public StyleableProperty<Color> sundayBorderProperty() { return sundayBorder; }

    public Color getSundayFill() { return sundayFill.getValue(); }
    public void setSundayFill(final Color sundayFill) { this.sundayFill.setValue(sundayFill); }
    public StyleableProperty<Color> sundayFillProperty() { return sundayFill; }

    public Color getSundayTextFill() { return sundayTextFill.getValue(); }
    public void setSundayTextFill(final Color sundayTextFill) { this.sundayTextFill.setValue(sundayTextFill); }
    public StyleableProperty<Color> sundayTextFillProperty() { return sundayTextFill; }

    public Color getTodayBorder() { return todayBorder.getValue(); }
    public void setTodayBorder(final Color todayBorder) { this.todayBorder.setValue(todayBorder); }
    public StyleableProperty<Color> todayBorderProperty() { return todayBorder; }

    public Color getTodayFill() { return todayFill.getValue(); }
    public void setTodayFill(final Color todayFill) { this.todayFill.setValue(todayFill); }
    public StyleableProperty<Color> todayFillProperty() { return todayFill; }

    public Color getTodayTextFill() { return todayTextFill.getValue(); }
    public void setTodayTextFill(final Color todayTextFill) { this.todayTextFill.setValue(todayTextFill); }
    public StyleableProperty<Color> todayTextFillProperty() { return todayTextFill; }

    public Color getSelectedBorder() { return selectedBorder.getValue(); }
    public void setSelectedBorder(final Color selectedBorder) { this.selectedBorder.setValue(selectedBorder); }
    public StyleableProperty<Color> selectedBorderProperty() { return selectedBorder; }

    public Color getSelectedFill() { return selectedFill.getValue(); }
    public void setSelectedFill(final Color selectedFill) { this.selectedFill.setValue(selectedFill); }
    public StyleableProperty<Color> selectedFillProperty() { return selectedFill; }

    public Color getSelectedTextFill() { return selectedTextFill.getValue(); }
    public void setSelectedTextFill(final Color selectedTextFill) { this.selectedTextFill.setValue(selectedTextFill); }
    public StyleableProperty<Color> selectedTextFillProperty() { return selectedTextFill; }

    public Color getStartBorder() { return startBorder.getValue(); }
    public void setStartBorder(final Color startBorder) { this.startBorder.setValue(startBorder); }
    public StyleableProperty<Color> startBorderProperty() { return startBorder; }

    public Color getStartFill() { return startFill.getValue(); }
    public void setStartFill(final Color startFill) { this.startFill.setValue(startFill); }
    public StyleableProperty<Color> startFillProperty() { return startFill; }

    public Color getStartTextFill() { return startTextFill.getValue(); }
    public void setStartTextFill(final Color startTextFill) { this.startTextFill.setValue(startTextFill); }
    public StyleableProperty<Color> startTextFillProperty() { return startTextFill; }

    public Color getRangeBorder() { return rangeBorder.getValue(); }
    public void setRangeBorder(final Color rangeBorder) { this.rangeBorder.setValue(rangeBorder); }
    public StyleableProperty<Color> rangeBorderProperty() { return rangeBorder; }

    public Color getRangeFill() { return rangeFill.getValue(); }
    public void setRangeFill(final Color rangeFill) { this.rangeFill.setValue(rangeFill); }
    public StyleableProperty<Color> rangeFillProperty() { return rangeFill; }

    public Color getRangeTextFill() { return rangeTextFill.getValue(); }
    public void setRangeTextFill(final Color rangeTextFill) { this.rangeTextFill.setValue(rangeTextFill); }
    public StyleableProperty<Color> rangeTextFillProperty() { return rangeTextFill; }

    public Color getEndBorder() { return endBorder.getValue(); }
    public void setEndBorder(final Color endBorder) { this.endBorder.setValue(endBorder); }
    public StyleableProperty<Color> endBorderProperty() { return endBorder; }

    public Color getEndFill() { return endFill.getValue(); }
    public void setEndFill(final Color endFill) { this.endFill.setValue(endFill); }
    public StyleableProperty<Color> endFillProperty() { return endFill; }

    public Color getEndTextFill() { return endTextFill.getValue(); }
    public void setEndTextFill(final Color endTextFill) { this.endTextFill.setValue(endTextFill); }
    public StyleableProperty<Color> endTextFillProperty() { return endTextFill; }

    public boolean isShowing() { return null != showing && showing.get(); }
    public BooleanBinding showingProperty() { return showing; }

    public Locale getLocale() { return locale.get(); }
    public void setLocale(final Locale locale) { this.locale.set(locale); }
    public ObjectProperty<Locale> localeProperty() { return locale; }

    public void setCurrentDate(final LocalDate currentDate) {
        setCurrentYear(Year.of(currentDate.getYear()));
        setCurrentMonth(currentDate.getMonth());
        setSelectedDate(currentDate);
    }

    public int getCurrentYear() { return currentYear.get(); }
    public void setCurrentYear(final Year year) { currentYear.set(year.getValue()); }
    public IntegerProperty currentYearProperty() { return currentYear; }

    public Month getCurrentMonth() { return currentMonth.get(); }
    public void setCurrentMonth(final Month month) { currentMonth.set(month); }
    public ObjectProperty<Month> currentMonthProperty() { return currentMonth; }

    public LocalDate getSelectedDate() { return selectedDate.get(); }
    public void setSelectedDate(final LocalDate selectedDate) { this.selectedDate.set(selectedDate); }
    public ObjectProperty<LocalDate> selectedDateProperty() { return selectedDate; }

    public LocalDate getStartDate() { return startDate.get(); }
    public void setStartDate(final LocalDate startDate) { this.startDate.set(startDate); }
    public ObjectProperty<LocalDate> startDateProperty() { return startDate; }

    public LocalDate getEndDate() { return endDate.get(); }
    public void setEndDate(final LocalDate endDate) { this.endDate.set(endDate); }
    public ObjectProperty<LocalDate> endDateProperty() { return endDate; }

    public Duration getDuration() {
        if (null == getStartDate() || null == getEndDate()) { return Duration.ZERO; }
        return Duration.between(getStartDate(), getEndDate());
    }

    public void onMousePressed(final MouseEvent evt) {
        EventType<? extends Event> type = evt.getEventType();
        if (type.equals(MouseEvent.MOUSE_PRESSED)) {
            if (evt.getSource() instanceof DateLabel) {
                DateLabel dateLabel = (DateLabel) evt.getSource();
                if (pane.getColumnIndex(dateLabel) > 0 && pane.getRowIndex(dateLabel) > 0 && !dateLabel.getText().isEmpty()) {
                    final LocalDate selectedDate = LocalDate.of(currentYear.get(), currentMonth.get().getValue(), Integer.parseInt(dateLabel.getText()));
                    if (shiftDown.get()) {
                        setSelectedDate(selectedDate);
                        if (null != getStartDate()) {
                            if (selectedDate.isBefore(getStartDate())) {
                                setEndDate(getStartDate());
                                setStartDate(selectedDate);
                            } else {
                                setEndDate(selectedDate);
                            }
                        }
                    } else {
                        setSelectedDate(selectedDate);
                        setStartDate(null);
                        setEndDate(null);
                        startDate.set(selectedDate);
                    }
                }
            }
        }
    }

    private void updateCells() {
        // Day of week on top
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            Optional<Node> optNode = getCellAt(dayOfWeek.getValue(), 0);
            if (optNode.isPresent()) {
                DateLabel dateLabel = (DateLabel) optNode.get();
                dateLabel.getStyleClass().setAll("label", "date-ranger", "day-of-week");
                dateLabel.setText(dayOfWeek.getDisplayName(TextStyle.NARROW_STANDALONE, locale.get()));
            }
        }

        // Calendar week on the left
        LocalDate currentDate = LocalDate.of(currentYear.get(), currentMonth.get().getValue(), 1);
        for (int y = 1 ; y < 7 ; y++) {
            Optional<Node> optNode = getCellAt(0, y);
            if (optNode.isPresent()) {
                DateLabel dateLabel = (DateLabel) optNode.get();
                dateLabel.getStyleClass().setAll("label", "date-ranger", "week-of-year");
                dateLabel.setText(Integer.toString(currentDate.get(WeekFields.ISO.weekOfYear())));
            }
            currentDate = currentDate.plusDays(7);
        }

        final boolean isLeap = Year.isLeap(currentYear.get());
        for (Month month : Month.values()) {
            cellMap.get(month).clear();
            LocalDate date = LocalDate.of(currentYear.get(), month.getValue(), 1);
            int noOfDays = month.length(isLeap);
            int y = 1;
            for (int i = 1 ; i <= noOfDays ; i++) {
                final DayOfWeek dayOfWeek = date.getDayOfWeek();
                if (dayOfWeek == DayOfWeek.MONDAY) { y++; }
                final DateCell dateCell = new DateCell(date, dayOfWeek.getValue(), y);
                cellMap.get(month).add(dateCell);
                date = date.plusDays(1);
            }
        }

        redraw();
    }

    private Optional<Node> getCellAt(final int x, final int y) {
        return pane.getChildren().stream().filter(child -> pane.getColumnIndex(child) == x && pane.getRowIndex(child) == y).findFirst();
    }


    // ******************** Layout *******************************************
    @Override public void layoutChildren() {
        super.layoutChildren();
    }

    @Override public String getUserAgentStylesheet() {
        if (null == userAgentStyleSheet) { userAgentStyleSheet = DateRanger.class.getResource("date-ranger.css").toExternalForm(); }
        return userAgentStyleSheet;
    }

    private void resize() {
        width  = getWidth() - getInsets().getLeft() - getInsets().getRight();
        height = getHeight() - getInsets().getTop() - getInsets().getBottom();

        if (width > 0 && height > 0) {
            pane.setMaxSize(width, height);
            pane.setPrefSize(width, height);
            pane.relocate((getWidth() - width) * 0.5, (getHeight() - height) * 0.5);
            final double cellWidth  = (width - 7 * H_SPACE) / 8;
            final double cellHeight = (height - 6 * V_SPACE) / 7;
            for (int x = 0 ; x < 8 ; x++) { columnConstraints.get(x).setPrefWidth(cellWidth); }
            for (int y = 0 ; y < 7 ; y++) { rowConstraints.get(y).setPrefHeight(cellHeight); }
            final Font cellFont = cellHeight < cellWidth ? Font.font(cellHeight * 0.65) : Font.font(cellWidth * 0.45);
            final Font weekFont = cellHeight < cellWidth ? Font.font(cellHeight * 0.55) : Font.font(cellWidth * 0.35);
            pane.getChildren().stream().map(node -> (DateLabel) node).forEach(dateLabel -> {
                dateLabel.setPrefSize(cellWidth, cellHeight);
                dateLabel.setFont(pane.getColumnIndex(dateLabel) == 0 ? weekFont : cellFont);
            });

            redraw();
        }
    }

    private void redraw() {
        final LocalDate      now              = LocalDate.now();
        final int            currentWeek      = now.get(WeekFields.ISO.weekOfYear());
        final DayOfWeek      currentDayOfWeek = now.getDayOfWeek();
        final LocalDate      startDate        = getStartDate();
        final LocalDate      endDate          = getEndDate();
        final boolean        isRange          = (null != startDate && null != endDate);
        final LocalDate      selectedDate     = getSelectedDate();
        final List<DateCell> cells            = cellMap.get(currentMonth.get());

        for (int y = 1 ; y < 7 ; y++) {
            for (int x = 1 ; x < 8; x++) {
                Optional<Node> optNode = getCellAt(x, y);
                if (optNode.isPresent()) {
                    DateLabel dateLabel = (DateLabel) optNode.get();
                    dateLabel.setText("");
                    dateLabel.setToday(false);
                    dateLabel.setStart(false);
                    dateLabel.setRange(false);
                    dateLabel.setEnd(false);
                }
            }
        }

        // Calendar week on the left
        LocalDate currentDate = LocalDate.of(currentYear.get(), currentMonth.get().getValue(), 1);
        for (int y = 1 ; y < 7 ; y++) {
            Optional<Node> optNode = getCellAt(0, y);
            if (optNode.isPresent()) {
                DateLabel dateLabel = (DateLabel) optNode.get();
                dateLabel.getStyleClass().setAll("label", "date-ranger", "week-of-year");
                dateLabel.setText(Integer.toString(currentDate.get(WeekFields.ISO.weekOfYear())));
                currentDate = currentDate.plus(1, ChronoUnit.WEEKS);
            }
        }

        // Colorize current day of week
        currentDate = LocalDate.of(currentYear.get(), currentMonth.get().getValue(), 1);
        for (int x = 1 ; x < 8 ; x++) {
            Optional<Node> optNode = getCellAt(x, 0);
            if (optNode.isPresent()) {
                DateLabel dateLabel = (DateLabel) optNode.get();
                if (currentYear.get() == now.getYear() && now.getMonth().equals(currentDate.getMonth())) {
                    dateLabel.setToday(dateLabel.getText().equals(currentDayOfWeek.getDisplayName(TextStyle.NARROW_STANDALONE, locale.get())));
                } else {
                    dateLabel.setToday(false);
                }
            }
        }

        // Colorize current week
        for (int y = 1 ; y < 7 ; y++) {
            Optional<Node> optNode = getCellAt(0, y);
            if (optNode.isPresent()) {
                DateLabel dateLabel = (DateLabel) optNode.get();
                dateLabel.setToday(dateLabel.getText().equals(Integer.toString(currentWeek)));
            }
        }

        // Draw cells of month
        cells.forEach(cell -> {
            Optional<Node> optNode = getCellAt(cell.x(), cell.y());
            if (optNode.isPresent()) {
                DateLabel dateLabel = (DateLabel) optNode.get();
                dateLabel.setSaturday(cell.getDayOfWeek().equals(DayOfWeek.SATURDAY));
                dateLabel.setSunday(cell.getDayOfWeek().equals(DayOfWeek.SUNDAY));
                dateLabel.setToday(cell.date().isEqual(now));
                if (null != startDate) { dateLabel.setStart(startDate.isEqual(cell.date())); }
                if (null != endDate)   { dateLabel.setEnd(endDate.isEqual(cell.date())); }
                if (isRange) {
                    dateLabel.setRange(!cell.date().isBefore(startDate) && !cell.date().isAfter(endDate));
                } else {
                    dateLabel.setStart(false);
                    dateLabel.setRange(false);
                    dateLabel.setEnd(false);
                }
                dateLabel.setSelected(selectedDate.isEqual(cell.date()));
                dateLabel.setText(Integer.toString(cell.getDayOfMonth()));
            }
        });
    }
}