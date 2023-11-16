module tomandjerryinmazegame.tomandjerryinmazegame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires javafx.media;

    opens tomandjerryinmazegame.tomandjerryinmazegame to javafx.fxml;
    exports FunctionA_CreateMaze;
    exports FunctionB_ShortestPath to javafx.graphics;
    exports FunctionC_TomCatchJerry;
    exports Main;
}