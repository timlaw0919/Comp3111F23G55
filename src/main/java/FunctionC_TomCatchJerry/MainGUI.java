package FunctionC_TomCatchJerry;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import static FunctionC_TomCatchJerry.GameMain.*;
import java.io.IOException;

import static FunctionC_TomCatchJerry.GameMazeGUI.*;
public class MainGUI extends Application {
    private static final String BACKGROUND_IMAGE_URL = "https://themepack.me/i/c/749x467/media/g/221/tom-jerry-theme-7.jpg";
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("start.fxml"));
        Image jerry = new Image("file:Jerry.png");
        ImageView jerry_view = new ImageView(jerry);
        jerry_view.setFitHeight(146);
        jerry_view.setFitWidth(238);
        jerry_view.setLayoutX(132);
        jerry_view.setLayoutY(160);

        Button start = new Button("Start");
        start.setLayoutX(129);
        start.setLayoutY(208);

        Slider mazeLength = new Slider();
        mazeLength.setMin(30);
        mazeLength.setMax(60);
        mazeLength.setBlockIncrement(10);
        mazeLength.setLayoutX(132);
        mazeLength.setLayoutY(136);
        Label title = new Label("Tom and Jerry");
        title.setLayoutX(63);
        title.setLayoutY(36);
        title.setFont(new Font("Bauhaus 93",27.0));
        Pane pane = new Pane();
        pane.getChildren().addAll(jerry_view,start,mazeLength,title);

//        mazeLength.setOnDragDone(dragEvent -> {
//            dragEvent.getPickResult().getIntersectedDistance();
//
//        });
//        Button start = new Button("Start");
        start.setOnAction(actionEvent -> {
            int mazeSize = (int)mazeLength.getValue();
            maze = newMaze(mazeSize);
            GameMazeGUI gameMazeGUI = new GameMazeGUI();
            gameMazeGUI.start(stage);
        });

        Scene startPage = new Scene(pane,300,300);
        stage.setScene(startPage);
        stage.show();
    }
}
