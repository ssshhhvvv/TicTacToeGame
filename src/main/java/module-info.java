module com.example.tictactoegame {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.tictactoegame.application to javafx.fxml;
    exports com.example.tictactoegame.application;
    exports com.example.tictactoegame.controllers;
    opens com.example.tictactoegame.controllers to javafx.fxml;
}