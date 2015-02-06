package com.packtpub.wflydevelopment.chapter9;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import java.io.Closeable;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class WatchMyDB {

    private final static Logger logger = Logger.getLogger(WatchMyDB.class.getName());

    @Schedule(dayOfWeek = "*", hour = "*", minute = "*", second =
            "*/30", year = "*", persistent = false)
    public void backgroundProcessing() {
        ModelControllerClient client = null;
        try {
            client = ModelControllerClient.Factory.create(InetAddress.getByName("localhost"), 9990);

            final ModelNode operation = new ModelNode();
            operation.get("operation").set("read-resource");
            operation.get("include-runtime").set(true);
            final ModelNode address = operation.get("address");
            address.add("subsystem", "datasources");
            address.add("data-source", "ExampleDS");
            address.add("statistics", "pool");
            final ModelNode returnVal = client.execute(operation);

            final ModelNode node2 = returnVal.get("result");
            final String stringActiveCount = node2.get("ActiveCount").asString();

            if (stringActiveCount.equals("undefined")) {
                return; // Connection unused
            }
            int activeCount = Integer.parseInt(stringActiveCount);

            if (activeCount > 50) {
                alertAdministrator();
            }
        } catch (Exception exc) {
            logger.log(Level.SEVERE, "Exception !", exc);
        } finally {
            safeClose(client);
        }
    }

    public void safeClose(final Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Exception closing the client! ", e);
            }
        }
    }

    private void alertAdministrator() {
        // Implement it !
    }
}
