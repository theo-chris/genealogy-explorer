<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mortu
  Date: 21/12/2018
  Time: 20:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Person List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
</head>

<body class="p-1">

<div class="container-fluid">
    <h2>List of Persons</h2>
    <div class="input-group mb-3">
        <div class ="input-group-prepend">
            <select class="custom-select" id="selection">
                <option selected value="0">Key</option>
                <option value="1">Name</option>
            </select>
        </div>
        <input type="text" id="tableInput" class="form-control" onkeyup="tableFunction()" placeholder="Search...">
    </div>
    <table  class="table table-hover table-responsive-md" id="dataTable">
        <thead class="thead-dark">
        <tr>
            <th>Key</th>
            <th>Name</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${persons}" var="person" varStatus="itr">
            <tr>
                <td>${person.getKey()}</td>
                <td>${person.getName()}</td>
                <td><a href="/GE/person/get/${person.getKey()}" class="btn btn-primary">Details</a></td>
                <td><a href="/GE/person/edit/${person.getKey()}" class="btn btn-primary">Edit</a></td>
                <td><a href="/GE/person/ancestors/${person.getKey()}" class="btn btn-primary">See Ancestors</a></td>
                <td><a href="/GE/person/descendants/${person.getKey()}" class="btn btn-primary">See Descendants</a></td>
                <td><a href="/GE/person/delete/${person.getKey()}" class="btn btn-primary">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>

    </table>
    <a href="/GE/person/create" class="btn btn-primary p-1">Add person</a>
    <a href="/GE/person/familyTree" class="btn btn-primary p-1">Show Family Tree</a>
    <a href="/GE/person/persons" class="btn btn-primary p-1">Show All Persons Paginated</a>
    <a href="/GE/person/printJSON" class="btn btn-primary p-1">Print Paginated JSON</a>


</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

 <script>
    function tableFunction(){
        var filter,table,td,row,input,textValue,counter;

        input = document.getElementById("tableInput");
        filter= input.value.toUpperCase();
        table = document.getElementById("dataTable");
        row = table.getElementsByTagName("tr");

        for(counter = 0; counter < row.length; counter++){
            td = row[counter].getElementsByTagName("td")[$("#selection").val()];
            if (td){
                textValue = td.textContent || td.innerText;
                if (textValue.toUpperCase().indexOf(filter)> -1){
                    row[counter].style.display="";

                }else{
                    row[counter].style.display="none";
                }
            }
        }
    }
</script>
<%--tree--%>
<script>
    $("#btnTree").click(function () {
        $.ajax({
            type: "GET",
            url: "/GE/person/descendants/"+$("#treeInput").val(),
            dataType: "json",
            success: function (result) {
                var object = JSON.stringify(result);

                var x = JSON.parse(object);

                var keys = [];
                for(var k in x){

                    if (k == "key"){
                        alert(k);
                    }
                 }
                $("#tree").html(keys.toString());
            }
            ,
            error:function () {
                alert("FAIL");
            }
        });
    });
</script>

</body>
</html>
