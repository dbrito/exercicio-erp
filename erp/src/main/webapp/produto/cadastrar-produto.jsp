<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cadastrar Produto</title>

        <style>
            <%@include file="../css/geral.css" %>
        </style>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" >

        <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
        <script src="https://cdn.rawgit.com/plentz/jquery-maskmoney/master/dist/jquery.maskMoney.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    </head>
    <body>
        <%@include file="../partials/menu.jsp" %>

        <div class="content">
            <h1><i class="fa fa-edit fa-lg"></i> Cadastrar Produto</h1>
            <form action="/produtos" method="post" id="formulario">
                <div class="row">
                    <div class="form-group col-md-3">
                        <label for="nome">Nome:</label>
                        <input type="text" class="form-control" name="nome" id="nome" required>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="marca">Marca:</label>
                        <input type="text" class="form-control" name="marca" id="marca" required>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-3">
                        <label for="preco">Preço (R$):</label>
                        <input type="text" class="form-control" name="preco" id="preco"/ required>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="quantidade">Quantidade (estoque):</label>
                        <input type="number" class="form-control" name="quantidade" id="quantidade" required>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-3">
                        <label for="descricao">Descrição:</label>
                        <textarea class="form-control" name="descricao" id="descricao"></textarea>
                    </div>
                    <div class="form-group col-md-3">
                        <label for="categoria">Categoria:</label>
                        <select class="form-control" name="categoria" id="categoria">
                            <option value="Perfume">Perfume</option>
                            <option value="Sabonete">Sabonete</option>
                        </select>
                    </div>
                </div>
                <input type="submit" class="btn btn-info" value="Salvar">
                <!-- <a href="#" class="btn btn-info" role="button">Cancelar</a> -->
            </form>
            <script>
                $('#formulario').click(function () { console.log('here') })
                $("#formulario").submit(function (e) {
                    e.preventDefault();
                    $.ajax({
                        type: "POST",
                        url: window.location.href,
                        data: $("#formulario").serialize(),
                        success: function (result, status) {
                            alert(result);
                            if (status == 'success') {
                                $('#nome').val('');
                                $('#marca').val('');
                                $('#preco').val('');
                                $('#quantidade').val('');
                                $('#descricao').val('');
                            }
                        }, error: function (err) {
                            alert('Erro tente novamente mais tarde;')
                        }
                    });
                });

                $("#preco").maskMoney({
                    prefix: "",
                    decimal: ",",
                    thousands: "."
                });
            </script>
        </div>

    </body>
</html>
