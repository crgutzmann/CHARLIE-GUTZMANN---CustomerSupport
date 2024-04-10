<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Ticket</title>
</head>
<body>
<h1>Ticket Details</h1>
<p><strong>Customer Name:</strong> ${ticket.customerName}</p>
<p><strong>Subject:</strong> ${ticket.subject}</p>
<p><strong>Body:</strong> ${ticket.body}</p>
<h2>Attachments</h2>
<ul>
    <c:forEach var="attachment" items="${ticket.attachments}">
        <li><a href="TicketServlet?action=download&id=${ticket.id}&attachmentIndex=${attachment.index}">${attachment.name}</a></li>
    </c:forEach>
</ul>
<a href="index.jsp">Back to List</a>
</body>
</html>
