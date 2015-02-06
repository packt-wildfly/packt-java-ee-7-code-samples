package com.packtpub.wflydevelopment.chapter12.batching;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
public class SeatWriter extends AbstractItemWriter {

    public static final String FILENAME_PARAM = "logFile";

    @Inject
    private JobContext jobContext;

    @PersistenceContext
    private EntityManager em;

    private BufferedWriter writer;

    @Override
    public void open(Serializable ckpt) throws Exception {
        final Properties jobProperties = jobContext.getProperties();
        final String fileName = jobProperties.getProperty(FILENAME_PARAM);
        writer = new BufferedWriter(new FileWriter(fileName));
        writer.write("Importing...");
        writer.newLine();
    }

    @Override
    public void writeItems(List<Object> items) throws Exception {
        writer.write("Chunk size: " + items.size());
        writer.newLine();
        for (Object obj : items) {
            em.persist(obj);
            writer.write("Persisted: " + obj);
            writer.newLine();
        }
    }

    @Override
    public void close() throws Exception {
        writer.write("Import finished");
        writer.newLine();
        writer.close();
    }

}
