package FunctionC_TomCatchJerry;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class InfoGUI extends Application {
    /**
     * Build the GUI for the Info page of the game.
     * Game rule is shown on this page.
     * @param stage Set the scene of the Info page of the maze game
     */
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();

        Rectangle box = new Rectangle(300, 300);
        box.setFill(Color.web("#e2d5bb"));
        box.setStroke(Color.web("#c08129bd"));
        box.setStrokeWidth(10);
        box.setArcHeight(5);
        box.setArcWidth(5);

        Label gameRuleLabel = new Label("GAME RULE");
        gameRuleLabel.setFont(new Font("Berlin Sans FB Demi Bold", 22));
        gameRuleLabel.setTextFill(Color.web("#1e4631"));
        gameRuleLabel.setLayoutX(87);
        gameRuleLabel.setLayoutY(26);

        Button okButton = new Button("OK!");
        okButton.setLayoutX(38);
        okButton.setLayoutY(249);

        ImageView tomImageView = new ImageView(new Image("file:tom.png"));
        tomImageView.setLayoutX(214);
        tomImageView.setLayoutY(249);
        tomImageView.setFitWidth(27);
        tomImageView.setFitHeight(27);

        ImageView jerryImageView = new ImageView(new Image("file:jerry.png"));
        jerryImageView.setLayoutX(134);
        jerryImageView.setLayoutY(249);
        jerryImageView.setFitWidth(27);
        jerryImageView.setFitHeight(27);

        Label donBeCaughtLabel = new Label("Don't Be Caught by TOM");
        donBeCaughtLabel.setFont(new Font("Berlin Sans FB", 12));
        donBeCaughtLabel.setTextFill(Color.web("#d52332"));
        donBeCaughtLabel.setLayoutX(29);
        donBeCaughtLabel.setLayoutY(59);

        Label jerryLabel = new Label("Jerry");
        jerryLabel.setFont(Font.font("Berlin Sans FB Demi Bold", 12));
        jerryLabel.setTextFill(Color.web("#c09129"));
        jerryLabel.setLayoutX(165);
        jerryLabel.setLayoutY(254);

        Label tomLabel = new Label("Tom");
        tomLabel.setFont(Font.font("Berlin Sans FB Demi Bold", 12));
        tomLabel.setTextFill(Color.web("#808990"));
        tomLabel.setLayoutX(245);
        tomLabel.setLayoutY(255);

        Label goToExitLabel = new Label("Go To The Exit Point");
        goToExitLabel.setFont(Font.font("Berlin Sans FB", 12));
        goToExitLabel.setTextFill(Color.web("#d52332"));
        goToExitLabel.setLayoutX(173);
        goToExitLabel.setLayoutY(59);

        Label andLabel = new Label("&");
        andLabel.setFont(Font.font("Berlin Sans FB", 12));
        andLabel.setTextFill(Color.web("#d9838b"));
        andLabel.setLayoutX(160);
        andLabel.setLayoutY(59);

        Font font = new Font("Berlin Sans FB Demi Bold", 12.0);

        Button wButton = new Button("W");
        wButton.setFont(font);
        wButton.setLayoutX(138);
        wButton.setLayoutY(103);
        wButton.setMinSize(27, 27);

        Button dButton = new Button("D");
        dButton.setFont(font);
        dButton.setLayoutX(181);
        dButton.setLayoutY(151);
        dButton.setMinSize(27, 27);

        Button sButton = new Button("S");
        sButton.setFont(font);
        sButton.setLayoutX(138);
        sButton.setLayoutY(151);
        sButton.setMinSize(27, 27);

        Button aButton = new Button("A");
        aButton.setFont(font);
        aButton.setLayoutX(95);
        aButton.setLayoutY(151);
        aButton.setMinSize(27, 27);

        Label upLabel = new Label("UP");
        upLabel.setFont(font);
        upLabel.setTextFill(Color.web("#040404"));
        upLabel.setLayoutX(144);
        upLabel.setLayoutY(133);

        Label leftLabel = new Label("LEFT");
        leftLabel.setFont(font);
        leftLabel.setTextFill(Color.web("#040404"));
        leftLabel.setLayoutX(93);
        leftLabel.setLayoutY(182);

        Label downLabel = new Label("DOWN");
        downLabel.setFont(font);
        downLabel.setTextFill(Color.web("#040404"));
        downLabel.setLayoutX(132);
        downLabel.setLayoutY(182);

        Label rightLabel = new Label("RIGHT");
        rightLabel.setFont(font);
        rightLabel.setTextFill(Color.web("#040404"));
        rightLabel.setLayoutX(181);
        rightLabel.setLayoutY(182);

        Label you = new Label("(You)");
        you.setFont(font);
        you.setTextFill(Color.web("#c09129"));
        you.setLayoutX(162);
        you.setLayoutY(267);

        Label comp = new Label("(Comp)");
        comp.setFont(font);
        comp.setTextFill(Color.web("#808990"));
        comp.setLayoutX(244);
        comp.setLayoutY(266);

        root.getChildren().addAll(box,gameRuleLabel,okButton,tomImageView,jerryImageView,donBeCaughtLabel,jerryLabel,
                tomLabel,goToExitLabel,andLabel,wButton,sButton,aButton,dButton,upLabel,leftLabel, rightLabel,downLabel,you,comp);

        okButton.setOnAction(actionEvent -> {
            MainGUI mainGUI = new MainGUI();
            try {
                mainGUI.start(stage);} catch (IOException e) {throw new RuntimeException(e);}
        });

        Scene startPage = new Scene(root,300,300);
        stage.setScene(startPage);
        stage.show();
    }

//    public static void main(String[] args) {
//        launch(args);
//    }
}

