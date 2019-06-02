<%@ page import="com.CO3098.MiniWeb.tc225.domain.Person" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <link href="/treeCSS.css" rel="stylesheet" type="text/css" >
    <meta charset="UTF-8">
    <title>Tree</title>
</head>
<body>
<div class="tree">
    <ul>
        <li>
            <%--<c:forEach items="${persons}" var="person" varStatus="itr">--%>


           <%--<a href="#"> <c:out value="${person.getName()}">--%>
                         <%--</c:out> </a>--%>
            <%--<c:if test="${person}"--%>
            <%--<a href="#">${person.getName()}</a>--%>
                <%--<ul><li>--%>

                <%--</li></ul>--%>
            <%--</c:forEach>--%>
        <%--</li></ul>--%>
        <a href="#">Krishna</a>
            <a href="#">Indu</a>
            <ul>
                <li>
                    <a href="#">Sudarshan</a>
                </li>
                <li>
                    <a href="#">Anirudha</a>
                <li>
                    <a href="#">Krishna</a>
                    <a href="#">Indu</a>
                    <ul>
                        <li>
                            <a href="#">Sudarshan</a>
                        </li>
                        <li>
                            <a href="#">Anirudha</a>
                        </li>
<%----%>
</div>
</body>
</html>