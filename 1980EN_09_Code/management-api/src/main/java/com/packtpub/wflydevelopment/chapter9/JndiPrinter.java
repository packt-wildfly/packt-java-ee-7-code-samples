package com.packtpub.wflydevelopment.chapter9;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Logger;

@Singleton
@Startup
public class JndiPrinter {

    private static final Logger logger = Logger.getLogger(JndiPrinter.class.getName());

    @PostConstruct
    public void print() throws IOException {
        final ModelControllerClient client = ModelControllerClient.Factory.create(InetAddress.getByName("localhost"), 9990);

        final ModelNode operation = new ModelNode();
        operation.get("operation").set("jndi-view");

        final ModelNode address = operation.get("address");
        address.add("subsystem", "naming");

        operation.get("recursive").set(true);
        operation.get("operations").set(true);

        final ModelNode returnVal = client.execute(operation);
        logger.info(returnVal.get("result").toString());
    }
}