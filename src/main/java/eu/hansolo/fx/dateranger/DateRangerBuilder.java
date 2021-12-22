package eu.hansolo.fx.dateranger;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.HashMap;
import java.util.Locale;


public class DateRangerBuilder<B extends DateRangerBuilder<B>> {
    private HashMap<String, Property> properties = new HashMap<>();


    // ******************** Constructors **************************************
    protected DateRangerBuilder() {}


    // ******************** Methods *******************************************
    public static final DateRangerBuilder create() {
        return new DateRangerBuilder();
    }

    public final B prefSize(final double WIDTH, final double HEIGHT) {
        properties.put("prefSize", new SimpleObjectProperty<>(new Dimension2D(WIDTH, HEIGHT)));
        return (B)this;
    }
    public final B minSize(final double WIDTH, final double HEIGHT) {
        properties.put("minSize", new SimpleObjectProperty<>(new Dimension2D(WIDTH, HEIGHT)));
        return (B)this;
    }
    public final B maxSize(final double WIDTH, final double HEIGHT) {
        properties.put("maxSize", new SimpleObjectProperty<>(new Dimension2D(WIDTH, HEIGHT)));
        return (B)this;
    }

    public final B prefWidth(final double PREF_WIDTH) {
        properties.put("prefWidth", new SimpleDoubleProperty(PREF_WIDTH));
        return (B)this;
    }
    public final B prefHeight(final double PREF_HEIGHT) {
        properties.put("prefHeight", new SimpleDoubleProperty(PREF_HEIGHT));
        return (B)this;
    }

    public final B minWidth(final double MIN_WIDTH) {
        properties.put("minWidth", new SimpleDoubleProperty(MIN_WIDTH));
        return (B)this;
    }
    public final B minHeight(final double MIN_HEIGHT) {
        properties.put("minHeight", new SimpleDoubleProperty(MIN_HEIGHT));
        return (B)this;
    }

    public final B maxWidth(final double MAX_WIDTH) {
        properties.put("maxWidth", new SimpleDoubleProperty(MAX_WIDTH));
        return (B)this;
    }
    public final B maxHeight(final double MAX_HEIGHT) {
        properties.put("maxHeight", new SimpleDoubleProperty(MAX_HEIGHT));
        return (B)this;
    }

    public final B scaleX(final double SCALE_X) {
        properties.put("scaleX", new SimpleDoubleProperty(SCALE_X));
        return (B)this;
    }
    public final B scaleY(final double SCALE_Y) {
        properties.put("scaleY", new SimpleDoubleProperty(SCALE_Y));
        return (B)this;
    }

    public final B layoutX(final double LAYOUT_X) {
        properties.put("layoutX", new SimpleDoubleProperty(LAYOUT_X));
        return (B)this;
    }
    public final B layoutY(final double LAYOUT_Y) {
        properties.put("layoutY", new SimpleDoubleProperty(LAYOUT_Y));
        return (B)this;
    }

    public final B translateX(final double TRANSLATE_X) {
        properties.put("translateX", new SimpleDoubleProperty(TRANSLATE_X));
        return (B)this;
    }
    public final B translateY(final double TRANSLATE_Y) {
        properties.put("translateY", new SimpleDoubleProperty(TRANSLATE_Y));
        return (B)this;
    }

    public final B padding(final Insets INSETS) {
        properties.put("padding", new SimpleObjectProperty<>(INSETS));
        return (B)this;
    }

    public final B date(final LocalDate date) {
        properties.put("date", new SimpleObjectProperty<>(date));
        return (B)this;
    }

    public final B backgroundFill(final Color backgroundFill) {
        properties.put("backgroundFill", new SimpleObjectProperty<>(backgroundFill));
        return (B)this;
    }

    public final B dayOfWeekBorder(final Color dayOfWeekBorder) {
        properties.put("dayOfWeekBorder", new SimpleObjectProperty<>(dayOfWeekBorder));
        return (B)this;
    }

