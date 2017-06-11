/*
 * Programmer: Stoyanov, Maksim
 * Chemeketa Community College
 * May 17, 2017
 * Class: CIS233J
 * Assignment: Final project
 * File Name: ShutTheBox.java
 */
package ShutTheBox;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.ImageIcon;

/**
 * This Program is designed to simulate a popular board game named 'Shut The
 * Box' The game rules are described in some comments and in the game itself.
 * The main goal is to have the smallest score.
 *
 * @author <a href= "mailto:mstoyano@my.chemeketa.edu" >Maksim Stoyanov</a>
 */
public class ShutTheBox extends Application
{
    // calling die globally
    Die die1;
    Die die2;
    ImageIcon image;
    // file name and directory constants of die
    private final String FILE_PREFIX = "images/die";
    private final String FILE_SUFFIX = ".png";

    // setting die1 and die 2 Imageviews
    ImageView die1Label = new ImageView();
    ImageView die2Label = new ImageView();

    // default score is 45, changable value.
    public static int score1 = 45, score2 = 45;

    // 2 labels for both players, and a volitile score
    public static Label Lscore1 = new Label("SCORE: " + score1);
    public static Label Lscore2 = new Label("SCORE: " + score2);

    // sound directory for auto files
    public static String soundDir;

    // meant for searching for importing image
    private Desktop desktop = Desktop.getDesktop();
    final FileChooser fileChooser = new FileChooser();

    //theme changer! :) These strings will be called once a subMenu button is clicked
    public static String defaultT = "default.css";
    public static String spaceT = "space.css";
    public static String boringT = "boring.css";

    //this will help rename the file for dieLabel (starts null because file name is die.png,
    //will be receieving an appended string "space" meaning dieSpace.png
    public static String dieTheme = "";

    @Override
    public void start(Stage primaryStage)
    {
        //found stackpane to be best choice
        BorderPane invRoot = new BorderPane();

        StackPane root = new StackPane();
        root.setAlignment(Pos.TOP_CENTER);

        TextInputDialog dialog = new TextInputDialog("Shut the Box");
        dialog.setTitle("Name the Game");
        dialog.setHeaderText("Naming your game");
        dialog.setContentText("Name the Game");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Name of the Game");
            alert.setHeaderText(null);
            primaryStage.setTitle(result.get());

        }

        /**
         * ************Introduction Label and button(s)↓**********************
         */
        Label intro = new Label("Welcome! Today we're going to play a game of\n"
                + "Shut-The-Box. Please press the Rules button or\n"
                + "the Objectives button to get the gist of the game!");
        intro.getStyleClass().add("label1");
        intro.setTranslateX(-110);
        intro.setTranslateY(190);

        Button rulesBut = new Button("Rules    ");
        rulesBut.setTooltip(new Tooltip("Will display the rules of the game!"));
        rulesBut.setTranslateX(380);
        rulesBut.setTranslateY(280);
        rulesBut.getStyleClass().add("button2");
        rulesBut.setOnAction((ActionEvent e)-> 
        {
            soundDir = "src/tap.mp3";
            sound();
            intro.setVisible(true);
            intro.setText("Rules: \n"
            + "Each player is given two die. Once the sum \n"
            + "of both dice is calculated the player who rolled it \n"
            + "has to 'check-off' one to many of their nine tiles. \n"
            + "The tile(s) must add up to the sum rolled, or else you \n"
            + "lose your turn. Once you lose the ability to have a turn, \n"
            + "you lose the ability to lower your score therefore you wait \n"
            + "until your opponent loses their ability to roll \n"
            + "and then see who wins the game.");
        });

