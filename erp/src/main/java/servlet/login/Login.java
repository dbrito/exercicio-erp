/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.login;

import ads.pi3.DAO.ProdutoDAO;
import ads.pi3.DAO.UsuarioDAO;
import ads.pi3.model.Produto;
import ads.pi3.model.Usuario;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author dbrito
 */
@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {                        
        
        try {
            //Verifico se o usuário já está logado
            Usuario teste = (Usuario) request.getSession().getAttribute("funcionario");            
            if (teste !=  null) {
                //Caso esteja mando para a tela de produtos                
                if (teste.getPerfil().equals("caixa")) response.sendRedirect("clientes");
                else response.sendRedirect("produtos");                
                return;
            }            
        } catch(Exception e) {
            System.out.print(e);
        }
        
        RequestDispatcher redir = request.getRequestDispatcher("login.jsp");
        redir.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {                
        String user, pass;
        Usuario usuario = null;
        try {
            user = request.getParameter("user");
            pass = request.getParameter("pass");            
            usuario = UsuarioDAO.efetuarLogin(user, pass);
        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (usuario == null) {
            response.sendError(500, "Login invalido, verifique o seu usuario e senha.");
            return;
        }        
        HttpSession sessao = request.getSession(true);
        sessao.setAttribute("funcionario", usuario);
        
        PrintWriter resposta = response.getWriter();
        resposta.println("Login efetuado com sucesso.");
    }

}