    public final B dayOfWeekFill(final Color dayOfWeekFill) {
        properties.put("dayOfWeekFill", new SimpleObjectProperty<>(dayOfWeekFill));
        return (B)this;
    }

    public final B dayOfWeekTextFill(final Color dayOfWeekTextFill) {
        properties.put("dayOfWeekTextFill", new SimpleObjectProperty<>(dayOfWeekTextFill));
        return (B)this;
    }

    public final B weekOfYearBorder(final Color weekOfYearBorder) {
        properties.put("weekOfYearBorder", new SimpleObjectProperty<>(weekOfYearBorder));
        return (B)this;
    }

    public final B weekOfYearFill(final Color weekOfYearFill) {
        properties.put("weekOfYearFill", new SimpleObjectProperty<>(weekOfYearFill));
        return (B)this;
    }

    public final B weekOfYearTextFill(final Color weekOfYearTextFill) {
        properties.put("weekOfYearTextFill", new SimpleObjectProperty<>(weekOfYearTextFill));
        return (B)this;
    }

    public final B weekdayBorder(final Color weekdayBorder) {
        properties.put("weekdayBorder", new SimpleObjectProperty<>(weekdayBorder));
        return (B)this;
    }

    public final B weekdayFill(final Color weekdayFill) {
        properties.put("weekdayFill", new SimpleObjectProperty<>(weekdayFill));
        return (B)this;
    }

    public final B weekdayTextFill(final Color weekdayTextFill) {
        properties.put("weekdayTextFill", new SimpleObjectProperty<>(weekdayTextFill));
        return (B)this;
    }

    public final B saturdayBorder(final Color saturdayBorder) {
        properties.put("saturdayBorder", new SimpleObjectProperty<>(saturdayBorder));
        return (B)this;
    }

    public final B saturdayFill(final Color saturdayFill) {
        properties.put("saturdayFill", new SimpleObjectProperty<>(saturdayFill));
        return (B)this;
    }

    public final B saturdayTextFill(final Color saturdayTextFill) {
        properties.put("saturdayTextFill", new SimpleObjectProperty<>(saturdayTextFill));
        return (B)this;
    }

    public final B sundayBorder(final Color sundayBorder) {
        properties.put("sundayBorder", new SimpleObjectProperty<>(sundayBorder));
        return (B)this;
    }

    public final B sundayFill(final Color sundayFill) {
        properties.put("sundayFill", new SimpleObjectProperty<>(sundayFill));
        return (B)this;
    }

    public final B sundayTextFill(final Color sundayTextFill) {
        properties.put("sundayTextFill", new SimpleObjectProperty<>(sundayTextFill));
        return (B)this;
    }

    public final B todayBorder(final Color todayBorder) {
        properties.put("todayBorder", new SimpleObjectProperty<>(todayBorder));
        return (B)this;
    }

    public final B todayFill(final Color todayFill) {
        properties.put("todayFill", new SimpleObjectProperty<>(todayFill));
        return (B)this;
    }

    public final B todayTextFill(final Color todayTextFill) {
        properties.put("todayTextFill", new SimpleObjectProperty<>(todayTextFill));
        return (B)this;
    }

    public final B selectedBorder(final Color selectedBorder) {
        properties.put("selectedBorder", new SimpleObjectProperty<>(selectedBorder));
        return (B)this;
    }

    public final B selectedFill(final Color selectedFill) {
        properties.put("selectedFill", new SimpleObjectProperty<>(selectedFill));
        return (B)this;
    }

    public final B selectedTextFill(final Color selectedTextFill) {
        properties.put("selectedTextFill", new SimpleObjectProperty<>(selectedTextFill));
        return (B)this;
    }

    public final B startBorder(final Color startBorder) {
        properties.put("startBorder", new SimpleObjectProperty<>(startBorder));
        return (B)this;
    }

    public final B startFill(final Color startFill) {
        properties.put("startFill", new SimpleObjectProperty<>(startFill));
        return (B)this;
    }

    public final B startTextFill(final Color startTextFill) {
        properties.put("startTextFill", new SimpleObjectProperty<>(startTextFill));
        return (B)this;
    }