        Button objectivesBut = new Button("Objective    ");
        objectivesBut.setTooltip(new Tooltip(
                "Will display the objectives of the game!"));
        objectivesBut.setTranslateX(380);
        objectivesBut.setTranslateY(325);
        objectivesBut.getStyleClass().add("button2");
        objectivesBut.setOnAction((ActionEvent e)-> 
        {
            soundDir = "src/tap.mp3";
            sound();
            intro.setVisible(true);
            intro.setText("Objective: \n"
            + "The objective of the game is almost like golf; \n"
            + "get the smallest amount of points from the sum \n"
            + "of your tiles. The player with the least amount \n"
            + "at the end of the game wins.");
        });

        /**
         * ****************************************
         * Player 1 ↓ buttons
         */
        Button one = new Button("1");
        one.getStyleClass().add("button1");
        one.setTranslateX(-365);
        one.setDisable(true);
        one.setFont(Font.font("Cambria", 32));
        one.setOnAction((ActionEvent s)-> 
        {
            soundDir = "src/tap.mp3";
            sound();
            one.setRotate(180);
            score1 = score1 - 1;
            scoreUpdate();
        });

        Button two = new Button("2");
        two.getStyleClass().add("button1");
        two.setTranslateX(-300);
        two.setDisable(true);
        two.setFont(Font.font("Cambria", 32));
        two.setOnAction((ActionEvent s)-> 
        {
            soundDir = "src/tap.mp3";
            sound();
            two.setRotate(180);
            score1 = score1 - 2;
            scoreUpdate();
        });

        Button thr = new Button("3");
        thr.getStyleClass().add("button1");
        thr.setTranslateX(-235);
        thr.setDisable(true);
        thr.setFont(Font.font("Cambria", 32));
        thr.setOnAction((ActionEvent s)-> 
        {
            soundDir = "src/tap.mp3";
            sound();
            thr.setRotate(180);
            score1 = score1 - 3;
            scoreUpdate();
        });

        Button fou = new Button("4");
        fou.getStyleClass().add("button1");
        fou.setTranslateX(-170);
        fou.setDisable(true);
        fou.setFont(Font.font("Cambria", 32));
        fou.setOnAction((ActionEvent s)-> 
        {
            soundDir = "src/tap.mp3";
            sound();
            fou.setRotate(180);
            score1 = score1 - 4;
            scoreUpdate();
        });

        Button fiv = new Button("5");
        fiv.getStyleClass().add("button1");
        fiv.setTranslateX(-105);
        fiv.setDisable(true);
        fiv.setFont(Font.font("Cambria", 32));
        fiv.setOnAction((ActionEvent s)-> 
        {
            soundDir = "src/tap.mp3";
            sound();
            fiv.setRotate(180);
            score1 = score1 - 5;
            scoreUpdate();
        });

        Button six = new Button("6");
        six.getStyleClass().add("button1");
        six.setTranslateX(-40);
        six.setDisable(true);
        six.setFont(Font.font("Cambria", 32));
        six.setOnAction((ActionEvent s)-> 
        {
            soundDir = "src/tap.mp3";
            sound();
            six.setRotate(180);
            score1 = score1 - 6;
            scoreUpdate();
        });

        Button sev = new Button("7");
        sev.getStyleClass().add("button1");
        sev.setTranslateX(25);
        sev.setDisable(true);
        sev.setFont(Font.font("Cambria", 32));
        sev.setOnAction((ActionEvent s)-> 
        {
            soundDir = "src/tap.mp3";
            sound();
            sev.setRotate(180);
            score1 = score1 - 7;
            scoreUpdate();
        });

        Button eig = new Button("8");
        eig.getStyleClass().add("button1");
        eig.setTranslateX(90);
        eig.setDisable(true);
        eig.setFont(Font.font("Cambria", 32));
        eig.setOnAction((ActionEvent s)-> 
        {
            soundDir = "src/tap.mp3";
            sound();
            eig.setRotate(180);
            score1 = score1 - 8;
            scoreUpdate();
        });

        Button nin = new Button("9");
        nin.getStyleClass().add("button1");
        nin.setTranslateX(155);
        nin.setDisable(true);
        nin.setFont(Font.font("Cambria", 32));
        nin.setOnAction((ActionEvent s)-> 
        {
            soundDir = "src/tap.mp3";
            sound();
            nin.setRotate(180);
            score1 = score1 - 9;
            scoreUpdate();
        });

