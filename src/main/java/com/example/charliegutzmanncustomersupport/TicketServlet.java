package com.example.charliegutzmanncustomersupport;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "ticket", value="/ticket")
@MultipartConfig(fileSizeThreshold = 5_242_880, maxFileSize = 20_971_520L, maxRequestSize = 41_943_040L)
public class TicketServlet extends HttpServlet {
    private volatile int TICKET_ID = 1;
    private Map<Integer, Ticket> ticketDB = new LinkedHashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }
        switch(action) {
            case "createTicket" -> showTicketForm(request, response);
            case "view" -> viewTicket(request, response);
            case "download" -> downloadAttachment(request, response);
            default -> listTickets(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }
        switch(action) {
            case "create" -> createTicket(request, response);
            default -> response.sendRedirect("ticket");
        }
    }

    private void listTickets(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setAttribute("ticketDatabase", ticketDB);
        request.getRequestDispatcher("WEB-INF/jsp/view/listTickets.jsp").forward(request, response);
    }

    private void createTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //makey makey wakey wakey
        Ticket ticket = new Ticket();
        ticket.setName(request.getParameter("title"));
        ticket.setSubject(request.getParameter("subject"));
        ticket.setBody(request.getParameter("body"));

        Part file = request.getPart("file1");
        if (file != null) {
            Attachment attachment = this.processAttachment(file);
            if (attachment != null) {
                ticket.setAttachment(attachment);
            }
        }

        int id;
        synchronized(this) {
            id = this.TICKET_ID++;
            ticketDB.put(id, ticket);
        }

        response.sendRedirect("ticket?action=view&ticketId=" + id);
    }

    private Attachment processAttachment(Part file) throws IOException{
        InputStream in = file.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // shark bytes
        int read;
        final byte[] bytes = new byte[1024];
        while ((read = in.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }

        Attachment attachment = new Attachment();
        attachment.setName(file.getSubmittedFileName());
        attachment.setContents(out.toByteArray());

        return attachment;
    }

    private void downloadAttachment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String idString = request.getParameter("ticketId");

        Ticket ticket = getTicket(idString, response);

        String name = request.getParameter("image");
        if (name == null) {
            response.sendRedirect("ticket?action=view&bticketId=" + idString);
        }

        Attachment attachment = ticket.getAttachment();
        if (attachment == null) {
            response.sendRedirect("ticket?action=view&ticketId=" + idString);
            return;
        }

        response.setHeader("Content-Disposition", "attachment; filename=" + attachment.getName());
        response.setContentType("application/octet-stream");

        ServletOutputStream out = response.getOutputStream();
        out.write(attachment.getContents());
    }

    private void viewTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String idString = request.getParameter("ticketId");
        Ticket ticket = getTicket(idString, response);

        request.setAttribute("ticket", ticket);
        request.setAttribute("ticketId", idString);

        request.getRequestDispatcher("WEB-INF/jsp/view/viewTicket.jsp").forward(request, response);
    }

    private void showTicketForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.getRequestDispatcher("WEB-INF/jsp/view/ticketForm.jsp").forward(request, response);
    }

    private Ticket getTicket(String idString, HttpServletResponse response) throws ServletException, IOException{
        if (idString == null || idString.length() == 0) {
            response.sendRedirect("ticket");
            return null;
        }

        try {
            int id = Integer.parseInt(idString);
            Ticket ticket = ticketDB.get(id);
            if (ticket == null) {
                response.sendRedirect("ticket");
                return null;
            }
            return ticket;
        }
        catch(Exception e) {
            response.sendRedirect("ticket");
            return null;
        }
    }
}

