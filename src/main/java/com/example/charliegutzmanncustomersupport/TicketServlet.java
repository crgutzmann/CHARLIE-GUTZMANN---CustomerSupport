package com.example.charliegutzmanncustomersupport;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

@WebServlet("/TicketServlet")
public class TicketServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Map<Integer, Ticket> tickets = new HashMap<>();
    private int nextId = 1;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            listTickets(request, response);
        } else {
            switch (action) {
                case "view":
                    viewTicket(request, response);
                    break;
                case "download":
                    downloadAttachment(request, response);
                    break;
                default:
                    listTickets(request, response);
                    break;
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "create":
                createTicket(request, response);
                break;
            default:
                listTickets(request, response);
                break;
        }
    }

    private void listTickets(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("tickets", tickets.values());
        request.getRequestDispatcher("list_tickets.jsp").forward(request, response);
    }

    private void viewTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Ticket ticket = getTicket(id);
        request.setAttribute("ticket", ticket);
        request.getRequestDispatcher("view_ticket.jsp").forward(request, response);
    }

    private void createTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerName = request.getParameter("customerName");
        String subject = request.getParameter("subject");
        String body = request.getParameter("body");
        Ticket ticket = new Ticket(customerName, subject, body);
        Part filePart = request.getPart("attachment");
        if (filePart != null) {
            processAttachment(filePart, ticket);
        }
        tickets.put(nextId++, ticket);
        response.sendRedirect("TicketServlet");
    }

    private void downloadAttachment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Ticket ticket = getTicket(id);
        int attachmentIndex = Integer.parseInt(request.getParameter("attachmentIndex"));
        Attachment attachment = ticket.getAttachment(attachmentIndex);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + attachment.getName() + "\"");
        try (OutputStream out = response.getOutputStream()) {
            out.write(attachment.getContents());
        }
    }

    private void processAttachment(Part filePart, Ticket ticket) throws IOException {
        String fileName = filePart.getSubmittedFileName();
        InputStream inputStream = filePart.getInputStream();
        byte[] contents = inputStream.readAllBytes();
        Attachment attachment = new Attachment();
        attachment.setName(fileName);
        attachment.setContents(contents);
        ticket.addAttachment(attachment);
    }

    private Ticket getTicket(int id) {
        return tickets.get(id);
    }
}

