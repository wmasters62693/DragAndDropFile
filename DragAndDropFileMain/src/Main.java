import javafx.embed.swing.JFXPanel;
import javafx.application.Platform;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.stage.WindowEvent;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.lang.String;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;


public class Main
{
    public static void main (String[] args)
    {
        launchFX();
    }

    private static void launchFX()
    {
        //initialises JavaFX
        new JFXPanel();

        //runs initialisation on the JavaFX thread
        Platform.runLater(() -> initialiseGUI());
    }

    public static void initialiseGUI() {
        Stage stage = new Stage();
        stage.setTitle("Testing drag and drop in JavaFX");
        stage.setResizable(false);
        stage.setWidth(720);
        stage.setHeight(720);
        stage.setOnCloseRequest((WindowEvent we) -> terminate());
        stage.show();

        
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("MainWindow.fxml"));
        
            Pane rootPane = (AnchorPane) loader.load();
            Scene scene = new Scene(rootPane);
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException io) {
            System.out.println("ERROR: " + io);
        }
        
        
        
        
        /*Button DNDbtn = new Button();
        DNDbtn.setOnAction((ActionEvent ae) -> openDNDWindow());
        DNDbtn.setText("Add a new file");

        rootPane.getChildren().add(DNDbtn);*/
    }

    public static void openDNDWindow() {

        //System.out.println("FUNCTION: openDNDWindow");
        final Stage[] DNDStage = {new Stage()};
        DNDStage[0].setTitle("Drag a file into the window");
        DNDStage[0].setResizable(false);
        Pane rootPane = new Pane();
        Scene scene = new Scene(rootPane);
        scene.getStylesheets().add("CSS.css");
        DNDStage[0].setScene(scene);
        DNDStage[0].setWidth(300);
        DNDStage[0].setHeight(300);
        DNDStage[0].show();

        Label directory = new Label("");

        Label DP = new Label("Drop a file into this window");
        Label dropped = new Label("File Received");
        VBox dragTarget = new VBox();
        dragTarget.getStyleClass().add("vBox");
        dragTarget.getChildren().add(DP);
        rootPane.setStyle("-fx-background-color: #FF0000;");

        rootPane.setOnDragOver((DragEvent de) -> {
            if (de.getGestureSource() != rootPane && de.getDragboard().hasFiles()) {
                List<File> newFile = de.getDragboard().getFiles();
                dragTarget.getChildren().remove(DP);
                dragTarget.getChildren().remove(dropped);
                dragTarget.getChildren().add(dropped);
                rootPane.setStyle("-fx-background-color: #00FF00;");

                directory.setText(splitUpDirectory(newFile));
                /*splitUpDirectory(newFile);
                directory.setText(newFile.toString());
                doSomeThingWithNewFile(newFile);*/

            }
        });
        directory.setTranslateY(64);
        rootPane.getChildren().add(directory);
        directory.setPrefWidth(300);
        directory.setWrapText(true);
        directory.setTextAlignment(TextAlignment.JUSTIFY);


        rootPane.getChildren().add(dragTarget);


    }

    public static void doSomeThingWithNewFile(List<File> newFile) {
        //https://github.com/google/guava
    }

    public static String splitUpDirectoryA(List<File> newFile) {
        String newFileString = newFile.toString();
        char[] newFileArray = newFileString.toCharArray();
        for(int i = 0; i <= newFileString.length() -1; i++ ){
            if (newFileString.charAt(i) == ',') {
                newFileArray[i] = '\\';
                newFileArray[i+1] = 'n';

            }
        }
        newFileString = String.valueOf(newFileArray);
        System.out.println(newFileString);
        return(newFileString);
    }

    public static String splitUpDirectory(List<File> newFiles) {
        String newFileString = newFiles.toString();
        String[] FileListShort = new String[newFiles.size()];

        //System.out.println("New file size: " + newFile.size());
        for (int i=0; i <= newFiles.size() -1; i++) {
            System.out.println("i: " + i);
            System.out.println("newFiles.get(i); = " + newFiles.get(i));

            String[] directoryList = (newFiles.get(i).toString()).split("\\\\");


            for (String x: directoryList) {
                System.out.println(x);
            }

            FileListShort[i] = directoryList[directoryList.length - 1 ] ;
            //System.out.println(directoryList);
            //FileListShort[i] = (newFile.indexOf(i)


        }

        String shortDirects = "";

        for (String x: FileListShort) {
            shortDirects = shortDirects + "\n " + x;


        }

        return(shortDirects);
    }


    private static void terminate()
    {
        System.out.println("bye bye!");
        System.exit(0);
    }
}
