package edu.ncssm.food.tictactoe;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * @author arnav m
 * @date 8/22/2022
 *
 * The graphical component of each player
 */
public class PlayerGUI extends BorderPane {

    private String name;
    private ImageView picture;

    // Allow editing of the name
    private TextField nameField;

    // Allow editing of the picture used by the player
    private Button imageButton;

    /**
     * Create a default player GUI.  The name is blank.
     */
    public PlayerGUI(){
        Label nameLabel = new Label("Name:");
        nameLabel.setPadding(new Insets(5,5,5,5));

        nameField = new TextField();

        HBox nameBox = new HBox();
        nameBox.setAlignment(Pos.CENTER_LEFT);
        nameBox.getChildren().addAll(nameLabel, nameField);

        imageButton = new Button("Add Picture");
        imageButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        imageButton.setContentDisplay(ContentDisplay.TOP);

        picture = new ImageView(getClass().getResource("/camera.png").toString());
        picture.fitWidthProperty().bind(this.widthProperty().divide(2));
        picture.fitHeightProperty().bind(picture.fitWidthProperty());
        imageButton.setGraphic(picture);

        setTop(nameBox);
        setCenter(imageButton);

        this.setPadding(new Insets(5,5,5,5));
    }

    /**
     * Get the name of the current player
     * @return the name of the player
     */
    public String getName(){
        return name;
    }

    /**
     * Change the name of the player
     * @param n the new name; cannot be empty or null
     * @return true of the name is changed, false otherwise
     */
    public boolean setName(String n){
        if( n != null && !n.isEmpty()){
            name = n;
            nameField.setText(n);
            return true;
        }
        return false;
    }
}
