package com.locks.calculadora.controller;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CalculadoraController {
    private TextField pantalla;
    private TextField lineaOperaciones;
    private double num1;
    private double num2;
    private String operador;
    private boolean esperandoSegundoNumero;
    private String operaciones;
    private boolean resultadoMostrado;
    private String ultimoOperador;
    private double ultimoOperando;

    //CONSTRUCTOR
    public CalculadoraController(TextField pantalla, TextField lineaOperaciones) {
        this.pantalla = pantalla;
        this.lineaOperaciones = lineaOperaciones;
        pantalla.setEditable(false); //BLOQUEA LA ESCRITURA CON TECLADO
        lineaOperaciones.setEditable(false);
        this.esperandoSegundoNumero = false;
        resultadoMostrado = true;
        operaciones = "";

    }
    //AGREGAR DIGITO PULSANDO EL BOTON
    public void agregarDigito(String digito) {
        String texto = pantalla.getText();
        if(digito.equals(".") && texto.contains(".")) {
            if(pantalla.getText().contains(".")) return; // ya hay un decimal
            digito= ".";
        }
        if(texto.equals("0") && !digito.equals(".")){
            pantalla.setText(digito);
        }else{
            pantalla.appendText(digito);
        }
    }
    //LIMPIAR PANTALLA
    public void limpiar(){
        pantalla.clear();
        lineaOperaciones.clear();
        num1 = 0;
        num2 = 0;
        operador = "";
        esperandoSegundoNumero = false;
    }
    //BORRAR ULTIMO CARACTER
    public void limpiarCaracter(){
        String texto = pantalla.getText();
        if(!texto.isEmpty()){
            pantalla.setText(texto.substring(0, texto.length()-1));
        }else{
            limpiar();
        }
    }
    //SELECCION DE OPERADOR
    public void seleccionarOperador(String op){
        String textoactual= pantalla.getText();
        if (resultadoMostrado) {
            operaciones = "";
            lineaOperaciones.clear(); // limpia la línea visual
            resultadoMostrado = false; // ya no estamos en modo resultado
        }
        if(!textoactual.isEmpty()){
            num1= Double.parseDouble(textoactual);
            operador=op;
            pantalla.clear();
            if(operaciones.isEmpty()) {
                operaciones = formatearResultado(num1) + " " + operador + " ";
                lineaOperaciones.setText(operaciones);
            }else{
                operaciones = operaciones + formatearResultado(num1) + " " + operador;
                lineaOperaciones.setText(operaciones);
            }

            esperandoSegundoNumero=true;
        }else if(esperandoSegundoNumero) {
            mostrarAdvertencia("Ya seleccionó un operador. Introduzca el siguiente número.");
        }else{
            mostrarAdvertencia("Debe ingresar un número primero");
        }
    }
    //CALCULAR RESULTADO
    public void cacularResultado(){
        String textoPantalla = pantalla.getText();

        // Detecta si se ha ingresado un nuevo número distinto del resultado previo
        boolean pantallaNoVacia = !textoPantalla.isEmpty();
        double valorPantalla = pantallaNoVacia ? Double.parseDouble(textoPantalla) : 0;
        boolean nuevoNumeroIngresado = pantallaNoVacia && (!resultadoMostrado || valorPantalla != num1);

        // Determina num2
        if (!pantallaNoVacia) {
            if (resultadoMostrado) {
                num2 = ultimoOperando;
                operador = (operador != null && !operador.isEmpty()) ? operador : ultimoOperador;
            } else {
                mostrarAdvertencia("Introduzca número");
                return;
            }
        } else {
            num2 = nuevoNumeroIngresado ? valorPantalla : ultimoOperando;
        }

        if (operador == null || operador.isEmpty()) {
            mostrarAdvertencia("Falta operador");
            return;
        }

        //validacion si no hay operador
        if(operador== null || operador.isEmpty()){
            mostrarAdvertencia("Falta operador");
            return;
        }
        //operacion
        double resultado=0;
        double valorInicial=num1;

        switch (operador){
            case "+":
                resultado= num1 + num2;
                break;
            case "-":
                 resultado=num1 - num2;
                 break;
            case "*":
                 resultado= num1 * num2;
                 break;
            case "÷":
                 if(num2==0){
                     mostrarAdvertencia("No se puede dividir por 0");
                     return;
                 }else{
                      resultado= num1 / num2;
                  }
                  break;
        }
        //repetir operaciones
            ultimoOperador=operador;
            ultimoOperando=num2;
        //muestra resultados
            operaciones = formatearResultado(valorInicial)+ " "+ operador+ " "+formatearResultado(num2) + " = " + formatearResultado(resultado);
            pantalla.setText(formatearResultado(resultado));
            lineaOperaciones.setText(operaciones);
        //actualiza variables y estados
            num1=resultado;
            esperandoSegundoNumero=false;
            resultadoMostrado=true;
    }

    //CALCULAR PORCENTAJE
    public void cacularPorcentajeYAplicar(){
        if(pantalla.getText().isEmpty()) {
            mostrarAdvertencia("Introduzca numero");
            return;
        }
        if(operador== null || operador.isEmpty()){
            mostrarAdvertencia("Falta operador");
            return;
        }
        if(esperandoSegundoNumero){
            num2= Double.parseDouble(pantalla.getText());
            double porcentaje= num1 * (num2/100);
            double resultado= 0;
            switch (operador) {
                case "+":
                    resultado = num1 + porcentaje;
                    break;
                case "-":
                    resultado = num1 - porcentaje;
                    break;
                case "*":
                    resultado = num1 * porcentaje;
                    break;
                case "÷":
                    if (porcentaje == 0) {
                        mostrarAdvertencia("No se puede dividir por 0");
                        return;
                    }
                    resultado = num1 / porcentaje;
                    break;
                default:
                    mostrarAdvertencia("Operador desconocido");
                    return;
            }

            operaciones += "(" + formatearResultado(num2) + "% → " + formatearResultado(porcentaje) + ") = " + formatearResultado(resultado);
            pantalla.setText(formatearResultado(resultado));
            lineaOperaciones.setText(operaciones);
            resultadoMostrado=true;

            // Actualizar estado
            num1 = resultado;
            operador = "";
            esperandoSegundoNumero = false;

        }
    }

    //FORMATEAR RESULTADO DE DOUBLE A STRING CONTROLANDO DECIMALES
    public String formatearResultado(double resultado){
        if(resultado == Math.floor(resultado)){
            return String.format("%.0f", resultado);
        }else{
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            symbols.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat("0.#########", symbols);
            return df.format(resultado);
        }
    }
    //MENSAJE DE ADVERTENCIA
    public void mostrarAdvertencia(String mensaje) {
        Alert alerta = new Alert(AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    //CAMBIO SIGNO
    public void cambioSigno(){
        if(!pantalla.getText().isEmpty()) {
            double numero = Double.parseDouble(pantalla.getText());
            pantalla.setText(formatearResultado(-numero));
        }
    }

    //LIMPIAR TEXTFIELD LINEA DE OPERACIONES (SIN USO EN ESTA VERSION)
    public void limpiarLineaOperaciones(){
        if(!lineaOperaciones.getText().isEmpty()) {
            lineaOperaciones.clear();
        }
    }

    //getter`s y setter`s
    public String getOperador(){
        return operador;
    }
}
