package com.tutorials.server.repo;

import com.tutorials.server.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author SleSla
 * @version 1.0
 * @since 8/9/2022
 */
public interface ServerRepo extends JpaRepository<Server, Long> {
    Server findByIpAddress(String ipAddress);
}