        /**
         * ******* end player one ↑ ******************************************
         */
        /**
         * ************ player 2 ↓ buttons **********************************
         */
        Button one2 = new Button("1");
        one2.getStyleClass().add("button3");
        one2.setTranslateX(-365);
        one2.setDisable(true);
        one2.setFont(Font.font("Cambria", 32));
        one2.setOnAction((ActionEvent s)-> 
        {
            sound();
            one2.setRotate(180);
            score2 = score2 - 1;
            scoreUpdate();
        });

        Button two2 = new Button("2");
        two2.getStyleClass().add("button3");
        two2.setTranslateX(-300);
        two2.setDisable(true);
        two2.setFont(Font.font("Cambria", 32));
        two2.setOnAction((ActionEvent s)-> 
        {
            soundDir = "src/tap.mp3";
            sound();
            two2.setRotate(180);
            score2 = score2 - 2;
            scoreUpdate();
        });

        Button thr2 = new Button("3");
        thr2.getStyleClass().add("button3");
        thr2.setTranslateX(-235);
        thr2.setDisable(true);
        thr2.setFont(Font.font("Cambria", 32));
        thr2.setOnAction((ActionEvent s)-> 
        {
            soundDir = "src/tap.mp3";
            sound();
            thr2.setRotate(180);
            score2 = score2 - 3;
            scoreUpdate();
        });

        Button fou2 = new Button("4");
        fou2.getStyleClass().add("button3");
        fou2.setTranslateX(-170);
        fou2.setDisable(true);
        fou2.setFont(Font.font("Cambria", 32));
        fou2.setOnAction((ActionEvent s)-> 
        {
            soundDir = "src/tap.mp3";
            sound();
            fou2.setRotate(180);
            score2 = score2 - 4;
            scoreUpdate();
        });

        Button fiv2 = new Button("5");
        fiv2.getStyleClass().add("button3");
        fiv2.setTranslateX(-105);
        fiv2.setDisable(true);
        fiv2.setFont(Font.font("Cambria", 32));
        fiv2.setOnAction((ActionEvent s)-> 
        {
            soundDir = "src/tap.mp3";
            sound();
            fiv2.setRotate(180);
            score2 = score2 - 5;
            scoreUpdate();
        });

        Button six2 = new Button("6");
        six2.getStyleClass().add("button3");
        six2.setTranslateX(-40);
        six2.setDisable(true);
        six2.setFont(Font.font("Cambria", 32));
        six2.setOnAction((ActionEvent s)-> 
        {
            soundDir = "src/tap.mp3";
            sound();
            six2.setRotate(180);
            score2 = score2 - 6;
            scoreUpdate();
        });

        Button sev2 = new Button("7");
        sev2.getStyleClass().add("button3");
        sev2.setTranslateX(25);
        sev2.setDisable(true);
        sev2.setFont(Font.font("Cambria", 32));
        sev2.setOnAction((ActionEvent s)-> 
        {
            soundDir = "src/tap.mp3";
            sound();
            sev2.setRotate(180);
            score2 = score2 - 7;
            scoreUpdate();
        });

        Button eig2 = new Button("8");
        eig2.getStyleClass().add("button3");
        eig2.setTranslateX(90);
        eig2.setDisable(true);
        eig2.setFont(Font.font("Cambria", 32));
        eig2.setOnAction((ActionEvent s)-> 
        {
            soundDir = "src/tap.mp3";
            sound();
            eig2.setRotate(180);
            score2 = score2 - 8;
            scoreUpdate();
        });

        Button nin2 = new Button("9");
        nin2.getStyleClass().add("button3");
        nin2.setTranslateX(155);
        nin2.setDisable(true);
        nin2.setFont(Font.font("Cambria", 32));
        nin2.setOnAction((ActionEvent s)-> 
        {
            soundDir = "src/tap.mp3";
            sound();
            nin2.setRotate(180);
            score2 = score2 - 9;
            scoreUpdate();
        });

