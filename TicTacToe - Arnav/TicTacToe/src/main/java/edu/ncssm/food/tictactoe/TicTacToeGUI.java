package edu.ncssm.food.tictactoe;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


/**
 * @author arnav m
 * @date 8/22/2022
 *
 * The GUI used to represent the Logic
 */

public class TicTacToeGUI extends BorderPane {

    // the gmae itself
    private TicTacToeLogic logic;

    // Top status bar
    private Label status;

    // Graphical buttons on the board for easy update
    private Button[] board;

    /**
     * Creates the graphical component of Tic Tac Toe
     * @param dimension number of rows/columns in the board
     */
    public TicTacToeGUI(int dimension){

        logic = new TicTacToeLogic(dimension);

        // Reset the dimension in case we were given an invalid one
        dimension = logic.getDimension();


        board = new Button[dimension*dimension];
        HBox optionBar = createOptions();
        GridPane board = createBoard();

        this.setTop(optionBar);
        this.setCenter(board);

        updateBoard();
    }

    // Update the graphical spaces, status bar, and winner
    private void updateBoard(){

        // Update buttons
        for( int spot = 0; spot < board.length; spot++ ){
            String player = logic.getOwner(spot);
            if( player.isEmpty() ){
                player = " ";
            }
            board[spot].setText(player);
        }

        // Update the status bar (turn or winner)
        String statusText = logic.whoseTurn() + "\'s Turn";
        String winner = logic.checkWinner();

        if( !winner.isEmpty() ){
            statusText = logic.whoseTurn() + " Wins!";
            if( winner.equals("TIE") ){
                statusText = "It\'s a tie.";
            }
        }
        status.setText(statusText);
    }

    // Create the top status bar
    private HBox createOptions(){

        Font font = new Font(14);
        HBox rtn = new HBox();

        Button back = new Button("Back");
        Button reset = new Button( "Reset" );
        status = new Label();

        back.setFont(font);
        reset.setFont(font);
        status.setFont(font);

        // When reset is clicked, reset the logic and update the board
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                logic.reset();
                updateBoard();
            }
        });

        Region leftSpacer = new Region();
        Region rightSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        rtn.getChildren().addAll(back,leftSpacer, status, rightSpacer, reset);

        Insets insets = new Insets(5,5,5,5);
        rtn.setPadding(insets);

        return rtn;
    }

    // Create the graphical board
    private GridPane createBoard(){
        GridPane board = new GridPane();

        final int ROW_GAP = 10;

        board.setHgap(ROW_GAP);
        board.setVgap(ROW_GAP);
        board.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        int dim = logic.getDimension();

        // Add the rows and columns
        for( int i = 0; i < dim; i++ ){
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100.0/dim);

            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100.0/dim);

            board.getColumnConstraints().add(col);
            board.getRowConstraints().add(row);
        }

        // Create and add the buttons to the board
        for( int row = 0; row < dim; row++ ){
            for( int col = 0; col < dim; col++ ){
                int spotNum = row * dim + col;

                Button space = new Button(""+spotNum);
                space.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                // Add a marker to ID the button
                space.setUserData(""+spotNum);
                this.board[spotNum] = space;

                // Resize the font when the window is resized
                space.heightProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
                        space.setStyle("-fx-font-size:"+(int)(0.45*newVal.doubleValue())+";");
                    }
                });

                GridPane.setVgrow(space, Priority.ALWAYS);
                GridPane.setHgrow(space, Priority.ALWAYS);

                board.add(space, col, row);

                // When a button is clicked, make a move!
                space.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        // Grab the ID off the button
                        Button source = (Button) actionEvent.getSource();
                        int spotNum = Integer.parseInt((String)source.getUserData());

                        logic.makeMove(spotNum);
                        updateBoard();
                    }
                });
            }
        }
        return board;
    }

}