    public final B rangeBorder(final Color rangeBorder) {
        properties.put("rangeBorder", new SimpleObjectProperty<>(rangeBorder));
        return (B)this;
    }

    public final B rangeFill(final Color rangeFill) {
        properties.put("rangeFill", new SimpleObjectProperty<>(rangeFill));
        return (B)this;
    }

    public final B rangeTextFill(final Color rangeTextFill) {
        properties.put("rangeTextFill", new SimpleObjectProperty<>(rangeTextFill));
        return (B)this;
    }

    public final B endBorder(final Color endBorder) {
        properties.put("endBorder", new SimpleObjectProperty<>(endBorder));
        return (B)this;
    }

    public final B endFill(final Color endFill) {
        properties.put("endFill", new SimpleObjectProperty<>(endFill));
        return (B)this;
    }

    public final B endTextFill(final Color endTextFill) {
        properties.put("endTextFill", new SimpleObjectProperty<>(endTextFill));
        return (B)this;
    }

    public final B locale(final Locale locale) {
        properties.put("locale", new SimpleObjectProperty<>(locale));
        return (B)this;
    }

    public final B currentYear(final Year currentYear) {
        properties.put("currentYear", new SimpleObjectProperty<>(currentYear));
        return (B)this;
    }

    public final B currentMonth(final Month currentMonth) {
        properties.put("currentMonth", new SimpleObjectProperty<>(currentMonth));
        return (B)this;
    }

    public final B startDate(final LocalDate startDate) {
        properties.put("startDate", new SimpleObjectProperty<>(startDate));
        return (B)this;
    }

    public final B endDate(final LocalDate endDate) {
        properties.put("endDate", new SimpleObjectProperty<>(endDate));
        return (B)this;
    }


