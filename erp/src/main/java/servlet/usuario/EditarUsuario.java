/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Douglas
 */
@WebServlet(name = "EditarUsuario", urlPatterns = {"/editar-usuario"})
public class EditarUsuario extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {                 
        Usuario user = Utils.getCurrentUser(request);
        if (user == null) {
            response.sendRedirect("login");
            return;
        } else if (!user.getPerfil().equals("gerente")){
            response.sendRedirect(request.getContextPath() + "/acesso-negado.html");
            return;
        }   
        
        Usuario funcionario = null;
        List<Filial> filiais = null;
        try {        
            funcionario = UsuarioDAO.obter(Integer.parseInt(request.getParameter("id")));
            filiais = FilialDAO.listar();
        } catch (Exception ex) {
            response.sendError(404, "Usuário não encontrado");
            Logger.getLogger(EditarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("user", funcionario);
        request.setAttribute("filiais", filiais);
        RequestDispatcher meuk = request.getRequestDispatcher("./usuario/editar-usuario.jsp");
        meuk.forward(request, response);                                        
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {                 
        Usuario user = Utils.getCurrentUser(request);
        if (user ==  null || !user.getPerfil().equals("gerente")) {
            response.sendError(403, "Acesso negado");
            return;
        }                
        
        Usuario funcionario;
        try {        
            funcionario = UsuarioDAO.obter(Integer.parseInt(request.getParameter("id")));
        } catch (Exception ex) {
            response.sendError(404, "Usuário não encontrado");
            Logger.getLogger(EditarUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
                                
        try {
            funcionario.setNome(request.getParameter("nome"));        
            funcionario.setCpf(request.getParameter("cpf"));        
            funcionario.setUser(request.getParameter("user"));        
            funcionario.setPass(request.getParameter("pass"));              
            funcionario.setPerfil(request.getParameter("perfil"));
            Filial filial;
            filial = FilialDAO.obter(Integer.parseInt(request.getParameter("filial")));
            funcionario.setFilial(filial);
            
            UsuarioDAO.atualizar(funcionario);
        } catch (Exception ex) {
            response.sendError(503, ex.toString());
            Logger.getLogger(EditarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PrintWriter resposta = response.getWriter();
        resposta.println("O usuario '" + user.getNome() + "' foi atualizado com sucesso.");
    }

}
