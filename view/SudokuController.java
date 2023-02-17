/*Imports*/

public class SudokuController {
    private SudokuModel model;
    private final SudokuView view;
    private int numberPicked;
    private FileChooser fileChooser;
    public SudokuController(SudokuModel model, SudokuView view) {
        this.model = model;
        this.view = view;
        fileChooser = new FileChooser();
    }

    public void HandleAddTile(int row, int col) {
        try {
            if(numberPicked>=0 && numberPicked<9) {
                model.setTile(row, col, numberPicked+1);
                view.updateBoard(row, col);
                //numberPicked = 0;
            }
            if(numberPicked==9){
                model.clearTile(row,col);
                view.updateBoard(row, col);
                //numberPicked = 0;
            }
        } catch(IllegalStateException wrong) {
            view.showAlert("This tile is an immutable tile, choose another one.");
        }
        if (model.isGameSolved())
            view.showAlert("Congratulations, you won!");
    }

    public void HandleNumberPicked(int numberPicked) {
        this.numberPicked = numberPicked;
    }

    public void HandleDifficulty(String difficulty) {
        switch (difficulty) {
            case "EASY" ->   model = new SudokuModel(SudokuUtilities.SudokuLevel.EASY);
            case "MEDIUM" -> model = new SudokuModel(SudokuUtilities.SudokuLevel.MEDIUM);
            case "HARD" ->   model = new SudokuModel(SudokuUtilities.SudokuLevel.HARD);
            default -> {}
        }
        view.updateBoard(model);

    }

    public void HandleClearAll() {
        model.clearBoard();
        view.updateBoard(model);
    }

    public String HandleCheck() {
        if(model.checkCorrect()) {
            return "Placed tiles are correct";
        } else {
            return"Placed tiles are incorrect";
        }
    }

    public String HandleRules() {
        return model.rules();
    }

    public void HandleNewGame() {
        model = new SudokuModel(model.getLevel());
        view.updateBoard(model);
    }

    public void HandleHint() {
        if(!model.isGameSolved())
            model.hint();
        if(model.isGameSolved())
            view.showAlert("Congratulations, you won!");
        view.updateBoard(model);
    }

    public void HandleSaveGame() {
        try {
            fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("C:/Sudoku"));
            File selectedFile = fileChooser.showSaveDialog(new Stage());
            SudokuModel modelToSave = this.model;
            SudokuIO.serializeToFile(selectedFile, modelToSave);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void HandleLoadGame() {
        fileChooser.setInitialDirectory(new File("C:/Sudoku"));
        File file = fileChooser.showOpenDialog(new Stage());
        try {
            if (file.exists()) {
               SudokuModel model =  SudokuIO.deSerializeFromFile(file);
               this.model = model;
               view.updateBoard(model);
            }
        } catch (ClassNotFoundException | IOException p) {
            view.showAlert("Could not load file,\n choose another file or start a new game.");
        }
    }

}
