<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>List Tickets</title>
</head>
<body>
<h1>List of Tickets</h1>
<ul>
    <c:forEach var="ticket" items="${tickets}">
        <li>
            <a href="TicketServlet?action=view&id=${ticket.id}">${ticket.subject}</a>
            - ${ticket.customerName}
        </li>
    </c:forEach>
</ul>
<a href="create_ticket.jsp">Create New Ticket</a>
</body>
</html>