        /**
         * ********* player 2 end ↑ *****************************************
         */
        /**
         * ************* separator begins **************
         */
        Separator separator1 = new Separator();
        separator1.setOrientation(Orientation.VERTICAL);
        separator1.setTranslateX(192);

        Separator separator2 = new Separator();
        separator2.setOrientation(Orientation.HORIZONTAL);
        separator2.setTranslateY(190);

        Separator separator3 = new Separator();
        separator3.setOrientation(Orientation.HORIZONTAL);
        separator3.setTranslateY(430);

        Lscore1.setTooltip(new Tooltip("Lowest score loses.\n"
                + "This is the sum of your available tiles!"));
        Lscore1.getStyleClass().add("label2");
        Lscore1.setDisable(true);
        Lscore1.setTranslateX(280);
        Lscore1.setTranslateY(145);
        Lscore1.setFont(Font.font("Cambria", 32));

        Lscore2.getStyleClass().add("label2");
        Lscore2.setDisable(true);
        Lscore2.setFont(Font.font("Cambria", 32));
        Lscore2.setTranslateX(280);
        Lscore2.setTranslateY(430);

        /* separator ends
         */
        /**
         * **********************************************************
         * Dice Positioning *************and rolling*****************
         */
        Button roll = new Button("Let's Roll!");
        roll.setTooltip(new Tooltip("Rolls your die!"));
        roll.getStyleClass().add("button2");
        roll.setDisable(true);
        roll.setTranslateX(120);
        roll.setTranslateY(390);
        roll.setOnAction((ActionEvent e)-> 
        {
            soundDir = "src/roll.mp3";
            sound();
            rollDie();
        });

        die1Label.setTranslateX(-300);
        die1Label.setTranslateY(320);
        die1Label.setVisible(true);

        die2Label.setTranslateX(80);
        die2Label.setTranslateY(220);
        die2Label.setVisible(true);
        die2Label.getStyleClass().add("translation");
        /*
**end Dice Positioning**************************************
* **********************************************************/

        Button restart = new Button("Restart    ");
        restart.setTooltip(new Tooltip("This will clear your game, be weary!"));
        restart.setDisable(true);
        restart.setTranslateX(380);
        restart.setTranslateY(240);
        restart.getStyleClass().add("button2");
        restart.setOnAction((ActionEvent e)-> 
        {
            soundDir = "src/swish.mp3";
            sound();
            Lscore1.setDisable(true);
            one.setDisable(true);
            one.setRotate(360);
            two.setDisable(true);
            two.setRotate(360);
            thr.setDisable(true);
            thr.setRotate(360);
            fou.setDisable(true);
            fou.setRotate(360);
            fiv.setDisable(true);
            fiv.setRotate(360);
            six.setDisable(true);
            six.setRotate(360);
            sev.setDisable(true);
            sev.setRotate(360);
            eig.setDisable(true);
            eig.setRotate(360);
            nin.setDisable(true);
            nin.setRotate(360);
            score1 = 45;
            score2 = 45;

            Lscore2.setDisable(true);
            one2.setDisable(true);
            one2.setRotate(360);
            two2.setDisable(true);
            two2.setRotate(360);
            thr2.setDisable(true);
            thr2.setRotate(360);
            fou2.setDisable(true);
            fou2.setRotate(360);
            fiv2.setDisable(true);
            fiv2.setRotate(360);
            six2.setDisable(true);
            six2.setRotate(360);
            sev2.setDisable(true);
            sev2.setRotate(360);
            eig2.setDisable(true);
            eig2.setRotate(360);
            nin2.setDisable(true);
            nin2.setRotate(360);
            scoreUpdate();
            rulesBut.setDisable(false);
            objectivesBut.setDisable(false);
            roll.setDisable(true);
            restart.setDisable(true);
            clearTable();
        });
//end restart button        

