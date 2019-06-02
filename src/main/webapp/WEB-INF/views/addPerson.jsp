<%--
  Created by IntelliJ IDEA.
  User: mortu
  Date: 29/12/2018
  Time: 12:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Add Person</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <style>
        .display{
            display: none;
        }
    </style>

</head>
<body>
<div class="container-fluid">

<form action="/GE/person/add" method="get" id="myForm">
    <table class="table table-hover table-responsive-md">
        <tbody>
            <tr><th>Key</th><td><input type="number" min="1" id="key" name="key" required="required" ></td></tr>
            <tr><th>Name</th><td><input type="text" name="name" required="required"></td></tr>
            <tr><th>Date of Birth</th><td><input type="number" min="0" name="dob"></td></tr>
            <tr><th>Mother Key</th><td><input type="number" min="0" name="m" id="motherID" class="check"> </td></tr>
            <tr><th>Father Key</th><td><input type="number" min="0" name="f" id="fatherID" class="check"></td></tr>
            <tr><th>Gender</th><td><input type="text" name="g"></td></tr>
            <tr><td colspan="2"><input type="submit" id="submit" value="Add" class="btn btn-success" onclick="myFunction()"> <a href="/GE/person/" class="btn btn-primary">Back</a></tr>
        </tbody>
    </table>
    <span id="errorField" class="display" style="color:red">Key exist!</span>
    <span id="errorField2" class="display" style="color:red">Mother with that ID doesnt exist</span>
    <span id="errorField3" class="display" style="color:red">Father with that ID doesnt exist!</span>

    <span id="errorField4" class="display" style="color:red;">Mother and Father ids cant be the same!</span>

</form>


    <%--<script>--%>

        <%--$(document).ready(function(){--%>
            <%--$("#motherID").change(function () {--%>
                <%--var motherKey = $(this);--%>
                <%--var fatherKey = $("#fatherID").val();--%>
                <%--if (motherKey== 1){--%>
                    <%--$('#errorField').html("Mother key and father key cannot be the same!");--%>
                    <%--$('#errorField').show();--%>
                    <%--$("#submit").prop('disabled', true);--%>
                <%--}else{--%>
                    <%--$('#errorField').hide();--%>
                    <%--$("#submit").prop('disabled', false);--%>
                <%--}--%>

            <%--})--%>
        <%--});--%>

    <%--</script>--%>


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#key").change(function(){
                $.ajax({
                    type: "GET",
                    url: "/GE/person/keyAvailability",
                    data:{key:$("#key").val()},
                    success: function(result){

                        var ret=jQuery.parseJSON(result);

                        if(ret.thekey==true){

                            $('#errorField').removeClass("display");
                            $("#submit").attr('disabled', true);
                        }else{
                            $('#errorField').addClass("display");
                            if (!$('#errorField2').hasClass("display")){
                                if (!$('#errorField3').hasClass("display")){
                                    if (!$('#errorField4').hasClass("display")){
                                        $("#submit").attr('disabled', false);
                                    }
                                }
                            }
                        }
                    },
                    error: function(){
                        alert("Failed");
                    }
                });
            });
        });
        $(document).ready(function () {
            $("#motherID").change(function () {
                $.ajax({
                    type: "GET",
                    url: "/GE/person/keyAvailability",
                    data: {key: $("#motherID").val()},
                    success: function (result) {
                        var res = jQuery.parseJSON(result);

                        if (res.thekey != true) {
                            $('#errorField2').removeClass("display");
                            $("#submit").attr('disabled', true);
                        } else {
                            $('#errorField2').addClass("display");
                            if (!$('#errorField2').hasClass("display")){
                                if (!$('#errorField3').hasClass("display")){
                                    if (!$('#errorField4').hasClass("display")){
                                        $("#submit").attr('disabled', false);
                                    }
                                }
                            }                         }

                    },
                    error: function () {
                        alert("Failed");
                    }
                });

            });
        });
        $(document).ready(function () {
            $("#fatherID").change(function () {
                $.ajax({
                    type: "GET",
                    url: "/GE/person/keyAvailability",
                    data: {key: $("#fatherID").val()},
                    success: function (result) {
                        var res = jQuery.parseJSON(result);

                        $('#errorField3').hide();
                        if (res.thekey != true) {
                            $('#errorField3').removeClass("display");
                            $("#submit").attr('disabled', true);
                        } else {
                            $('#errorField3').addClass("display");
                            if (!$('#errorField2').hasClass("display")){
                                if (!$('#errorField3').hasClass("display")){
                                    if (!$('#errorField4').hasClass("display")){
                                        $("#submit").attr('disabled', false);
                                    }
                                }
                            }
                        }
                    },
                    error: function () {
                        alert("Failed");
                    }
                });
            });
        });
        $(".check").change(function () {
            var m = $("#motherID").val();
            var f = $("#fatherID").val();

            if (m === f && m!=="" && f !=="") {
                $('#errorField4').removeClass("display");
                $("#submit").attr('disabled', true);
            } else {
                $('#errorField4').addClass("display");
                if (!$('#errorField2').hasClass("display")){
                    if (!$('#errorField3').hasClass("display")){
                        if (!$('#errorField4').hasClass("display")){
                            $("#submit").attr('disabled', false);
                        }
                    }
                }             }
        });
    </script>
</div>

</body>
</html>
