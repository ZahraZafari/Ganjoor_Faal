module GanjoorApp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
	requires java.json;

    exports ganjoor to javafx.graphics;
    opens ganjoor to javafx.fxml;
}
