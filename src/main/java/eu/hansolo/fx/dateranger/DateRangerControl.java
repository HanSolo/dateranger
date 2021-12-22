package eu.hansolo.fx.dateranger;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.time.format.TextStyle;


public class DateRangerControl extends BorderPane {
    private Label      monthYearLabel;
    private Label      previousMonthButton;
    private Label      todayButton;
    private Label      nextMonthButton;
    private DateRanger dateRanger;


    public DateRangerControl() {
        super();
        init(new DateRanger());
    }
    public DateRangerControl(final DateRanger dateRanger) {
        super();
        init(dateRanger);
    }
    public DateRangerControl(final Node center, final Node top, final Node right, final Node bottom, final Node left) {
        super();
        init(new DateRanger());
    }
    public DateRangerControl(final LocalDate date) {
        super();
        init(new DateRanger(date));
    }


    private void init(final DateRanger dateRanger) {
        this.dateRanger = dateRanger;

        this.monthYearLabel = new Label(dateRanger.getCurrentMonth().getDisplayName(TextStyle.FULL, dateRanger.getLocale()) + " " + dateRanger.getCurrentYear());
        this.monthYearLabel.setMaxWidth(Double.MAX_VALUE);
        this.monthYearLabel.setAlignment(Pos.CENTER_LEFT);
        this.monthYearLabel.getStyleClass().addAll("date-ranger-control", "header");
        HBox.setHgrow(this.monthYearLabel, Priority.SOMETIMES);

        this.previousMonthButton = new Label("<");
        this.previousMonthButton.getStyleClass().addAll("date-ranger-control", "header-button");
        HBox.setHgrow(this.previousMonthButton, Priority.NEVER);

        this.todayButton = new Label(Integer.toString(LocalDate.now().getDayOfMonth()));
        this.todayButton.getStyleClass().addAll("date-ranger-control", "header-today-button");
        HBox.setHgrow(this.todayButton, Priority.NEVER);

        this.nextMonthButton = new Label(">");
        this.nextMonthButton.getStyleClass().addAll("date-ranger-control", "header-button");
        HBox.setHgrow(this.nextMonthButton, Priority.NEVER);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        getStylesheets().add(DateRangerControl.class.getResource("date-ranger-control.css").toExternalForm());

        HBox headerBox = new HBox(5, monthYearLabel, spacer, previousMonthButton, todayButton, nextMonthButton);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setFillHeight(true);

        getStyleClass().add("date-ranger-control");

        setTop(headerBox);
        setCenter(dateRanger);

        registerListener();
    }


    private void registerListener() {
        widthProperty().addListener(e -> resize());
        heightProperty().addListener(e -> resize());
        setOnMousePressed(e -> dateRanger.onMousePressed(e));
        dateRanger.selectedDateProperty().addListener((o, ov, nv) -> monthYearLabel.setText(dateRanger.getCurrentMonth().getDisplayName(TextStyle.FULL, dateRanger.getLocale()) + " " + dateRanger.getCurrentYear()));
        dateRanger.currentYearProperty().addListener((o, ov, nv) -> monthYearLabel.setText(dateRanger.getCurrentMonth().getDisplayName(TextStyle.FULL, dateRanger.getLocale()) + " " + dateRanger.getCurrentYear()));
        dateRanger.currentMonthProperty().addListener((o, ov, nv) -> monthYearLabel.setText(dateRanger.getCurrentMonth().getDisplayName(TextStyle.FULL, dateRanger.getLocale()) + " " + dateRanger.getCurrentYear()));
        previousMonthButton.setOnMousePressed(e -> dateRanger.setCurrentMonth(dateRanger.getCurrentMonth().minus(1)));
        nextMonthButton.setOnMousePressed(e -> dateRanger.setCurrentMonth(dateRanger.getCurrentMonth().plus(1)));
        todayButton.setOnMousePressed(e -> dateRanger.setCurrentDate(LocalDate.now()));
    }

    private void resize() {
        double width  = getWidth() - getInsets().getLeft() - getInsets().getRight();
        double height = getHeight() - getInsets().getTop() - getInsets().getBottom();

        if (width > 0 && height > 0) {
            final Font headerFont      = Font.font(height / 7 * 0.65);
            final Font headerTodayFont = Font.font(height / 14 * 0.5);
            monthYearLabel.setFont(headerFont);
            previousMonthButton.setFont(headerFont);
            todayButton.setFont(headerTodayFont);
            nextMonthButton.setFont(headerFont);
        }
    }

}
