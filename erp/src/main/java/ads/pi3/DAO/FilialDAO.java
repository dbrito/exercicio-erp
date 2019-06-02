package ads.pi3.DAO;

import ads.pi3.model.Filial;
import ads.pi3.utils.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FilialDAO {
    
    private static List<Filial> listaFiliais = new ArrayList<Filial>();
    
    // Iniciando a conexão com o banco.
    public static void inserir (Filial filial){
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        
        try {
            // Inserindo os valores para o Banco com o parâmetro.
            stmt = con.prepareStatement("INSERT INTO filial (nome, endereco, numero, cidade, estado) VALUES(?,?,?,?,?)");
            // Passando os dados para o insert.            
            stmt.setString(1, filial.getNome());
            stmt.setString(2, filial.getEndereco());
            stmt.setInt(3, filial.getNumero());
            stmt.setString(4, filial.getCidade());
            stmt.setString(5, filial.getEstado());
            stmt.execute();            
        } catch (SQLException ex) {
            System.out.print(ex);
            Logger.getLogger(FilialDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        
 }
    
    public static void atualizar(Filial filial) throws SQLException, Exception {
        //Monta a string de atualização do cliente no BD, utilizando
        //prepared statement
        String sql = "UPDATE filial SET nome=?, endereco=?, numero=?, cidade=?, estado=? "
            + "WHERE (id=?)";
        //Conexão para abertura e fechamento
        Connection connection = null;
        //Statement para obtenção através da conexão, execução de
        //comandos SQL e fechamentos
        PreparedStatement preparedStatement = null;
        try {
            //Abre uma conexão com o banco de dados
            connection = ConnectionFactory.getConnetion();
            //connection = ConnectionUtils.getConnection();
            //Cria um statement para execução de instruções SQL
            preparedStatement = connection.prepareStatement(sql);
            //Configura os parâmetros do "PreparedStatement"
            
            preparedStatement.setString(1, filial.getNome());
            preparedStatement.setString(2, filial.getEndereco());
            preparedStatement.setInt(3, filial.getNumero());
            preparedStatement.setString(4, filial.getCidade());            
            preparedStatement.setString(5, filial.getEstado());
            preparedStatement.setInt(6, filial.getId());
            
            
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
     
    public static void excluir(int id) throws SQLException, Exception {
        //Monta a string de atualização do cliente no BD, utilizando
        //prepared statement
        String sql = "UPDATE filial SET ativo=0 WHERE (id=?)";
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
     
    // listar as filiais
    public static List <Filial>  listar (){
        Connection con = ConnectionFactory.getConnetion();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Filial> filiais = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM filial where ativo=1");
            rs = stmt.executeQuery();
            while (rs.next()) {                
                Filial filial = new Filial();
                filial.setId(rs.getInt("id"));                
                filial.setNome(rs.getString("nome"));
                filial.setEndereco(rs.getString("endereco"));
                filial.setNumero(rs.getInt("numero"));
                filial.setCidade(rs.getString("cidade"));
                filial.setEstado(rs.getString("estado"));
                
                filiais.add(filial);
            }
        } catch (SQLException ex) {
            System.out.print("Não foi possivel listar!");
            ex.printStackTrace();
            Logger.getLogger(FilialDAO.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(, "");
            
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        return filiais;
    }
    
    //Procura um cliente no banco de dados, de acordo com o nome
    //ou com o sobrenome, passado como parâmetro
    public static List<Filial> procurar(String valor) throws SQLException, Exception {
        //Monta a string de consulta de clientes no banco, utilizando
        //o valor passado como parâmetro para busca nas colunas de
        //nome ou sobrenome (através do "LIKE" e ignorando minúsculas
        //ou maiúsculas, através do "UPPER" aplicado à coluna e ao
        //parâmetro). Além disso, também considera apenas os elementos
        //que possuem a coluna de ativação de clientes configurada com
        //o valor correto ("enabled" com "true")
        String sql = "SELECT * FROM filial WHERE ((UPPER(nome) LIKE UPPER(?) "
            + "OR UPPER(id) LIKE UPPER(?) OR UPPER(cidade) LIKE UPPER(?)))";
        //Lista de clientes de resultado
        List<Filial> listaFiliais = null;
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
                
                if (listaFiliais == null) {
                    listaFiliais = new ArrayList<>();
                }
                
                //Se a lista não foi inicializada, a inicializa
                //Cria uma instância de Cliente e popula com os valores do BD
                Filial filial = new Filial();
                filial.setId(result.getInt("id"));
                filial.setNome(result.getString("nome"));
                filial.setEndereco(result.getString("endereco"));
                filial.setNumero(result.getInt("numero"));
                filial.setCidade(result.getString("cidade"));
                filial.setEstado(result.getString("estado"));
                
                //Adiciona a instância na lista
                listaFiliais.add(filial);
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
        return listaFiliais;        
    }    
    
    public static Filial obter(int id) throws SQLException, Exception {
        //Compõe uma String de consulta que considera apenas o cliente
        //com o ID informado e que esteja ativo ("enabled" com "true")
        String sql = "SELECT * FROM filial WHERE (id=?)";

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
                Filial filial = new Filial();
                filial.setId(result.getInt("id"));
                filial.setNome(result.getString("nome"));
                filial.setEndereco(result.getString("endereco"));                
                filial.setNumero(result.getInt("numero"));
                filial.setCidade(result.getString("cidade"));
                filial.setEstado(result.getString("estado"));
                
                                
                //Retorna o resultado
                return filial;
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


    

