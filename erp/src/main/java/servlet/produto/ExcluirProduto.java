/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.produto;

import ads.pi3.DAO.ProdutoDAO;
import ads.pi3.model.Produto;
import ads.pi3.model.Usuario;
import ads.pi3.utils.Utils;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "ExcluirProduto", urlPatterns = {"/excluir-produto"})
public class ExcluirProduto extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {                                         
        Usuario user = Utils.getCurrentUser(request);
        if (user ==  null || user.getPerfil().equals("caixa")) {
            response.sendError(403, "Acesso negado");
            return;
        }
        
        try {                    
            ProdutoDAO.excluir(Integer.parseInt(request.getParameter("id")));                                               
        } catch (Exception ex) {
            response.sendError(404, ex.getMessage());
            Logger.getLogger(EditarProduto.class.getName()).log(Level.SEVERE, null, ex);
        }        
        PrintWriter resposta = response.getWriter();
        resposta.println("O produto foi excluido com sucesso.");
    }       
}
