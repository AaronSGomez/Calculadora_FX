package com.locks.calculadora.model;
import com.locks.calculadora.controller.CalculadoraController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;


public class EntornoGrafico_fx extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        VBox root = new VBox(); //layout vertical
        root.setAlignment(Pos.CENTER);
        root.setSpacing(0);
        Scene scene = new Scene(root, 300, 400);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        TextField pantalla = new TextField();
        pantalla.getStyleClass().add("textfield-display");
        TextField lineaOperaciones = new TextField();
        lineaOperaciones.getStyleClass().add("textfield-linea");
        //Constructor

        CalculadoraController cal = new CalculadoraController(pantalla, lineaOperaciones);
        GridPane grid = new GridPane();
        grid.setHgap(10); //espacio 10 pixels entre columnas
        grid.setVgap(10); //espacio 10 pixels entre filas
        grid.setPadding(new Insets(15, 15, 15, 15)); // margen general alrededor del grid

        //agregamos como hijos los elementos
        root.getChildren().addAll(lineaOperaciones, pantalla, grid);

        //creamos buttones
        Button button0 = new Button("0");
        button0.getStyleClass().add("boton-numero");
        Button button1 = new Button("1");
        button1.getStyleClass().add("boton-numero");
        Button button2 = new Button("2");
        button2.getStyleClass().add("boton-numero");
        Button button3 = new Button("3");
        button3.getStyleClass().add("boton-numero");
        Button button4 = new Button("4");
        button4.getStyleClass().add("boton-numero");
        Button button5 = new Button("5");
        button5.getStyleClass().add("boton-numero");
        Button button6 = new Button("6");
        button6.getStyleClass().add("boton-numero");
        Button button7 = new Button("7");
        button7.getStyleClass().add("boton-numero");
        Button button8 = new Button("8");
        button8.getStyleClass().add("boton-numero");
        Button button9 = new Button("9");
        button9.getStyleClass().add("boton-numero");
        Button buttonPunto = new Button(".");
        buttonPunto.getStyleClass().add("boton-funcion");
        Button buttonC = new Button("<=");
        buttonC.getStyleClass().add("boton-funcion");
        Button buttonMas = new Button("+");
        buttonMas.getStyleClass().add("boton-funcion");
        Button buttonMenos = new Button("-");
        buttonMenos.getStyleClass().add("boton-funcion");
        Button buttonPor = new Button("x");
        buttonPor.getStyleClass().add("boton-funcion");
        Button buttonDiv = new Button("÷");
        buttonDiv.getStyleClass().add("boton-funcion");
        Button buttonIgual = new Button("=");
        buttonIgual.getStyleClass().add("boton-igual");
        Button buttonCA = new Button("CE");
        buttonCA.getStyleClass().add("boton-funcion");
        Button buttonCent = new Button("%");
        buttonCent.getStyleClass().add("boton-funcion");
        Button buttonSymbol = new Button("+|-");
        buttonSymbol.getStyleClass().add("boton-funcion");

        grid.add(buttonCA, 0, 0);
        grid.add(buttonCent, 1, 0);
        grid.add(buttonDiv, 2, 0);
        grid.add(buttonPor, 3, 0);
        grid.add(button7, 0, 1);
        grid.add(button8, 1, 1);
        grid.add(button9, 2, 1);
        grid.add(buttonMenos, 3, 1);
        grid.add(button4, 0, 2);
        grid.add(button5, 1, 2);
        grid.add(button6, 2, 2);
        grid.add(buttonMas, 3, 2);
        grid.add(button1, 0, 3);
        grid.add(button2, 1, 3);
        grid.add(button3, 2, 3);
        grid.add(buttonIgual, 3, 3, 1, 2);
        // (inicio columna, fila, columnas que ocupa, filas que ocupa)
        grid.add(buttonSymbol, 0, 4);
        grid.add(button0, 1, 4);
        grid.add(buttonPunto, 2, 4);

        primaryStage.setTitle("Calculadora FX");
        primaryStage.setScene(scene);
        primaryStage.show();

        button0.setOnAction(e -> cal.agregarDigito("0"));
        button1.setOnAction(e -> cal.agregarDigito("1"));
        button2.setOnAction(e -> cal.agregarDigito("2"));
        button3.setOnAction(e -> cal.agregarDigito("3"));
        button4.setOnAction(e -> cal.agregarDigito("4"));
        button5.setOnAction(e -> cal.agregarDigito("5"));
        button6.setOnAction(e -> cal.agregarDigito("6"));
        button7.setOnAction(e -> cal.agregarDigito("7"));
        button8.setOnAction(e -> cal.agregarDigito("8"));
        button9.setOnAction(e -> cal.agregarDigito("9"));
        buttonPunto.setOnAction(e -> cal.agregarDigito("."));
        buttonC.setOnAction(e -> cal.limpiar()); //desactivado de momento
        buttonMas.setOnAction(e -> cal.seleccionarOperador("+"));
        buttonMenos.setOnAction(e -> cal.seleccionarOperador("-"));
        buttonPor.setOnAction(e -> cal.seleccionarOperador("*"));
        buttonDiv.setOnAction(e -> cal.seleccionarOperador("÷"));
        buttonIgual.setOnAction(e -> cal.cacularResultado());
        buttonCA.setOnAction(e -> cal.limpiarCaracter());
        buttonCent.setOnAction(e -> cal.cacularPorcentajeYAplicar());
        buttonSymbol.setOnAction(e -> cal.cambioSigno());
    }
}

