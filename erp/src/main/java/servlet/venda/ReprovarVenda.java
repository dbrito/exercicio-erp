package servlet.venda;

import ads.pi3.DAO.ClienteDAO;
import ads.pi3.DAO.ProdutoDAO;
import ads.pi3.DAO.VendaDAO;
import ads.pi3.model.Cliente;
import ads.pi3.model.Produto;
import ads.pi3.model.Usuario;
import ads.pi3.model.Venda;
import ads.pi3.utils.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Douglas
 */
@WebServlet(name = "ReprovarVenda", urlPatterns = {"/reprovar-venda"})
public class ReprovarVenda extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {                                 
        Usuario user = Utils.getCurrentUser(request);
        if (user == null) {
            response.sendError(403, "Acesso negado");
            return;
        }        
        
        try {
            int vendaId = Integer.parseInt(request.getParameter("vendaId"));                        
            VendaDAO.reprovar(vendaId);
            PrintWriter resposta = response.getWriter();
            resposta.println("A venda foi reprovada com sucesso!");
        } catch (Exception ex) {
            response.sendError(503, ex.getMessage());
            Logger.getLogger(RealizarVenda.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
}
