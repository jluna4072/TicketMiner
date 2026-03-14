/**
 * Utility class for logging actions taken within the TicketMiner system.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
package utility;

public class Logger{
    
    /**
     * Records the specified action detail to the log. Currently not implemented.
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
