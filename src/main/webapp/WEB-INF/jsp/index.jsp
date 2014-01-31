<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel='stylesheet' href='/webjars/bootstrap/3.0.3/css/bootstrap.min.css'>
</head>
<body>
<h1>${message}</h1>

<br><br>

<div class="panel panel-default">
    <div class="panel-heading">The location results for your IP ${ip}</div>

    <table class="table">
        <c:forEach items="${locationMap}" var="entry">
            <tr>
                <td>${entry.key}</td>
                <td>${entry.value}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>