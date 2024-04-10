<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Create Ticket</title>
</head>
<body>
<h1>Create Ticket</h1>
<form action="TicketServlet" method="post" enctype="multipart/form-data">
  <label for="customerName">Customer Name:</label>
  <input type="text" id="customerName" name="customerName" required><br>
  <label for="subject">Subject:</label>
  <input type="text" id="subject" name="subject" required><br>
  <label for="body">Body:</label><br>
  <textarea id="body" name="body" rows="4" cols="50" required></textarea><br>
  <label for="attachment">Attachment:</label>
  <input type="file" id="attachment" name="attachment"><br>
  <input type="hidden" name="action" value="create">
  <input type="submit" value="Create">
</form>
<a href="index.jsp">Back to List</a>
</body>
</html>
