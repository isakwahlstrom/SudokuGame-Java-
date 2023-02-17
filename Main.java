/*Imports */

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
