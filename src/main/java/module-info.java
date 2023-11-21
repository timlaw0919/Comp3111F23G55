module tomandjerryinmazegame.tomandjerryinmazegame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires javafx.media;
    requires org.testfx;

    opens tomandjerryinmazegame.tomandjerryinmazegame to javafx.fxml;
    exports FunctionA_CreateMaze;
    exports FunctionA_CreateMaze.constant;
    exports FunctionB_ShortestPath;
    exports FunctionC_TomCatchJerry;
    exports Main;
}