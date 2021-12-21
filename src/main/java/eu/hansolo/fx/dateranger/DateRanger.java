package eu.hansolo.fx.dateranger;

import eu.hansolo.fx.dateranger.Records.DateCell;
import javafx.beans.DefaultProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.IntegerPropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


/**
 * User: hansolo
 * Date: 21.12.21
 * Time: 13:44
 */
@DefaultProperty("children")
public class DateRanger extends Region {
    private static final double                     PREFERRED_WIDTH  = 250;
    private static final double                     PREFERRED_HEIGHT = 250;
    private static final double                     MINIMUM_WIDTH    = 50;
    private static final double                     MINIMUM_HEIGHT   = 50;
    private static final double                     MAXIMUM_WIDTH    = 1024;
    private static final double                     MAXIMUM_HEIGHT   = 1024;
    private static final double                     H_SPACE          = 0;
    private static final double                     V_SPACE          = 0;
    private              String                     userAgentStyleSheet;
    private              double                     size;
    private              double                     width;
    private              double                     height;
    private              GridPane                   pane;
    private              Map<Month, List<DateCell>> cellMap;
    private              List<ColumnConstraints>    columnConstraints;
    private              List<RowConstraints>       rowConstraints;
    private              ObjectProperty<Locale>     locale;
    private              IntegerProperty            currentYear;
    private              ObjectProperty<Month>      currentMonth;
    private              ObjectProperty<LocalDate>  selectedDate;
    private              ObjectProperty<LocalDate>  startDate;
    private              ObjectProperty<LocalDate>  endDate;



    // ******************** Constructors **************************************
    public DateRanger() {
        LocalDate now = LocalDate.now();
        locale        = new ObjectPropertyBase<>(Locale.getDefault()) {
            @Override protected void invalidated() { updateCells(); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public String getName() { return "locale"; }
        };
        currentYear   = new IntegerPropertyBase(now.getYear()) {
            @Override protected void invalidated() { updateCells(); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public String getName() { return "currentYear"; }
        };
        currentMonth  = new ObjectPropertyBase<>(now.getMonth()) {
            @Override protected void invalidated() { redraw(); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public String getName() { return "currentMonth"; }
        };
        selectedDate  = new ObjectPropertyBase<>(now) {
            @Override protected void invalidated() { redraw(); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public String getName() { return "selectedDate"; }
        };
        startDate     = new ObjectPropertyBase<>() {
            @Override protected void invalidated() { redraw(); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public String getName() { return "startDate"; }
        };
        endDate       = new ObjectPropertyBase<>() {
            @Override protected void invalidated() { redraw(); }
            @Override public Object getBean() { return DateRanger.this; }
            @Override public String getName() { return "endDate"; }
        };

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
                dateLabel.setAlignment(Pos.CENTER);
                GridPane.setHalignment(dateLabel, HPos.CENTER);
                GridPane.setValignment(dateLabel, VPos.CENTER);
                GridPane.setFillWidth(dateLabel, true);
                GridPane.setFillHeight(dateLabel, true);
                pane.add(dateLabel, x, y);
            }
        }

        getChildren().setAll(pane);
    }

    private void registerListeners() {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
    }


    // ******************** Methods *******************************************
    @Override protected double computeMinWidth(final double height) { return MINIMUM_WIDTH; }
    @Override protected double computeMinHeight(final double width)  { return MINIMUM_HEIGHT; }
    @Override protected double computePrefWidth(final double height) { return super.computePrefWidth(height); }
    @Override protected double computePrefHeight(final double width) { return super.computePrefHeight(width); }
    @Override protected double computeMaxWidth(final double height)  { return MAXIMUM_WIDTH; }
    @Override protected double computeMaxHeight(final double width)  { return MAXIMUM_HEIGHT; }

    @Override public ObservableList<Node> getChildren()              { return super.getChildren(); }

    public Locale getLocale() { return locale.get(); }
    public void setLocale(final Locale locale) { this.locale.set(locale); }
    public ObjectProperty<Locale> localeProperty() { return locale; }

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

    private void updateCells() {
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            Optional<Node> optNode = getCellAt(dayOfWeek.getValue(), 0);
            if (optNode.isPresent()) {
                DateLabel dateLabel = (DateLabel) optNode.get();
                dateLabel.setText(dayOfWeek.getDisplayName(TextStyle.NARROW_STANDALONE, locale.get()));
            }
        }

        boolean isLeap = Year.isLeap(currentYear.get());
        for (Month month : Month.values()) {
            cellMap.get(month).clear();
            LocalDate date = LocalDate.of(currentYear.get(), month.getValue(), 1);
            int noOfDays = month.length(isLeap);
            int y = 1;
            for (int i = 1 ; i <= noOfDays ; i++) {
                DayOfWeek dayOfWeek = date.getDayOfWeek();
                if (dayOfWeek == DayOfWeek.MONDAY) { y++; }
                DateCell dateCell = new DateCell(date, dayOfWeek.getValue(), y);
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
        size   = width < height ? width : height;

        if (width > 0 && height > 0) {
            pane.setMaxSize(width, height);
            pane.setPrefSize(width, height);
            pane.relocate((getWidth() - width) * 0.5, (getHeight() - height) * 0.5);
            final double cellWidth  = (width - 7 * H_SPACE) / 8;
            final double cellHeight = (height - 6 * V_SPACE) / 7;
            for (int x = 0 ; x < 8 ; x++) { columnConstraints.get(x).setPrefWidth(cellWidth); }
            for (int y = 0 ; y < 7 ; y++) { rowConstraints.get(y).setPrefHeight(cellHeight); }
            final Font cellFont = Font.font(cellHeight * 0.65);
            pane.getChildren().stream().map(node -> (DateLabel) node).forEach(dateLabel -> {
                dateLabel.setPrefSize(cellWidth, cellHeight);
                dateLabel.setFont(cellFont);
            });

            redraw();
        }
    }

    private void redraw() {
        final LocalDate      now          = LocalDate.now();
        final LocalDate      startDate    = getStartDate();
        final LocalDate      endDate      = getEndDate();
        final boolean        isRange      = (null != startDate && null != endDate);
        final LocalDate      selectedDate = getSelectedDate();
        final List<DateCell> cells        = cellMap.get(currentMonth.get());
        cells.forEach(cell -> {
            Optional<Node> optNode = getCellAt(cell.x(), cell.y());
            if (optNode.isPresent()) {
                DateLabel dateLabel = (DateLabel) optNode.get();
                dateLabel.setSaturday(cell.getDayOfWeek().equals(DayOfWeek.SATURDAY));
                dateLabel.setSunday(cell.getDayOfWeek().equals(DayOfWeek.SUNDAY));
                dateLabel.setToday(cell.date().isEqual(now));
                if (null != startDate) { dateLabel.setStart(startDate.isEqual(cell.date())); }
                if (null != endDate)   { dateLabel.setEnd(endDate.isEqual(cell.date())); }
                if (isRange) { dateLabel.setRange(!cell.date().isBefore(startDate) && !cell.date().isAfter(endDate)); }
                dateLabel.setSelected(selectedDate.isEqual(cell.date()));
                dateLabel.setText(Integer.toString(cell.getDayOfMonth()));
            }
        });
    }
}