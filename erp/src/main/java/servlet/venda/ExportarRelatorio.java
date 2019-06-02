/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.venda;

import ads.pi3.DAO.FilialDAO;
import ads.pi3.DAO.VendaDAO;
import ads.pi3.model.Filial;
import ads.pi3.model.ItemVenda;
import ads.pi3.model.Usuario;
import ads.pi3.model.Venda;
import ads.pi3.utils.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 *
 * @author dbrito
 */
@WebServlet(name = "ExportarRelatorio", urlPatterns = {"/exportar-relatorio"})
public class ExportarRelatorio extends HttpServlet {

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
        
        //Recupero as vendas com base nos parametros repassados
        List<Venda> vendas = getVendas(request);

        //Defino os headers necessarios para entregar um excel
        ServletOutputStream buffer = response.getOutputStream();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=relatorio_vendas.xls");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet folha = workbook.createSheet();

        //Crio o cabeçalho da planilha
        HSSFRow cabecalho = folha.createRow(0);

        HSSFCell hData = cabecalho.createCell(0);
        hData.setCellValue("Data");
        hData.setCellStyle(getCellStyleHeader(workbook));
        HSSFCell hFilial = cabecalho.createCell(1);
        hFilial.setCellValue("Filial");
        hFilial.setCellStyle(getCellStyleHeader(workbook));
        HSSFCell hVendedor = cabecalho.createCell(2);
        hVendedor.setCellValue("Vendedor");
        hVendedor.setCellStyle(getCellStyleHeader(workbook));
        HSSFCell hTotal = cabecalho.createCell(3);
        hTotal.setCellValue("Total");
        hTotal.setCellStyle(getCellStyleHeader(workbook));
        HSSFCell hItens = cabecalho.createCell(4);
        hItens.setCellValue("Itens");
        hItens.setCellStyle(getCellStyleHeader(workbook));

        HSSFCellStyle alinhadoTopo = workbook.createCellStyle();
        alinhadoTopo = workbook.createCellStyle();
        alinhadoTopo.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
        //Adiciono as informações a planilha
        for (int i=0; i < vendas.size(); i++) {
            Venda venda = vendas.get(i);
            System.out.println(venda.getData()+ " | " + venda.getFilial().getNome());

            HSSFRow linha = folha.createRow(i+1);

            HSSFCell data = linha.createCell(0);
            data.setCellValue(venda.getDataFormatada());
            data.setCellStyle(alinhadoTopo);

            HSSFCell nome_filial = linha.createCell(1);
            nome_filial.setCellValue(venda.getFilial().getNome());
            nome_filial.setCellStyle(alinhadoTopo);

            HSSFCell nome_vendedor = linha.createCell(2);
            nome_vendedor.setCellValue(venda.getVendedor().getNome());
            nome_vendedor.setCellStyle(alinhadoTopo);

            HSSFCell total_venda = linha.createCell(3);
            total_venda.setCellValue(venda.getTotalFormatado());
            total_venda.setCellStyle(alinhadoTopo);

            HSSFCell itens = linha.createCell(4);
            String textoItens = "";
            List<ItemVenda> itensVenda = venda.getItens();
            for (int j=0; j < itensVenda.size(); j++) {
                ItemVenda item = itensVenda.get(j);
                if (j!=0) textoItens += "\n";
                textoItens += item.getQuantidade() + "x " + item.getProduto().getNome() + " (" + Utils.numToBrl(item.getPreco())+ ")";
            }
            System.out.println("-------");
            System.out.println(textoItens);
            itens.setCellStyle(getCellStyleItens(workbook));
            itens.setCellValue(textoItens);
        }
        folha.autoSizeColumn(0);
        folha.autoSizeColumn(1);
        folha.autoSizeColumn(2);
        folha.autoSizeColumn(3);
        folha.autoSizeColumn(4);        
        //Escrevo e é caixa !
        workbook.write(buffer);
        buffer.close();
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
            filial = FilialDAO.obter( Integer.valueOf(request.getParameter("filial_id")) );
        } catch (Exception ex) {}

        //Com os parametros decido qual metodo de busca utilizo
        try {
            Date menosTrinta = new Date();
            menosTrinta.setDate(menosTrinta.getDate() - 30);

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

    HSSFCellStyle getCellStyleItens(HSSFWorkbook workbook) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle = workbook.createCellStyle();
        HSSFFont hSSFFont = workbook.createFont();
        hSSFFont.setFontName("Consolas");
        // hSSFFont.setFontHeightInPoints((short) 8);
        hSSFFont.setItalic(true);
        hSSFFont.setColor(HSSFColor.GREY_40_PERCENT.index);
        cellStyle.setWrapText(true);
        cellStyle.setFont(hSSFFont);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
        return cellStyle;
    }

    HSSFCellStyle getCellStyleHeader(HSSFWorkbook workbook) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle = workbook.createCellStyle();
        HSSFFont hSSFFont = workbook.createFont();
        hSSFFont.setFontHeightInPoints((short) 16);
        hSSFFont.setColor(HSSFColor.BLACK.index);
        cellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle.setFont(hSSFFont);
        return cellStyle;
    }    
}
