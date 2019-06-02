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


@WebServlet(name = "CadastrarUsuario", urlPatterns = {"/cadastrar-usuario"})
public class CadastrarUsuario extends HttpServlet {

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
        
        List<Filial> filiais = FilialDAO.listar();
        request.setAttribute("filiais", filiais);
        RequestDispatcher meuk = request.getRequestDispatcher("./usuario/cadastrar-usuario.jsp");
        meuk.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {                 
        Usuario user = Utils.getCurrentUser(request);
        if (user ==  null || !user.getPerfil().equals("gerente")) {
            response.sendError(403, "Acesso negado");
            return;
        }
        
        try {
            Usuario novoUser = new Usuario();
            novoUser.setNome(request.getParameter("nome"));        
            novoUser.setCpf(request.getParameter("cpf"));      
            novoUser.setUser(request.getParameter("user"));        
            novoUser.setPass(request.getParameter("pass"));        
            novoUser.setPerfil(request.getParameter("perfil"));        
            Filial filial;
            filial = FilialDAO.obter(Integer.parseInt(request.getParameter("filial")));
            novoUser.setFilial(filial);
            UsuarioDAO.inserir(novoUser);
            PrintWriter resposta = response.getWriter();
            resposta.println("O usuario '" + novoUser.getUser()+ "' foi cadastrado com sucesso.");
        } catch (Exception ex) {
            response.sendError(500, "Erro ao cadastrar o usuario");
            Logger.getLogger(CadastrarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        
    }

}
