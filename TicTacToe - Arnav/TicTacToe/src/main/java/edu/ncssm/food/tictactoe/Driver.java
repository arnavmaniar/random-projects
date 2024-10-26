package edu.ncssm.food.tictactoe;

/**
 * @author arnav m
 * @date 8/25/2022
 *
 * A generic Tic Tac Toe game using JavaFX.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Driver extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        TTTOptionsGUI gui = new TTTOptionsGUI();

        Scene sc = new Scene(gui, 500,500);
        stage.setScene(sc);

        stage.show();
    }

    public static void main(String[] args ){
        launch(args);
    }
}
