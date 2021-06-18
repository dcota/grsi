package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Cliente;
import model.MySQLConnection;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListaClientesController {

    @FXML
    private TableView<Cliente> tblClientes;

    @FXML
    private TableColumn<Cliente, Integer> colNum;

    @FXML
    private TableColumn<Cliente, String> colNome;

    @FXML
    private TableColumn<Cliente, String> colMorada;

    @FXML
    private TableColumn<Cliente, Integer> colNIF;

    @FXML
    private Button btnInserirCliente;

    @FXML
    private Button btnAbrirTicket;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConsultar;

    @FXML
    private TextField tfPesquisar;

    private MySQLConnection connection;

    private ObservableList<Cliente> listaClientes;
    private ObservableList<Cliente> listaPesquisa;


    private Cliente linhaCliente;

    public void initialize() throws SQLException {
        //preparar a tabela para receber os clientes da base de dados
        listaClientes = FXCollections.observableArrayList();
        listaPesquisa = FXCollections.observableArrayList();
        this.tblClientes.setItems(listaClientes);
        this.colNum.setCellValueFactory(new PropertyValueFactory<Cliente,Integer>("numCliente"));
        this.colNome.setCellValueFactory(new PropertyValueFactory<Cliente,String>("nome"));
        this.colMorada.setCellValueFactory(new PropertyValueFactory<Cliente,String>("morada"));
        this.colNIF.setCellValueFactory(new PropertyValueFactory<Cliente,Integer>("nif"));
        //chama método para listar os clientes
        preencheTabela();
    }

    @FXML
    public void pesquisarNome(){
        String nomePesquisa = this.tfPesquisar.getText();
        if(nomePesquisa.isEmpty()){
            this.tblClientes.setItems(listaClientes);
        } else {
            this.listaPesquisa.clear();
            for(Cliente c : this.listaClientes){
                if(c.getNome().toLowerCase().contains(nomePesquisa.toLowerCase())){
                    this.listaPesquisa.add(c);
                }
            }

            /*for(int i=0; i<this.listaClientes.size();i++){
                if(this.listaClientes.get(i).getNome().toLowerCase().contains(nomePesquisa.toLowerCase())){
                    this.listaPesquisa.add(this.listaClientes.get(i));
                }
            }*/

            this.tblClientes.setItems(listaPesquisa);
        }
    }

    @FXML
    public void inserirCliente (ActionEvent event){
        try {
            //preparar o lançamento da vista formulário de cliente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/clientesView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            preencheTabela();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void consultar(ActionEvent event){
        //criar cliente com a informação da linha selecionada
        linhaCliente = this.tblClientes.getSelectionModel().getSelectedItem();
        if(linhaCliente==null){
            alertaERRO("Tem de selecionar uma linha!");
        } else {
            try {
                //preparar o lançamento da vista formulário de cliente
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/clientesView.fxml"));
                Parent root = loader.load();

                //usar métodos do controller da vista clientes
                ClientesController controller = loader.getController();
                //passar o objeto para a vista cliente
                controller.getCliente(this.linhaCliente.getNumCliente());
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
                preencheTabela();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void preencheTabela() throws SQLException {
        listaClientes.clear();
        //instanciar a ligação à BD
        connection = new MySQLConnection();
        //obter resultado do query
        ResultSet result = connection.getClientes();
        //iterar no resultado do query
        while(result.next()){
            try{
                int id = result.getInt(1);
                String nome = result.getString(2);
                String morada = result.getString(3);
                int nif = result.getInt(4);
                Cliente cliente = new Cliente (id,nome,nif, morada);
                listaClientes.add(cliente);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            this.tblClientes.setItems(listaClientes);
            this.tblClientes.refresh();
        }
    }

    public void alertaERRO(String txt){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setHeaderText(null);
        alerta.setTitle("ATENÇÃO");
        alerta.setContentText(txt);
        alerta.showAndWait();
    }

}
