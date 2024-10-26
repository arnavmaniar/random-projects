module edu.ncssm.food.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires kotlin.stdlib;

    opens edu.ncssm.food.tictactoe to javafx.fxml;
    exports edu.ncssm.food.tictactoe;
}