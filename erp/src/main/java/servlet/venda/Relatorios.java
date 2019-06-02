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
        
        List<Venda> vendas = getVendas(request);                
        request.setAttribute("vendas", vendas);        
        Utils utils= new Utils();
        request.setAttribute("utils", utils);        
        List<Filial> filiais = FilialDAO.listar();
        request.setAttribute("filiais", filiais);        
        RequestDispatcher meuk = request.getRequestDispatcher("./venda/relatorios.jsp");
        meuk.forward(request, response);
    }
    
    List<Venda> getVendas(HttpServletRequest request) {
        Date inicio=null;
        Date fim=null;
        Filial filial = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<Venda> vendas = null;

        //Recupero os parametros
        try {
            inicio = sdf.parse(request.getParameter("data_inicio"));
            fim = sdf.parse(request.getParameter("data_fim"));
        } catch (Exception ex) {}
        try {
            filial = FilialDAO.obter( Integer.valueOf(request.getParameter("filial")) );
        } catch (Exception ex) {}

        //Com os parametros decido qual metodo de busca utilizo
        try {
            Date menosTrinta = new Date();
            menosTrinta.setDate(menosTrinta.getDate() - 364);

            if (inicio != null && fim != null && filial != null) { //Se passou de/até + Filial ? Filtra
                vendas = VendaDAO.pegaRelatório(inicio, fim, filial);
            } else if (inicio != null && fim != null) { //Se passou apenas o de/até ? Filtra
                vendas = VendaDAO.pegaRelatório(inicio, fim);
            } else if (filial != null) { //Se passou apenas o de/até ? Filtra
                vendas = VendaDAO.pegaRelatório(menosTrinta, new Date(), filial);
            } else { //Se não passou nada ? Traz tudo
                vendas = VendaDAO.pegaRelatório(menosTrinta, new Date());
            }
        } catch (Exception ex) {
            Logger.getLogger(ExportarRelatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vendas;
    }       

}
