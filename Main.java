package se.kth.isakwah.labb4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.kth.isakwah.labb4.model.SudokuModel;
import se.kth.isakwah.labb4.model.SudokuUtilities;
import se.kth.isakwah.labb4.view.SudokuView;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        SudokuModel model = new SudokuModel(SudokuUtilities.SudokuLevel.EASY);
        SudokuView view = new SudokuView(model);

        Scene scene = new Scene(view);
        primaryStage.setTitle("Sudoku");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}