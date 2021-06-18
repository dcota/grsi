package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class MySQLConnection {

    private Properties p;
    private Connection connection;

    public MySQLConnection(){
        setConnection();
    }

    public void setConnection(){
        p = new Properties();
        try{
            InputStream input = new FileInputStream("dbconfig.properties");
            p.load(input);
            connection = DriverManager.getConnection(
                    p.getProperty("url"),p.getProperty("username"),p.getProperty("password"));
            System.out.println("LIGADO À BD");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("OCORREU UM ERRO");
        }
    }

    public int getIDCliente (){
        int numCliente=0;
        String sql = "SELECT MAX(idcliente) FROM cliente";
        try{
            Statement stm = connection.createStatement();
            ResultSet result = stm.executeQuery(sql);
            while(result.next()){
                numCliente = result.getInt(1);
                System.out.println(result.getInt(1));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return numCliente + 1;
    }

    public boolean inserirCliente (Cliente cliente){
        String sql = "INSERT INTO cliente VALUES (?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1,cliente.getNumCliente());
            stm.setString(2,cliente.getNome());
            stm.setInt(3,cliente.getNif());
            stm.setString(4,cliente.getMorada());
            stm.setInt(5,cliente.getCodLoc());
            stm.setInt(6,cliente.getCodRua());
            stm.setString(7,cliente.getLocal());
            stm.setDate(8, Date.valueOf(cliente.getDataNasc()));
            stm.setString(9,cliente.getObs());
            //executar inserção
            int rows = stm.executeUpdate();
            if (rows == 1){
                return true;
            }
            else return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public ResultSet getClientes(){
        ResultSet result = null;
        String sql = "SELECT idcliente, nome, morada, nif FROM cliente";
        try{
            Statement stm = connection.createStatement();
            result = stm.executeQuery(sql);
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return result;
        }
    }

    public ResultSet consultaCliente (int id){
        ResultSet result = null;
        String sql = "SELECT * FROM cliente WHERE idcliente = " + id;
        try{
            Statement stm = connection.createStatement();
            result = stm.executeQuery(sql);
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return result;
        }
    }



}
