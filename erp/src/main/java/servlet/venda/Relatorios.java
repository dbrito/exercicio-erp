/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.venda;

import ads.pi3.DAO.FilialDAO;
import ads.pi3.DAO.VendaDAO;
import ads.pi3.model.Filial;
import ads.pi3.model.Usuario;
import ads.pi3.model.Venda;
import ads.pi3.utils.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
@WebServlet(name = "Relatorios", urlPatterns = {"/relatorios"})
public class Relatorios extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {                         
        try {
            //Caso o usuário não esteja logado redireciono para a tela de login
            Usuario user = (Usuario) request.getSession().getAttribute("funcionario");
            if (user ==  null) {
                response.sendRedirect("login");
                return;
            }
            //Caso o usuário esteja loga mas não seja um gerente redireciona para a tela de acesso negado
            else if (!user.getPerfil().equals("gerente")){
                response.sendRedirect(request.getContextPath() + "/acesso-negado.html");
                return;
            }
        } catch(Exception e) {}
        
        try {        
            request.setAttribute("vendasAprovadas", getVendasAprovadas(request));
        } catch (Exception ex) {
            Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {        
            request.setAttribute("vendasReprovadas", getVendasReprovadas(request));
        } catch (Exception ex) {
            Logger.getLogger(Relatorios.class.getName()).log(Level.SEVERE, null, ex);
        }
        Utils utils= new Utils();
        request.setAttribute("utils", utils);        
        List<Filial> filiais = FilialDAO.listar();
        request.setAttribute("filiais", filiais);        
        RequestDispatcher meuk = request.getRequestDispatcher("./venda/relatorios.jsp");
        meuk.forward(request, response);
    }
    
    List<Venda> getVendasAprovadas(HttpServletRequest request) throws Exception {
        List<Venda> vendas = null;
        vendas = VendaDAO.pegaRelatórioA();
        return vendas;
    }
    
    List<Venda> getVendasReprovadas(HttpServletRequest request) throws Exception {
        List<Venda> vendas = null;

        vendas = VendaDAO.pegaRelatórioR();
        return vendas;
    }

}
