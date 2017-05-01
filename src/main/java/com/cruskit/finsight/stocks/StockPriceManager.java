package com.cruskit.finsight.stocks;

import com.cruskit.finsight.analysis.Metric;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Allows retrieval of pricing information for specified stocks.
 * Created by paul on 30/4/17.
 */
public class StockPriceManager {


    /**
     * Gets the closing prices for the specified stock on a daily basis
     *
     * @param symbol
     * @return a List of the closing prices for the stock, sorted by date asc
     */
    public List<Metric> getClosingPrice(String symbol) {

        // TODO: periodically refresh the info & store in dynamodb
        List<Metric> prices = new LinkedList<Metric>();

        try {

            // TODO: Read from dynamodb
            // TODO: Actually ready the symbol that has been asked for
            ClassLoader classLoader = this.getClass().getClassLoader();
            File file = new File(classLoader.getResource("ivv_history_test.csv").getFile());
            String fileContent = new String(Files.readAllBytes(file.toPath()));


            String[] lines = fileContent.split("\\r?\\n");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // Assume first line just has headings & skip it
            for (int i = 1; i < lines.length; i++){

                String[] fields = lines[i].split(",");

                // Date is in the first field
                LocalDateTime closingDate = LocalDateTime.of(LocalDate.parse(fields[0], formatter), LocalTime.MIDNIGHT);

                // Closing price is in the 5th field
                BigDecimal closingPrice = new BigDecimal(fields[4]);

//                System.out.println("Line: " + lines[i]);
//                System.out.println("date: " + closingDate + ", price: " + closingPrice);

                prices.add(new Metric(closingDate, closingPrice));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // Sort Metric in ascending date order
        prices.sort(Comparator.comparing(Metric::getDateTime));

        prices.forEach((metric)-> System.out.println(metric));

        return prices;
    }




    public static void main(String[] args){
        StockPriceManager mgr = new StockPriceManager();
        mgr.getClosingPrice("IVV.AX");
    }
}
