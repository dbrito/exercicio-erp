/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.cliente;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ads.pi3.DAO.ClienteDAO;
import ads.pi3.model.Cliente;
import ads.pi3.model.Usuario;
import ads.pi3.utils.Utils;
import java.util.List;

/**
 *
 * @author ninck
 */
@WebServlet(urlPatterns = {"/clientes"})
public class Clientes extends HttpServlet {    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {                
        if (Utils.getCurrentUser(request) == null) {
            response.sendRedirect("login");
            return;
        }
                
        List<Cliente> teste = ClienteDAO.listar();                
        request.setAttribute("clientes", teste);        
        RequestDispatcher meuk = request.getRequestDispatcher("./cliente/clientes.jsp");
        meuk.forward(request, response);
    }    
}

