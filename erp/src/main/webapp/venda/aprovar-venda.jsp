<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Relatórios</title>

        <style>
            <%@include file="../css/geral.css" %>
        </style>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" >
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">


        <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    </head>
    <body>
        <%@include file="../partials/menu.jsp" %>

        <div class="content">
            <h1><i class="fa fa-list fa-lg"></i> Aprovar Vendas</h1>            
            <style>
                table {
                    font-family: consolas;
                }
                thead tr td {
                    font-weight: bold;
                }
                .products {
                    font-style:italic;
                    font-size:12px;
                }
            </style>
            <div class="row">
                <div class="col-md-12"><table class=" table table-bordered table-hover">
                    <thead>
                        <tr>
                            <td>Data</td>
                            <td>Filial</td>
                            <td>Vendedor</td>
                            <td>Total</td>
                            <td>Itens</td>
                            <td>Aprovar</td>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="venda" items="${vendas}">
                            <tr>
                                <td><c:out value="${venda.getDataFormatada()}" /></td>
                                <td><c:out value="${venda.getFilial().getNome()}" /></td>
                                <td><c:out value="${venda.getVendedor().getNome()}" /></td>
                                <td><c:out value="${venda.getTotalFormatado()}" /></td>
                                <td class='products'>
                                    <c:forEach var="item" items="${venda.getItens()}">
                                        <c:out value="${item.getQuantidade()}" />x
                                        <c:out value="${item.getProduto().getNome()}" />
                                        (<c:out value="${utils.numToBrl(item.getPreco())}" />)
                                        <br>
                                    </c:forEach>
                                </td>
                                <td>
                                    <button type="button" onclick="aprovarVenda(<c:out value="${venda.getId()}" />); return false;" data-dismiss="modal" class="btn btn-secondary btn-sm">Aprovar Venda</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table></div>
                <div class="form-group col-md-2">
                    <input style="margin-top:32px;" type="submit" id="exportar" class="btn btn-info" value="Exportar Relatório">
                </div>
            </div>
        </div>

        <script>
            function aprovarVenda(vendaId) {
                $.ajax({
                    type: "POST",
                    url: window.location.href,
                    data: {"vendaId": vendaId},
                    success: function (result, status) {
                        alert(result);
                        if (status == 'success') {
                            window.location.reload(false);
                        }
                    }, error: function (err) {
                        alert('Erro tente novamente mais tarde;')
                    }
                });
            }
        </script>
    </body>
</html>