        /* Start Button!
         */ Button start = new Button("Begin Playing   ");
        start.setTooltip(new Tooltip("This enables you to roll the die\n"
                + "and flip tiles!"));
        start.setTranslateX(360);
        start.setTranslateY(195);
        start.getStyleClass().add("button2");
        start.setOnAction((ActionEvent e)-> 
        {
            soundDir = "src/tap.mp3";
            sound();
            Lscore1.setDisable(false);
            one.setDisable(false);
            two.setDisable(false);
            thr.setDisable(false);
            fou.setDisable(false);
            fiv.setDisable(false);
            six.setDisable(false);
            sev.setDisable(false);
            eig.setDisable(false);
            nin.setDisable(false);

            Lscore2.setDisable(false);
            one2.setDisable(false);
            two2.setDisable(false);
            thr2.setDisable(false);
            fou2.setDisable(false);
            fiv2.setDisable(false);
            six2.setDisable(false);
            sev2.setDisable(false);
            eig2.setDisable(false);
            nin2.setDisable(false);

            intro.setVisible(false);
            objectivesBut.setDisable(true);
            rulesBut.setDisable(true);

            roll.setDisable(false);
            restart.setDisable(false);
        });

// start of Menu Bar
        MenuBar menuBar = new MenuBar();

//starting file menu selection and items
        Menu file = new Menu("File");

        MenuItem newI = new MenuItem("New");
        newI.setOnAction((ActionEvent e)-> 
        {
            restart.fire();
            start.fire();
        });

        MenuItem saveI = new MenuItem("Save");
        saveI.setOnAction((ActionEvent e)-> 
        {
            File save = fileChooser.showSaveDialog(primaryStage);
        });

        MenuItem saveAsI = new MenuItem("Save As");
        saveAsI.setOnAction((ActionEvent e)-> 
        {
            File saveAs = fileChooser.showSaveDialog(primaryStage);
        });

        MenuItem openI = new MenuItem("Open");
        openI.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null)
                {
                    openFile(file);
                }
            }
        });

        MenuItem exitI = new MenuItem("Exit");
        exitI.setOnAction(ActionEvent -> Platform.exit());

        //starting functions menu and its items
        Menu functions = new Menu("Functions");

        MenuItem startI = new MenuItem("Begin");
        startI.setOnAction((ActionEvent e)-> 
        {
            start.fire();
        });

        MenuItem restartI = new MenuItem("Restart");
        restartI.setOnAction((ActionEvent e)-> 
        {
            restart.fire();
        });

        //Sub-Menu
        Menu subThemes = new Menu("Themes");

        MenuItem defaultI = new MenuItem("Default");
        defaultI.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                root.getStylesheets().clear();
                dieTheme = ""; //makes sure the die changes
                clearTable();
                root.getStylesheets().add(defaultT);

            }
        });

        MenuItem spaceI = new MenuItem("Space");
        spaceI.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                root.getStylesheets().clear();
                dieTheme = "space"; //makes sure the die changes
                clearTable();
                root.getStylesheets().add(spaceT);

            }
        });

        MenuItem boringI = new MenuItem("Boring");
        boringI.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                root.getStylesheets().clear();
                dieTheme = "bore"; //makes sure the die changes
                clearTable();
                root.getStylesheets().add(boringT);

            }
        });

        subThemes.getItems().addAll(defaultI, spaceI, boringI);

