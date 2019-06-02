<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Realizar Venda</title>

        <style>
            <%@include file="../css/geral.css" %>
        </style>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" >

        <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    </head>
    <body>
        <%@include file="../partials/menu.jsp" %>

        <div class="content">
            <h1><i class="fa fa-shopping-cart fa-lg"></i> Realizar Venda</h1>
            <form action="/realizar-venda" method="post" id="formulario">
                <div class="row">
                    <div class="form-group col-md-8">
                        Cliente
                        <input type="text" class="form-control col-md-5" disabled name="nomeCliente" id="nomeCliente" required>

                        <a style="margin-top:10px" href="#" data-toggle="modal" data-target="#modalClientes" class="btn btn-secondary" id="selecionar-usuario" role="button">Selecionar Cliente</a>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-10">
                        Produtos
                        <table class="table table-hover col-md-6 lista-produtos" style="width: 100%;">
                            <thead class="thead-dark">
                                <tr>
                                    <th scope="col">Nome</th>
                                    <th scope="col">Preço</th>
                                    <th scope="col">Categoria</th>
                                    <th scope="col">Quantidade</th>
                                    <th scope="col">Ações</th>
                                </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                        <strong id="total-compra">Total: R$</strong>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-10">
                        <a style="margin-top:10px" href="#" data-toggle="modal" data-target="#modalProdutos" class="btn btn-secondary" id="selecionar-usuario" role="button">Adicionar Produto</a>
                    </div>
                </div>
                <input type="submit" class="btn btn-primary btn-lg" value="Realizar Venda">
            </form>

            <div class="modal fade" id="modalProdutos" tabindex="-1" role="dialog" aria-labelledby="labelModalProdutos" aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="labelModalProdutos">Produtos</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <table class="table table-hover col-md-6" style="width: 100%;">
                                <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">Nome</th>
                                        <th scope="col">Preço</th>
                                        <th scope="col">Categoria</th>
                                        <th scope="col">Quantidade</th>
                                        <th scope="col">Ações</th>
                                    </tr>
                                </thead>
                                <c:forEach var="prod" items="${produtos}">
                                    <tr>
                                        <td><c:out value="${prod.getNome()}" /></td>
                                        <td><c:out value="R$ ${prod.getPrecoFormatado()}" /></td>
                                        <td><c:out value="${prod.getCategoria()}" /></td>
                                        <td>
                                            <input style="width:80px" class="form-control" type="number" name="quantidade" value="1" min="1" max="<c:out value="${prod.getQuantidade()}" />" dt-id="<c:out value="${prod.getId()}" />">
                                        </td>
                                        <td>
                                            <a class="add-item"href="#" title="Adicionar Produto" dt-price="<c:out value="${prod.getPreco()}"/>" dt-id="<c:out value="${prod.getId()}" />"><i class="fa fa-plus-circle fa-lg"></i></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="modalClientes" tabindex="-1" role="dialog" aria-labelledby="labelModalClientes" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="labelModalClientes">Clientes</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <table class="table table-hover col-md-6" style="width: 100%;">
                                <thead>
                                    <tr>
                                        <th scope="col">Nome</th>
                                        <th scope="col">CPF</th>
                                        <th scope="col">Ações</th>
                                    </tr>
                                </thead>
                                <c:forEach var="prod" items="${clientes}">
                                    <tr>
                                        <td><c:out value="${prod.getNome()}" /></td>
                                        <td><c:out value="${prod.getCpf()}" /></td>
                                        <td>
                                            <button type="button" onclick="setClienteId(<c:out value="${prod.getId()}" />, '<c:out value="${prod.getNome()}" />'); return false;" data-dismiss="modal" class="btn btn-secondary btn-sm">Selecionar Cliente</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <script>
                var venda = {};
                venda.produtos = [];

                function setClienteId(id, clienteNome) {
                    $('#nomeCliente').val(clienteNome);
                    venda.clienteId = id;
                }

                //Lógica para adicionar os produtos
                $('.add-item').click(function (e) {
                    e.preventDefault();
                    var produto = e.currentTarget;
                    var elementoProduto = produto.parentNode.parentNode.cloneNode(true);
                    var novoBotao = elementoProduto.children[4].children[0];
                    var quantidade = elementoProduto.children[3].children[0];
                    novoBotao.setAttribute('class', 'remove-item');
                    quantidade.setAttribute('disabled', 'disabled');
                    novoBotao.children[0].setAttribute('class', 'fa fa-trash fa-lg');

                    var produto = {
                        id: produto.getAttribute('dt-id'),
                        preco: Number(produto.getAttribute('dt-price')),
                        quantidade: parseInt(elementoProduto.children[3].children[0].value)
                    }

                    var produtoExistente = produtoFoiAdicionado(produto.id);
                    console.log(produtoExistente);
                    if (produtoExistente) {
                        produtoExistente.quantidade += produto.quantidade;
                        $('.lista-produtos tbody input[dt-id="'+ produto.id +'"]').val(produtoExistente.quantidade);
                        $('#total-compra').text('Total: R$' + pegaTotalCompra());
                        return;
                    }

                    $('.lista-produtos tbody').append(elementoProduto);
                    venda.produtos.push(produto);
                    $('#total-compra').text('Total: R$' + pegaTotalCompra());

                    $('.remove-item').click(function (e) {
                        e.preventDefault();
                        var produto = $(e.currentTarget);

                        for(var i = venda.produtos.length - 1; i >= 0; i--) {
                            var item = venda.produtos[i];
                            if(venda.produtos[i].id == produto.attr('dt-id')) {
                                venda.produtos.splice(i, 1);
                                produto.parent().parent().remove();
                                console.log(venda.produtos);
                                $('#total-compra').text('Total: R$' + pegaTotalCompra());
                            }
                        }
                    });
                });

                function produtoFoiAdicionado(id) {
                    for (var i=0;i < venda.produtos.length; i++) {
                        var produto = venda.produtos[i];
                        if (produto.id == id) return produto;
                    }
                    return false;
                }

                function pegaTotalCompra() {
                    var total = 0;
                    for (var i=0;i < venda.produtos.length; i++) {
                        var produto = venda.produtos[i];
                        total += produto.preco * produto.quantidade;
                    }
                    return total;
                }

                $("#formulario").submit(function (e) {
                    e.preventDefault();
                    if (!venda.clienteId) {
                        alert('Selecione um cliente');
                        return;
                    }
                    if (venda.produtos.length == 0) {
                        alert('Selecione ao menos um produto');
                        return;
                    }

                    $.ajax({
                        type: "POST",
                        url: window.location.href,
                        data: {"jsonData": JSON.stringify(venda)},
                        success: function (result, status) {
                            alert(result);
                            if (status == 'success') {
                                window.location.reload(false);
                            }
                        }, error: function (err) {
                            alert('Erro tente novamente mais tarde;')
                        }
                    });
                });
            </script>
        </div>

    </body>
</html>
