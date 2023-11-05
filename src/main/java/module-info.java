module tomandjerryinmazegame.tomandjerryinmazegame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens tomandjerryinmazegame.tomandjerryinmazegame to javafx.fxml;
    exports FunctionA_CreateMaze;
}