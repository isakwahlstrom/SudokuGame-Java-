package se.kth.isakwah.labb4.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import se.kth.isakwah.labb4.model.SudokuModel;
import java.util.Objects;


public class SudokuView extends BorderPane {
    public static final int GRID_SIZE = 9;
    public static final int SECTIONS_PER_ROW = 3;
    public static final int SECTION_SIZE = 3;
    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private Label[][] gridTiles; // the tiles/squares to show in the Sudoku grid
    private TilePane numberPane;
    private SudokuModel model;
    private Button hintButton;
    private Button checkButton;
    private final Button[] buttons = new Button[10];
    private final SudokuController controller;

    public SudokuView(SudokuModel model) {
        super(); // creates borderpane
        this.controller = new SudokuController(model,this);
        this.model = model;
        gridSetup();
        initButtons();
        initMenues();
        addButtonEventHandlers();
    }

    public void updateBoard(int row, int col) {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);
        createClickableTile(row, col, font);
        numberPane = makeNumberPane();
        this.setCenter(numberPane);
    }

    public void updateBoard(SudokuModel model) {
        this.model = model;
        gridSetup();
    }

    private void gridSetup() {
        gridTiles = new Label[GRID_SIZE][GRID_SIZE];
        initSudokuGrid();
        numberPane = makeNumberPane();
        this.setCenter(numberPane);

    }

    private void initSudokuGrid() {
        Font font;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if(!model.getStartTile(row,col)) {
                    font = Font.font("Monospaced", FontWeight.NORMAL, 20);
                    createClickableTile(row, col, font);
                } else {
                    font = Font.font("Monospaced", FontWeight.EXTRA_BOLD, 20);
                    createClickableTile(row, col, font);
                }
            }
        }
    }

    private void createClickableTile(int row, int col,Font font) {
        Label tile;
        if(Objects.equals(model.getTile(row, col), "0")) {
            tile = new Label(" ");
        } else {
            tile = new Label(model.getTile(row, col));
        }

        tile.setPrefWidth(16);
        tile.setPrefHeight(16);
        tile.setFont(font);
        tile.setAlignment(Pos.CENTER);
        tile.setStyle("-fx-border-color: black; -fx-border-width: 0.9x;"); // css style
        EventHandler<MouseEvent> tileCLickHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for(int row = 0; row < GRID_SIZE; row++) {
                    for(int col = 0; col < GRID_SIZE; col++) {
                        if(event.getSource() == gridTiles[row][col]) {
                            controller.HandleAddTile(row,col);
                            return;
                        }
                    }
                }
            }
        };
        tile.setOnMouseClicked(tileCLickHandler);
        gridTiles[row][col] = tile;
    }

    private TilePane makeNumberPane() {
        // create the root tile pane
        TilePane root = new TilePane();
        root.setPrefColumns(SECTIONS_PER_ROW);
        root.setPrefRows(SECTIONS_PER_ROW);
        root.setStyle(
                "-fx-border-color: black; -fx-border-width: 0.3px; -fx-background-color: white;");

        // create the 3*3 sections and add the number tiles
        //TilePane[][] sections = new TilePane[SECTIONS_PER_ROW][SECTIONS_PER_ROW];
        //int i = 0;
        for (int srow = 0; srow < SECTIONS_PER_ROW; srow++) {
            for (int scol = 0; scol < SECTIONS_PER_ROW; scol++) {
                TilePane section = new TilePane();
                section.setPrefColumns(SECTION_SIZE);
                section.setPrefRows(SECTION_SIZE);
                section.setStyle("-fx-border-color: black; -fx-border-width: 0.3px;");

                // add number tiles to this section
                for (int row = 0; row < SECTION_SIZE; row++) {
                    for (int col = 0; col < SECTION_SIZE; col++) {
                        // calculate which tile and add
                        section.getChildren().add(
                                gridTiles[srow * SECTION_SIZE + row][scol * SECTION_SIZE + col]);
                    }
                }
                // add the section to the root tile pane
                root.getChildren().add(section);
            }
        }
        return root;
    }

    private void initButtons() {
        checkButton = new Button("Check");
        hintButton = new Button("Hint");
        VBox leftButtons = new VBox();
        leftButtons.getChildren().addAll(checkButton,hintButton);
        leftButtons.setSpacing(5);
        leftButtons.setAlignment(Pos.CENTER);
        this.setLeft(leftButtons);

        VBox RightNumberButtons = new VBox();
        buttons[0] = new Button("1");
        buttons[1] = new Button("2");
        buttons[2] = new Button("3");
        buttons[3] = new Button("4");
        buttons[4] = new Button("5");
        buttons[5] = new Button("6");
        buttons[6] = new Button("7");
        buttons[7] = new Button("8");
        buttons[8] = new Button("9");
        buttons[9] = new Button("C");
        RightNumberButtons.setAlignment(Pos.CENTER);
        RightNumberButtons.getChildren().addAll(buttons[0],buttons[1],buttons[2],buttons[3],buttons[4],buttons[5],buttons[6],buttons[7],buttons[8],buttons[9]);
        this.setRight(RightNumberButtons);
    }

    private void initMenues() {
        //The three menus
        Menu fileMenu = new Menu("File");
        Menu gameMenu = new Menu("Game");
        Menu helpMenu = new Menu("Help");

        //Filemenu submenus
        MenuItem exitItem = new MenuItem("Exit");
        MenuItem loadItem = new MenuItem("Load game");
        MenuItem saveItem = new MenuItem("Save game");

        exitItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });
        loadItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.HandleLoadGame();
            }
        });
        saveItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.HandleSaveGame();
            }
        });

        //Gamemenu submenus
        MenuItem startNewGame = new MenuItem("Start new game");
        Menu pickDifficulty = new Menu("Choose difficulty");

        MenuItem Easy = new MenuItem("Easy");
        MenuItem Medium = new MenuItem("Medium");
        MenuItem Hard = new MenuItem("Hard");

        //Add each difficulty to pickDifficultyMenu
        pickDifficulty.getItems().addAll(Easy,Medium,Hard);
        Easy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.HandleDifficulty("EASY");
            }
        });
        Medium.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.HandleDifficulty("MEDIUM");
            }
        });
        Hard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.HandleDifficulty("HARD");
            }
        });

        startNewGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.HandleNewGame();
            }
        });

        //Helpmenu submenus
        MenuItem ClearAllItem = new MenuItem("Clear all");
        MenuItem checkItem = new MenuItem("Check");
        MenuItem infoItem = new MenuItem("Info");
        //Eventhandlers for every menuitem

        ClearAllItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.HandleClearAll();
            }
        });

        checkItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showAlert(controller.HandleCheck());
            }
        });
        infoItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showAlert(controller.HandleRules());
            }
        });

        // Adds the submenus to the menus
        fileMenu.getItems().addAll(loadItem,saveItem,exitItem);
        gameMenu.getItems().addAll(startNewGame,pickDifficulty);
        helpMenu.getItems().addAll(ClearAllItem,checkItem,infoItem);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu,gameMenu,helpMenu);
        this.setTop(menuBar);
    }

    private void addButtonEventHandlers() {
        EventHandler<MouseEvent> buttonClickHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for(int i=0;i<10;i++) {
                    if(event.getSource()==buttons[i]) {
                        controller.HandleNumberPicked(i);
                    }
                }
            }
        };
        for(int i=0;i<10;i++) {
            buttons[i].setOnMouseClicked(buttonClickHandler);
        }

        EventHandler<ActionEvent> checkButtonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showAlert(controller.HandleCheck());
            }
        };
        checkButton.setOnAction(checkButtonHandler);

        EventHandler<ActionEvent> hintHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {controller.HandleHint();}
        };
        hintButton.setOnAction(hintHandler);
    }

    void showAlert(String message) {
        alert.setHeaderText("");
        alert.setTitle("Alert!");
        alert.setContentText(message);
        alert.show();
    }

}
