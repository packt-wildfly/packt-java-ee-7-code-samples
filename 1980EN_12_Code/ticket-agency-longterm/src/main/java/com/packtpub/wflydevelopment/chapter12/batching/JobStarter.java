package com.packtpub.wflydevelopment.chapter12.batching;

import java.util.Properties;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Stateless
@Path("/job")
public class JobStarter {

    @GET
    public String start() {
        final JobOperator operator = BatchRuntime.getJobOperator();
        final Properties properties = new Properties();
        properties.put("propertyName", "propertyValue");

        long jobId = operator.start("externalSystem", properties);

        return Long.toString(jobId);
    }

}
