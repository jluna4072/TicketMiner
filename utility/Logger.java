package utility;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for logging actions taken within the TicketMiner system.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
public class Logger {

    /**
     * Appends the specified action detail to the log file ({@code data/ticketMinerLogger.txt}).
     * Each entry includes a timestamp and is written on its own line.
     *
     * @param actionDetail a description of the action to be logged
     */
    public static void logAction(String actionDetail) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        try (FileWriter logger = new FileWriter("data/ticketMinerLogger.txt", true)) {
            logger.write("[" + timestamp + "] " + actionDetail);
            logger.write(System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error occurred when writing to log file.");
        }
    }
}