//starting about menu and its items
        Menu about = new Menu("About");

        MenuItem infoI = new MenuItem("Info");
        infoI.setOnAction((ActionEvent e)-> 
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Source : Tradgames.org.uk");
            alert.setContentText(
            "It's not known for sure where Shut the box"
            + " originated.  Most theories point to a source in Northern"
            + " France and give Normandy or the Channel Islands as the"
            + " specific location.  It appears that it has been played"
            + " in this region for at least two hundred years and is"
            + " a game popular with sailors and fishermen.  There is"
            + " only evidence for the game in England from the middle"
            + " of the twentieth century, and since most games of a"
            + " similar ilk spawned references in a variety of literature,"
            + " this would seem to be a good indication that it did not"
            + " originate in that country.  In fact, Timothy Finn in his"
            + " \"Pub Games of England\" says that that the game was"
            + " brought to the South of England from the Channel Islands"
            + " in 1958 by a Mr. 'Chalky' Towbridge.  Apparently the game"
            + " has several names, one of which is Canoga and one unconfirmed"
            + " rumour claims that Shut the Box is also common in Zambia!");

            alert.showAndWait();
        });

        MenuItem helpI = new MenuItem("Help");
        helpI.setOnAction((ActionEvent e)-> 
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Help");
            alert.setHeaderText("Here's some help");
            alert.setContentText(
            "Please Reffer to the buttons on the right\n"
            + "side of the screen! Button(s) Rules and Objectives\n"
            + "will summerise the game for you. If you found the\n"
            + "instructions I put out a little confusing, feel\n"
            + "free googling \"shut in the box\" I'm pretty sure\n"
            + "you will find the rules and objectives there.\n\n"
            + "Thank you for playing!");

            alert.showAndWait();
        });

        about.getItems().addAll(infoI, helpI);

        functions.getItems().addAll(startI, restartI, subThemes);

        file.getItems().addAll(newI, saveI, saveAsI, openI, exitI);

        menuBar.getMenus().addAll(file, functions, about);
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        // end of menu bar -- -- --

        //text field start
        //This Text Area is Transparent and located near the
        //bottom right of the game's UI
        TextArea textArea = new TextArea();
        textArea.setTranslateX(300);
        textArea.setTranslateY(465);
        textArea.setScrollLeft(500);
        textArea.setMaxSize(200, 135);
        //text field end 

        root.getChildren().addAll(invRoot, one, two, thr, fou, fiv, six, sev,
                eig, nin, one2, two2, thr2, fou2, fiv2, six2, sev2,
                eig2, nin2, Lscore1, Lscore2, separator1, separator2,
                separator3, start, restart, die1Label, die2Label,
                objectivesBut, rulesBut, intro, roll, menuBar, textArea);
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(defaultT);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("images/die1.png"));
    }

    /**
     * Sound() generates a sound depending on soundDir
     */
    //MediaPlayer mediaPlayer;
    public void sound()
    {
        Media sound = new Media(new File(soundDir).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    /**
     * rollDie rolls the two die from the Die Class
     */
    public void rollDie()
    {
        die1 = new Die();
        die2 = new Die();

        die1.roll();
        die2.roll();

        displayDie(die1Label, die1.getFaceValue());
        displayDie(die2Label, die2.getFaceValue());
    }

    //DisplayDie displays our die by using a file prefix.
    public void displayDie(ImageView picDieLabel, int face)
    {
        Image image = new Image(FILE_PREFIX + face + dieTheme + FILE_SUFFIX); //

        // display die images in picDieJLabel
        picDieLabel.setImage(image);
    } // end method displayDie

    //score update is called when ever a change is made to the score.
    //it also makes sure the score will stay at 0 after going lower than 0.
    public void scoreUpdate()
    {
        if (score1 <= 0)
        {
            score1 = 0;
        }
        
        if (score2 <= 0)
        {
            score2 = 0;
        }
        
        Lscore1.setText("SCORE: " + score1);
        Lscore2.setText("SCORE: " + score2);
    }

    //clear table just clears the die off the table
    public void clearTable()
    {
        die1Label.setImage(null);
        die2Label.setImage(null);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    private void openFile(File file)
    {
        try
        {
            desktop.open(file);
        }
        catch (IOException ex)
        {
            Logger.getLogger(
                    ShutTheBox.class.getName()).log(
                            Level.SEVERE, null, ex
                    );
        }
    }

}
