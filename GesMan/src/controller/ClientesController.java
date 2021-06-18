package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Cliente;
import model.MySQLConnection;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ClientesController {

    @FXML private TextField tfNumCliente;

    @FXML private TextField tfNome;

    @FXML private TextField tfNIF;

    @FXML private TextField tfMorada;

    @FXML private TextField tfCPlocalidade;

    @FXML private TextField tfCPrua;

    @FXML private TextField tfCPdescr;

    @FXML private DatePicker dpDataNasc;

    @FXML private TextArea taObs;

    @FXML private Button btnCriar;

    @FXML private Button btnFechar;

    private int numCliente;

    private MySQLConnection connection;

    public void initialize(){
        //estabelecer ligação
        connection = new MySQLConnection();
        //ir buscar o próximo num de cliente
        numCliente = connection.getIDCliente();
        //preencher o campo com o num de cliente
        this.tfNumCliente.setText(String.valueOf(numCliente));
    }

    @FXML
    void criar(ActionEvent event) {
        if(validaCliente()){
            String nome = this.tfNome.getText();
            int nif = Integer.parseInt(this.tfNIF.getText());
            String morada = this.tfMorada.getText();
            int cpLoc = Integer.parseInt(this.tfCPlocalidade.getText());
            int cpRua = Integer.parseInt(this.tfCPrua.getText());
            String cpDescr = this.tfCPdescr.getText();
            LocalDate dataNasc = this.dpDataNasc.getValue();
            String obs = this.taObs.getText();
            //definir objeto a inserir
            Cliente cliente =
                    new Cliente(numCliente,nome,nif,morada,cpLoc,cpRua,cpDescr,dataNasc,obs);
            //passar o objeto cliente para o método que vai inserir na BD
            if(connection.inserirCliente(cliente)){
                alertaSucesso("Cliente adicionado com sucesso!");
                Stage stage = (Stage) this.btnCriar.getScene().getWindow();
                stage.close();
            }
            else alertaERRO("Não foi possível adicionar o cliente...");
        }
        else {
            alertaERRO("Campos vazios não permitidos!");
        }
    }
    public boolean validaCliente(){
        if(this.tfNome.getText().isEmpty() || this.tfNIF.getText().isEmpty()
           ||this.tfMorada.getText().isEmpty()||this.tfCPlocalidade.getText().isEmpty()
           ||this.tfCPrua.getText().isEmpty()
           ||this.tfCPdescr.getText().isEmpty()){
            return false;
        }
        else return true;
    }

    public void getCliente(int id){
        try{
            ResultSet result = connection.consultaCliente(id);
            while(result.next()){
                this.tfNumCliente.setText(String.valueOf(result.getInt(1)));
                this.tfNome.setText(result.getString(2));
                this.tfNIF.setText(String.valueOf(result.getInt(3)));
                this.tfMorada.setText(result.getString(4));
                this.tfCPlocalidade.setText(String.valueOf(result.getInt(5)));
                this.tfCPrua.setText(String.valueOf(result.getInt(6)));
                this.tfCPdescr.setText((result.getString(7)));
                Date date = result.getDate(8);
                Format f = new SimpleDateFormat("dd/MM/yyyy");
                String strDate = f.format(date);
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate ld = LocalDate.parse(strDate,dateTimeFormatter);
                this.dpDataNasc.setValue(ld);
                this.taObs.setText(result.getString(9));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void alertaERRO(String txt){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setHeaderText(null);
        alerta.setTitle("ATENÇÃO");
        alerta.setContentText(txt);
        alerta.showAndWait();
    }

    public void alertaSucesso(String txt){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setTitle("SUCESSO");
        alerta.setContentText(txt);
        alerta.showAndWait();
    }

    public void fechar(ActionEvent event){
        Stage stage = (Stage) this.btnFechar.getScene().getWindow();
        stage.close();
    }
}
