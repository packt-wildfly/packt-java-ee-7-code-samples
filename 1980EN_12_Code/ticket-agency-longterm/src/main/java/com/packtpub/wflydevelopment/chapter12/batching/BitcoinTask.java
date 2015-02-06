package com.packtpub.wflydevelopment.chapter12.batching;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.batch.api.AbstractBatchlet;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.packtpub.wflydevelopment.chapter12.control.SeatTypeDao;
import com.packtpub.wflydevelopment.chapter12.entity.SeatType;

@Named
public class BitcoinTask extends AbstractBatchlet {

    private static final String EXTERNAL_API = "https://api.bitcoinaverage.com/exchanges/USD";
    public static final String FILENAME_PARAM = "bitcoinFile";

    @Inject
    private SeatTypeDao seatTypeDao;

    @Inject
    private JobContext jobContext;

    @Override
    public String process() throws Exception {
        final WebTarget api = ClientBuilder.newClient().target(EXTERNAL_API);
        final Response response = api.request().get();
        final JsonObject entity = response.readEntity(JsonObject.class);

        double averageValue = entity.getJsonObject("btce").getJsonObject("rates").getJsonNumber("bid").doubleValue();

        final Map<SeatType, Double> pricesInBitcoins = calculeteBitcoinPrices(averageValue, seatTypeDao.findAll());

        writeToFile(pricesInBitcoins);

        return "OK";
    }

    private void writeToFile(Map<SeatType, Double> pricesInBitcoins) throws Exception {
        final Properties jobProperties = jobContext.getProperties();
        final String fileName = jobProperties.getProperty(FILENAME_PARAM);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(pricesInBitcoins.toString());
            writer.newLine();
        }
    }

    private Map<SeatType, Double> calculeteBitcoinPrices(double averageValue, List<SeatType> findAll) {
        return findAll.stream().collect(
            Collectors.toMap(seatType -> seatType, seatType -> seatType.getPrice() / averageValue));
    }
}
