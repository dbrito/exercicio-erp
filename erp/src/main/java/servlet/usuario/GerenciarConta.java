package servlet.usuario;

import ads.pi3.DAO.FilialDAO;
import ads.pi3.DAO.UsuarioDAO;
import ads.pi3.model.Filial;
import ads.pi3.model.Usuario;
import ads.pi3.utils.Utils;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author dbrito
 */
@WebServlet(name = "GerenciarConta", urlPatterns = {"/gerenciar-conta"})
public class GerenciarConta extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {                 
        Usuario user = Utils.getCurrentUser(request);
        if (user == null) {
            response.sendRedirect("login");
            return;
        }        
                
        List<Filial> filiais = null;
        try {                    
            filiais = FilialDAO.listar();
        } catch (Exception ex) {
            response.sendError(404, "Sem filiais");
            Logger.getLogger(EditarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("user", user);
        request.setAttribute("filiais", filiais);
        RequestDispatcher meuk = request.getRequestDispatcher("./usuario/gerenciar-conta.jsp");
        meuk.forward(request, response);                                        
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {                 
        Usuario user = Utils.getCurrentUser(request);
        if (user == null) {
            response.sendError(403, "Acesso negado");
            return;
        }
                                
        try {            
            user.setUser(request.getParameter("user"));        
            user.setPass(request.getParameter("pass"));                                                              
            UsuarioDAO.atualizar(user);
        } catch (Exception ex) {
            response.sendError(503, ex.toString());
            Logger.getLogger(EditarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PrintWriter resposta = response.getWriter();
        resposta.println("O usuario '" + user.getNome() + "' foi atualizado com sucesso.");
    }  

}
