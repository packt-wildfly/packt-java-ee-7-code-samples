package com.packtpub.wflydevelopment.chapter12.external;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import javax.enterprise.concurrent.ManagedTask;
import javax.enterprise.concurrent.ManagedTaskListener;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

public class GenerateSeatRequestFromArtificial implements Callable<List<Integer>>, ManagedTask {

    @Inject
    private Logger logger;

    @Inject
    private Instance<TaskListener> taskListener;

    @Override
    public List<Integer> call() throws Exception {
        logger.info("Sleeping...");
        Thread.sleep(5000);
        logger.info("Finished sleeping!");

        return Arrays.asList(4, 5, 6);
    }

    @Override
    public ManagedTaskListener getManagedTaskListener() {
        return taskListener.get();
    }

    @Override
    public Map<String, String> getExecutionProperties() {
        return new HashMap<>();
    }
}
