package com.intellekta.messageDatabase;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MessageRepository extends JpaRepository<Message, String> {
    List<Message> findByUsername(String username);
}
