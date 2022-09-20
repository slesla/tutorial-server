package com.tutorials.server.service.implementation;

import com.tutorials.server.enumeration.Status;
import com.tutorials.server.model.Server;
import com.tutorials.server.repo.ServerRepo;
import com.tutorials.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

/**
 * @author SleSla
 * @version 1.0
 * @since 8/9/2022
 */

@RequiredArgsConstructor //lombok feat creates the constructor
@Service
@Transactional
@Slf4j  // lombok print in a console
public class ServerServiceImplementation implements ServerService {
    private final ServerRepo serverRepo;

    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException { //FIXME isReachable not the best way to check the server
        log.info("Pinging server IP: {}", ipAddress);
        Server server = serverRepo.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000)? Status.SERVER_UP: Status.SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by id: {}", id);
        return serverRepo.findById(id).get(); //the get is needed to get the server found by id
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server: {}", server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server by ID: {}", id);
        serverRepo.deleteById(id);
        return Boolean.TRUE; //never reached if error on deleting
    }

    private String setServerImageUrl() {
        String[] imageNames = { "server1.png", "server2.png", "server3.png", "server4.png", "server5.png"}; //is this ok??????
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" + imageNames[new Random().nextInt(5)]).toUriString(); // this will get the images from localhost:8080/server/image/{imageNames}
    }
}
