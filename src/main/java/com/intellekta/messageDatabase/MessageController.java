package com.intellekta.messageDatabase;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MessageController {
    private final MessageRepository messageRepository;
    private String sender;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/messages")
    public String startMessages(@RequestParam("sender") String sender) {
        this.sender = sender;
        return "redirect:/messages";
    }

    @GetMapping("/messages")
    public String messages(Model model) {
        model.addAttribute("sender", sender);
        model.addAttribute("messages", messageRepository.findAll());
        return "messages";
    }

    @PostMapping("/messages/add")
    public String addMessage(@RequestParam("message") String message,
                          @RequestParam("username") String username) {
        Message newMessage = new Message();
        newMessage.setMessage(message);
        newMessage.setUsername(username);
        messageRepository.save(newMessage);
        return "redirect:/messages";
    }

    @GetMapping("/messages/filter")
    public String findMessage(@RequestParam(name = "filter", required = false) String username, Model model) {
        List<Message> messages;
        if (username != null) {
            messages = messageRepository.findByUsername(username);
        } else {
            messages = messageRepository.findAll();
        }
        model.addAttribute("messages", messages);
        return "messages";
    }
}
