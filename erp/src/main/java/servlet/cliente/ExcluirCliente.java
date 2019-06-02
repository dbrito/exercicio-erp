/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.cliente;

import ads.pi3.DAO.ClienteDAO;
import ads.pi3.model.Cliente;
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
 * @author ninck
 */

@WebServlet(name = "ExcluirCliente", urlPatterns = {"/excluir-cliente"})
public class ExcluirCliente extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {                                 
        if (Utils.getCurrentUser(request) == null) {
            response.sendError(403, "Acesso negado");
            return;
        } 
        
        request.getParameterMap();        
        try {                    
            ClienteDAO.excluir(Integer.parseInt(request.getParameter("id")));                                               
        } catch (Exception ex) {
            response.sendError(404, ex.getMessage());
            Logger.getLogger(EditarCliente.class.getName()).log(Level.SEVERE, null, ex);
        }        
        PrintWriter resposta = response.getWriter();
        resposta.println("O cliente foi excluido com sucesso.");
    }       
}

