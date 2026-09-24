module week4uilab.week4uilab {
    requires javafx.controls;
    requires javafx.fxml;


    opens week4uilab.week4uilab to javafx.fxml;
    exports week4uilab.week4uilab;
}