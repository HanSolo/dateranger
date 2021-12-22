package eu.hansolo.fx.dateranger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Locale;


public class Demo extends Application {
    private DateRanger        dateRanger;
    private DateRangerControl dateRangerControl;

    @Override public void init() {
        dateRanger = DateRangerBuilder.create()
                                      .locale(Locale.GERMAN)
                                      .build();

        dateRangerControl = new DateRangerControl(dateRanger);
    }

    private void initOnFxApplicationThread(final Stage stage) {

        registerListeners();
    }

    private void registerListeners() {

    }

    @Override public void start(final Stage stage) {
        initOnFxApplicationThread(stage);

        StackPane pane = new StackPane(dateRangerControl);
        pane.setPrefSize(400, 400);
        //pane.setPadding(new Insets(10));

        Scene scene = new Scene(pane);

        stage.setTitle("DateRanger");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    @Override public void stop() {
        // Remove event handlers


        // Shutdown
        Platform.exit();
        System.exit(0);
    }


    // ******************** Launching *******************************
    public static void main(final String[] args) {
        launch(args);
    }
}
