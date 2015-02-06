package com.packtpub.wflydevelopment.chapter12.external;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Path("/external")
@Produces(MediaType.TEXT_PLAIN)
@DependsOn("DatabaseInitializer")
public class PendingSeats {

    private final Queue<Long> seats = new ConcurrentLinkedQueue<>();

    @Inject
    private Logger logger;

    @Resource
    private ManagedExecutorService executorService;

    @Inject
    private Instance<GenerateSeatRequestsFromDatabase> databaseCollector;

    @Inject
    private Instance<GenerateSeatRequestFromArtificial> artificalCollector;

    @PostConstruct
    private void setUp() {
        try {
            final List<Future<List<Integer>>> futures = executorService.invokeAll(Arrays.asList(
                databaseCollector.get(), artificalCollector.get()));
            final List<Integer> requestedIds = futures.stream().flatMap(future -> get(future).stream()).distinct()
                .collect(Collectors.toList());
            logger.info(requestedIds.toString());
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private List<Integer> get(Future<List<Integer>> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @GET
    public Long getNextSeat() {
        return seats.poll();
    }
}
