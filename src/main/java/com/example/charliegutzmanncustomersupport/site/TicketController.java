package com.example.charliegutzmanncustomersupport.site;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("Ticket")
public class TicketController {
    private volatile int TICKET_ID = 1;
    private Map<Integer, Ticket> ticketDB = new LinkedHashMap<>();

    @RequestMapping(value={"list", ""})
    public String listTickets(Model model) {
        model.addAttribute("ticketDatabase", ticketDB);
        return "listTickets";
    }

    @GetMapping("create")
    public ModelAndView createTicket() {
        return new ModelAndView("ticketForm", "ticket", new TicketForm());
    }

    @PostMapping("create")
    public View createPost(@ModelAttribute("ticket")TicketForm form) throws IOException {
        Ticket ticket = new Ticket();
        ticket.setName(form.getName());
        ticket.setSubject(form.getSubject);
        ticket.setBody(form.getBody());

        MultipartFile file = form.getAttachment();
        Attachment attachment = new Attachment();
        attachment.setName(file.getOriginalFilename());
        attachment.setContents(file.getBytes());
        if ((attachment.getName() != null && attachment.getName().length() > 0) ||
                (attachment.getContents() != null && attachment.getContents().length > 0)) {
            ticket.setAttachment(attachment);
        }

        // add and synchronize
        int id;
        synchronized(this) {
            id = this.TICKET_ID++;
            ticketDB.put(id, ticket);
        }

        // show the view with the blog id
        return new RedirectView("view/"+id, true, false);

    }

    @GetMapping("view/{ticketId}")
    public ModelAndView viewTicket(Model model, @PathVariable("ticketId")int ticketId) {
        Ticket ticket = ticketDB.get(ticketId);
        if (ticket == null) {
            return new ModelAndView(new RedirectView("ticket/list", true, false));
        }

        // found the blog, so send it to the view
        model.addAttribute("ticketId", ticketId);
        model.addAttribute("ticket", ticket);

        return new ModelAndView("viewTicket");

    }

    @GetMapping("/{ticketId}/attachment/{attachment:.+}")
    public View downloadAttachment(@PathVariable("attachmentId")int ticketId, @PathVariable("ticket")String name) {
        Ticket ticket = ticketDB.get(ticketId);
        if (ticket == null) {
            return new RedirectView("listTickets", true, false);
        }

        Attachment attachment = ticket.getAttachment();
        if (attachment == null) {
            return new RedirectView("listTickets", true, false);
        }

        return new DownloadView(attachment.getName(), attachment.getContents());
    }

    public static class TicketForm {
        private String name;
        private String subject;
        private String body;
        private MultipartFile attachment;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject() {
            this.subject = this.subject = subject;
        }

        public MultipartFile getAttachment() {
            return attachment;
        }

        public void setAttachment(MultipartFile image) {
            this.attachment = attachment;
        }
    }

}