    public final DateRanger build() {
        final LocalDate  date       = properties.containsKey("date") ? ((ObjectProperty<LocalDate>) properties.get("date")).get() : LocalDate.now();
        final DateRanger dateRanger = new DateRanger(date);
        for (String key : properties.keySet()) {
            if ("prefSize".equals(key)) {
                Dimension2D dim = ((ObjectProperty<Dimension2D>) properties.get(key)).get();
                dateRanger.setPrefSize(dim.getWidth(), dim.getHeight());
            } else if("minSize".equals(key)) {
                Dimension2D dim = ((ObjectProperty<Dimension2D>) properties.get(key)).get();
                dateRanger.setMinSize(dim.getWidth(), dim.getHeight());
            } else if("maxSize".equals(key)) {
                Dimension2D dim = ((ObjectProperty<Dimension2D>) properties.get(key)).get();
                dateRanger.setMaxSize(dim.getWidth(), dim.getHeight());
            } else if("prefWidth".equals(key)) {
                dateRanger.setPrefWidth(((DoubleProperty) properties.get(key)).get());
            } else if("prefHeight".equals(key)) {
                dateRanger.setPrefHeight(((DoubleProperty) properties.get(key)).get());
            } else if("minWidth".equals(key)) {
                dateRanger.setMinWidth(((DoubleProperty) properties.get(key)).get());
            } else if("minHeight".equals(key)) {
                dateRanger.setMinHeight(((DoubleProperty) properties.get(key)).get());
            } else if("maxWidth".equals(key)) {
                dateRanger.setMaxWidth(((DoubleProperty) properties.get(key)).get());
            } else if("maxHeight".equals(key)) {
                dateRanger.setMaxHeight(((DoubleProperty) properties.get(key)).get());
            } else if("scaleX".equals(key)) {
                dateRanger.setScaleX(((DoubleProperty) properties.get(key)).get());
            } else if("scaleY".equals(key)) {
                dateRanger.setScaleY(((DoubleProperty) properties.get(key)).get());
            } else if ("layoutX".equals(key)) {
                dateRanger.setLayoutX(((DoubleProperty) properties.get(key)).get());
            } else if ("layoutY".equals(key)) {
                dateRanger.setLayoutY(((DoubleProperty) properties.get(key)).get());
            } else if ("translateX".equals(key)) {
                dateRanger.setTranslateX(((DoubleProperty) properties.get(key)).get());
            } else if ("translateY".equals(key)) {
                dateRanger.setTranslateY(((DoubleProperty) properties.get(key)).get());
            } else if ("padding".equals(key)) {
                dateRanger.setPadding(((ObjectProperty<Insets>) properties.get(key)).get());
            } else if("backgroundFill".equals(key)) {
                dateRanger.setBackgroundFill(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("dayOfWeekBorder".equals(key)) {
                dateRanger.setDayOfWeekBorder(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("dayOfWeekFill".equals(key)) {
                dateRanger.setDayOfWeekFill(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("dayOfWeekTextFill".equals(key)) {
                dateRanger.setDayOfWeekTextFill(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("weekOfYearBorder".equals(key)) {
                dateRanger.setWeekOfYearBorder(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("weekOfYearFill".equals(key)) {
                dateRanger.setWeekOfYearFill(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("weekOfYearTextFill".equals(key)) {
                dateRanger.setWeekOfYearTextFill(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("weekdayBorder".equals(key)) {
                dateRanger.setWeekdayBorder(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("weekdayFill".equals(key)) {
                dateRanger.setWeekdayFill(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("weekdayTextFill".equals(key)) {
                dateRanger.setWeekdayTextFill(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("saturdayBorder".equals(key)) {
                dateRanger.setSaturdayBorder(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("saturdayFill".equals(key)) {
                dateRanger.setSaturdayFill(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("saturdayTextFill".equals(key)) {
                dateRanger.setSaturdayTextFill(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("sundayBorder".equals(key)) {
                dateRanger.setSundayBorder(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("sundayFill".equals(key)) {
                dateRanger.setSundayFill(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("sundayTextFill".equals(key)) {
                dateRanger.setSundayTextFill(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("todayBorder".equals(key)) {
                dateRanger.setTodayBorder(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("todayFill".equals(key)) {
                dateRanger.setTodayFill(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("todayTextFill".equals(key)) {
                dateRanger.setTodayTextFill(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("selectedBorder".equals(key)) {
                dateRanger.setSelectedBorder(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("selectedfill".equals(key)) {
                dateRanger.setSelectedFill(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("selectedTextFill".equals(key)) {
                dateRanger.setSelectedTextFill(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("startBorder".equals(key)) {
                dateRanger.setStartBorder(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("startFill".equals(key)) {
                dateRanger.setStartFill(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("startTextFill".equals(key)) {
                dateRanger.setStartTextFill(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("rangeBorder".equals(key)) {
                dateRanger.setRangeBorder(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("rangeFill".equals(key)) {
                dateRanger.setRangeFill(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("rangeTextFill".equals(key)) {
                dateRanger.setRangeTextFill(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("endBorder".equals(key)) {
                dateRanger.setEndBorder(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("endFill".equals(key)) {
                dateRanger.setEndFill(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("endTextFill".equals(key)) {
                dateRanger.setEndTextFill(((ObjectProperty<Color>) properties.get(key)).get());
            } else if("locale".equals(key)) {
                dateRanger.setLocale(((ObjectProperty<Locale>) properties.get(key)).get());
            } else if("currentYear".equals(key)) {
                dateRanger.setCurrentYear(((ObjectProperty<Year>) properties.get(key)).get());
            } else if("currentMonth".equals(key)) {
                dateRanger.setCurrentMonth(((ObjectProperty<Month>) properties.get(key)).get());
            } else if("selectedDate".equals(key)) {
                dateRanger.setSelectedDate(((ObjectProperty<LocalDate>) properties.get(key)).get());
            } else if ("startDate".equals(key)) {
                dateRanger.setStartDate(((ObjectProperty<LocalDate>) properties.get(key)).get());
            } else if ("endDate".equals(key)) {
                dateRanger.setEndDate(((ObjectProperty<LocalDate>) properties.get(key)).get());
            }
        }
        return dateRanger;
    }
}
