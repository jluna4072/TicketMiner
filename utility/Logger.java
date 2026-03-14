package utility;
import java.io.FileWriter; 
import java.io.IOException;

public class Logger{
    
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
