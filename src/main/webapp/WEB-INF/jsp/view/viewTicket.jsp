<html>
<head>
    <title>Ticket #<c:out value="${ticketId}"/></title>
</head>
<body>
    <a href="<c:url value='/login'>
        <c:param name='logout'/>
    </c:url>">Logout</a>
    <h2>Ticket</h2>
    <h3>Customer Name #<c:out value="${ticketId}"/>: <c:out value="${ticket.name}"/></h3>
    <p>Subject: <c:out value="${ticket.subject}"/></p>
    <p><c:out value="${ticket.body}"/></p>
    <c:if test="${ticket.hasAttachment()}">
    <a href="<c:url value='/ticket' >
            <c:param name='action' value='download' />
            <c:param name='ticketId' value='${ticketId}' />
            <c:param name='attachment' value='${ticket.attachment.name}'/>
        </c:url>"><c:out value="${ticket.attachment.name}"/></a>
    </c:if>
<br><a href="ticket">Return to ticket list</a>

</body>
</html>
