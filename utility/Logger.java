/**
 * Utility class for logging actions taken within the TicketMiner system.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
package utility;

import java.io.FileWriter;
import java.io.IOException;

public class Logger{
    
    /**
     * Appends the specified action detail to the log file ({@code data/ticketMinerLogger.txt}).
     * Each entry is written on its own line.
     *
     * @param actionDetail a description of the action to be logged
     */
    public static void logAction(String actionDetail){
     //Writing to file for decision 
      try(FileWriter logger = new FileWriter("data/ticketMinerLogger.txt", true)){
        logger.write(actionDetail);
        logger.write(System.lineSeparator());
      }
      catch(IOException e){
        System.out.println("Error occured when");
      }
    }

}
