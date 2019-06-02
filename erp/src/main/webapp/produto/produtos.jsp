<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Gerenciamento de Produtos</title>

        <style>
            <%@include file="../css/geral.css" %>
        </style>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" >

        <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    </head>
    <body>
        <%@include file="../partials/menu.jsp" %>

        <div class="content">
            <h1><i class="fa fa-edit fa-lg"></i> Gerenciar Produtos</h1>
            <table class="table table-hover" >
                <thead >
                    <tr>
                        <th scope="col">Nome</th>
                        <th scope="col">Marca</th>
                        <th scope="col">Preço</th>
                        <th scope="col">Quantidade</th>
                        <th scope="col">Categoria</th>
                        <th scope="col">Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="prod" items="${produtos}">
                        <tr>
                            <td><c:out value="${prod.getNome()}" /></td>
                            <td><c:out value="${prod.getMarca()}" /></td>
                            <td><c:out value="R$ ${prod.getPrecoFormatado()}" /></td>
                            <td><c:out value="${prod.getQuantidade()}" /></td>
                            <td><c:out value="${prod.getCategoria()}" /></td>
                            <td>
                                <a href="./editar-produto?id=<c:out value="${prod.getId()}" />" title="Editar"><i class="fa fa-edit fa-lg"></i></a>
                                <a class="remove-item"href="#" title="Excluir" dt-id="<c:out value="${prod.getId()}" />"><i class="fa fa-trash fa-lg"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
                <script>
                    $('.remove-item').click(function (e) {
                        e.preventDefault();        
                        var prod = e.currentTarget;                        
                        var nomeProduto = prod.parentNode.parentNode.children[0].innerText;
                        var excluir = confirm('Deseja excluir o produto "'+nomeProduto+'" ?');
                        if(!excluir) return;
                                
                        window.currentProd = prod;
                        $.ajax({
                            type: "POST",
                            url: './excluir-produto',
                            data: {id : prod.getAttribute('dt-id')},
                            success: function (result, status) {
                                //alert(result);     
                                $(window.currentProd.parentNode.parentNode).hide();
                            }, error: function (err) {
                                alert('Erro tente novamente mais tarde;')
                            }
                        });
                            
                    });
                    
                </script>
            </table>
        </div>

    </body>
</html>
