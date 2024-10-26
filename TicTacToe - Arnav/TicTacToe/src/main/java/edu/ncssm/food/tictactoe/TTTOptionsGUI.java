package edu.ncssm.food.tictactoe;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * @author arnav m
 * @date 8/22/2022
 *
 * The Options Screen for Tic Tac Toe
 *   -- change number of rows/columns
 *   -- change name of players
 *   -- change images used when playing
 */
public class TTTOptionsGUI extends BorderPane {

    private Slider dimensions;

    /**
     * Create the Options GUI for Tic Tac Toe
     */
    public TTTOptionsGUI(){

        // Rows/Column slider
        dimensions = new Slider(1, 10, 3);
        dimensions.setMajorTickUnit(1);
        dimensions.setShowTickLabels(true);
        dimensions.setSnapToTicks(true);
        dimensions.setPadding(new Insets(5,5,5,5));

        // Keep the slider on an integer (not half rows!)
        dimensions.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> o, Number oldVal, Number newVal) {

                // Round to nearest integer
                int actualVal = (int)(newVal.doubleValue()+0.5);
                dimensions.setValue(actualVal);
            }
        });

        VBox centerScreen = new VBox();
        centerScreen.getChildren().addAll( createPlayers(), dimensions);
        setCenter(centerScreen);
        setBottom(createStatus());

        this.layout();
    }

    // Create eh row of players graphically
    private HBox createPlayers(){
        HBox players = new HBox();
        String[] names = { "X", "O"};           // Default names if not picture is set

        for( int i = 0; i < names.length; i++ ){
            PlayerGUI player = new PlayerGUI();
            player.setName(names[i]);
            HBox.setHgrow(player, Priority.ALWAYS);
            players.getChildren().add(player);
        }


        return players;
    }

    // Create the graphical top status bar
    private HBox createStatus(){
        HBox status = new HBox();
        status.setPadding(new Insets(5,5,5,5));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Button play = new Button("Play Game");
        status.getChildren().addAll(spacer, play);

        // When the Play button is clicked move the Tic Tac Toe graphic
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                // Get the value off the slider
                int dim = (int)dimensions.getValue();

                TicTacToeGUI game = new TicTacToeGUI(dim);

                // Change the scene to the Tic Tac Toe board
                ((Stage)getScene().getWindow()).setScene(new Scene(game, 500,500));
            }
        });

        return status;
    }
}
