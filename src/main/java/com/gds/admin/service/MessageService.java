package com.gds.admin.service;

import com.gds.admin.domain.Message;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Message.
 */
public interface MessageService {

    /**
     * Save a message.
     *
     * @param message the entity to save
     * @return the persisted entity
     */
    Message save(Message message);

    /**
     * Get all the messages.
     *
     * @return the list of entities
     */
    List<Message> findAll();


    /**
     * Get the "id" message.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Message> findOne(Long id);

    /**
     * Delete the "id" message.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the message corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Message> search(String query);
}
