/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ads.pi3.DAO;

import ads.pi3.utils.ConnectionFactory;
import ads.pi3.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ninck
 */
public class ClienteDAO {
    
    private static int totalClientes= 0;
    private static List<Cliente> listaClientes = new ArrayList<Cliente>();
    
    // inserir no banco de dados
    public static void inserir (Cliente cliente){
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        
        try {            
            stmt = con.prepareStatement("INSERT INTO cliente (cpf, nome, data_nascimento, telefone, email) VALUES(?,?,?,?,?)");            
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            java.sql.Date dt = new java.sql.Date(cliente.getDataNascimento().getTime());                        
            stmt.setDate(3, dt);
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEmail());                        
            stmt.execute();            
        } catch (SQLException ex) {
            System.out.print(ex);
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }        
    }
    
    public static void atualizar(Cliente cliente) throws SQLException, Exception {        
        String sql = "UPDATE cliente SET cpf=?, nome=?, data_nascimento=?, telefone=?, email=?"
            + "WHERE (id=?)";
        
        Connection connection = ConnectionFactory.getConnetion();;        
        PreparedStatement stmt = null;
        try {                        
            stmt = connection.prepareStatement(sql);            
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            java.sql.Date dt = new java.sql.Date (cliente.getDataNascimento().getTime());                        
            stmt.setDate(3, dt);            
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEmail());                        
            stmt.setInt(6, cliente.getId());                        
            stmt.execute();
        } finally {
            //Se o statement ainda estiver aberto, realiza seu fechamento
            if (stmt != null && !stmt.isClosed()) stmt.close();
            //Se a conexão ainda estiver aberta, realiza seu fechamento
            if (connection != null && !connection.isClosed()) connection.close();
        }
    }
     
    public static void excluir(int id) throws SQLException, Exception {
        //Monta a string de atualização do cliente no BD, utilizando
        //prepared statement
        String sql = "UPDATE cliente SET ativo=0 WHERE (id=?)";
        //Conexão para abertura e fechamento
        Connection connection = null;
        //Statement para obtenção através da conexão, execução de
        //comandos SQL e fechamentos
        PreparedStatement preparedStatement = null;
        try {
            //Abre uma conexão com o banco de dados
            connection = ConnectionFactory.getConnetion();
            //Cria um statement para execução de instruções SQL
            preparedStatement = connection.prepareStatement(sql);
            //Configura os parâmetros do "PreparedStatement"
            preparedStatement.setInt(1, id);
            
            //Executa o comando no banco de dados
            preparedStatement.execute();
        } finally {
            //Se o statement ainda estiver aberto, realiza seu fechamento
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
            //Se a conexão ainda estiver aberta, realiza seu fechamento
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }
     
    // listar os Cliente
    public static List <Cliente>  listar (){
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Cliente> clientes = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM cliente where ativo=1");
            rs = stmt.executeQuery();
            while (rs.next()) {                
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));                
                cliente.setCpf(rs.getString("cpf"));
                cliente.setNome(rs.getString("nome"));                
                Date d = new Date(rs.getTimestamp("data_nascimento").getTime());
                cliente.setData_nascimento(d);                
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));                
                cliente.setAtivo(rs.getInt("ativo"));
                clientes.add(cliente);
            }
        } catch (SQLException ex) {
            System.out.print(ex);
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(, "");
            
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        return clientes;
    }
    
    //Procura um cliente no banco de dados, de acordo com o nome
    //ou com o sobrenome, passado como parâmetro
    public static List<Cliente> procurar(String valor) throws SQLException, Exception {
        //Monta a string de consulta de clientes no banco, utilizando
        //o valor passado como parâmetro para busca nas colunas de
        //nome ou sobrenome (através do "LIKE" e ignorando minúsculas
        //ou maiúsculas, através do "UPPER" aplicado à coluna e ao
        //parâmetro). Além disso, também considera apenas os elementos
        //que possuem a coluna de ativação de clientes configurada com
        //o valor correto ("enabled" com "true")
        String sql = "SELECT * FROM cliente WHERE ((UPPER(nome) LIKE UPPER(?) "
            + "OR UPPER(cpf) LIKE UPPER(?) OR UPPER(email) LIKE UPPER(?)) AND enabled=1)";
        //Lista de clientes de resultado
        List<Cliente> listaClientes = null;
        //Conexão para abertura e fechamento
        Connection connection = null;
        //Statement para obtenção através da conexão, execução de
        //comandos SQL e fechamentos
        PreparedStatement preparedStatement = null;
        //Armazenará os resultados do banco de dados
        ResultSet result = null;
        try {
            //Abre uma conexão com o banco de dados
            connection = ConnectionFactory.getConnetion();
            //Cria um statement para execução de instruções SQL
            preparedStatement = connection.prepareStatement(sql);
            //Configura os parâmetros do "PreparedStatement"
            preparedStatement.setString(1, "%" + valor + "%");
            preparedStatement.setString(2, "%" + valor + "%");
            preparedStatement.setString(3, "%" + valor + "%");            
            //Executa a consulta SQL no banco de dados
            result = preparedStatement.executeQuery();
            
            //Itera por cada item do resultado
            while (result.next()) {
                //Se a lista não foi inicializada, a inicializa
                if (listaClientes == null) {
                    listaClientes = new ArrayList<Cliente>();
                }
                //Cria uma instância de Cliente e popula com os valores do BD
                Cliente cliente = new Cliente();
                cliente.setId(result.getInt("id"));                
                cliente.setCpf(result.getString("cpf"));
                cliente.setNome(result.getString("nome"));
                Date d = new Date(result.getTimestamp("data_nascimento").getTime());
                cliente.setData_nascimento(d);                
                cliente.setTelefone(result.getString("telefone"));
                cliente.setEmail(result.getString("email"));                
                cliente.setAtivo(result.getInt("ativo"));
                //Adiciona a instância na lista
                listaClientes.add(cliente);
            }
        } finally {
            //Se o result ainda estiver aberto, realiza seu fechamento
            if (result != null && !result.isClosed()) {
                result.close();
            }
            //Se o statement ainda estiver aberto, realiza seu fechamento
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
            //Se a conexão ainda estiver aberta, realiza seu fechamento
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        //Retorna a lista de clientes do banco de dados
        return listaClientes;        
    }    
    
    public static Cliente obter(int id) throws SQLException, Exception {
        //Compõe uma String de consulta que considera apenas o cliente
        //com o ID informado e que esteja ativo ("enabled" com "true")
        String sql = "SELECT * FROM cliente WHERE (id=?)";

        //Conexão para abertura e fechamento
        Connection connection = null;
        //Statement para obtenção através da conexão, execução de
        //comandos SQL e fechamentos
        PreparedStatement preparedStatement = null;
        //Armazenará os resultados do banco de dados
        ResultSet result = null;
        try {
            //Abre uma conexão com o banco de dados
            connection = ConnectionFactory.getConnetion();
            //Cria um statement para execução de instruções SQL
            preparedStatement = connection.prepareStatement(sql);
            //Configura os parâmetros do "PreparedStatement"
            preparedStatement.setInt(1, id);            
            
            //Executa a consulta SQL no banco de dados
            result = preparedStatement.executeQuery();
            
            //Verifica se há pelo menos um resultado
            if (result.next()) {                
                //Cria uma instância de Cliente e popula com os valores do BD
                Cliente cliente = new Cliente();
                cliente.setId(result.getInt("id"));                
                cliente.setCpf(result.getString("cpf"));
                cliente.setNome(result.getString("nome"));
                Date d = new Date(result.getTimestamp("data_nascimento").getTime());
                cliente.setData_nascimento(d);                
                cliente.setTelefone(result.getString("telefone"));
                cliente.setEmail(result.getString("email"));                
                cliente.setAtivo(result.getInt("ativo"));
                //Retorna o resultado
                return cliente;
            }            
        } finally {
            //Se o result ainda estiver aberto, realiza seu fechamento
            if (result != null && !result.isClosed()) {
                result.close();
            }
            //Se o statement ainda estiver aberto, realiza seu fechamento
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
            //Se a conexão ainda estiver aberta, realiza seu fechamento
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }

        //Se chegamos aqui, o "return" anterior não foi executado porque
        //a pesquisa não teve resultados
        //Neste caso, não há um elemento a retornar, então retornamos "null"
        return null;
    }
}

   
