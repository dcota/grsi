package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class MainController {

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnTickets;

    @FXML
    private BorderPane bpCenter;

    private Pane view;

    @FXML
    void clientes(ActionEvent event) {
        Pane view = getView("/view/listaClientesView.fxml");
        bpCenter.setCenter(view);
    }

    @FXML
    void tickets(ActionEvent event) {
        Pane view = getView("/view/ticketsView.fxml");
        bpCenter.setCenter(view);
    }

    //método para colocar a nova view na MainView
    public Pane getView(String fileName)  {
        URL fileURL = Main.class.getResource(fileName);
        try {
            view = new FXMLLoader().load(fileURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

}