import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Configs{
    private static long mult, seed, incr, modu;
    
    public Configs(){
        
    }
    public Configs(long mu, long se, long in, long mo){
        mult = mu;
        seed = se;
        incr = in;
        modu = mo;
    }
    public static void setMult(long mu){
        mult = mu;
        write();
    }
    public static long getMult(){
        return mult;
    }
    public static void setSeed(long se){
        seed = se;
        write();
    }
    public static long getSeed(){
        return seed;
    }
    public static void setIncr(long in){
        incr = in;
        write();
    }
    public static long getIncr(){
        return incr;
    }
    public static void setModu(long mo){
        modu = mo;
        write();
    }
    public static long getModu(){
        return modu;
    }
    public static void printConfigs(){
        System.out.println("MULTIPLIER: " + mult);
        System.out.println("SEED / KEY: " + seed);
        System.out.println("INCREMENT:  " + incr);
        System.out.println("MODULUS:    " + modu);
    }
    public static void write(){
        try{
            FileWriter file = new FileWriter("configs.ini");
            file.write("" + mult);
            file.write("\n" + seed);
            file.write("\n" + incr);
            file.write("\n" + modu);
            file.close();
            
            System.out.println("Successfully wrote to file.");
        }catch(Exception e){
            System.out.println("ERROR COULD NOT WRITE");
            e.printStackTrace();
        }
    }
    public static void restore(){
        try{
            File configs = new File("configs.ini");
            FileWriter file = new FileWriter(configs);

            //Predetermined values
            file.write("314224455513");
            file.write("\n938394967345");
            file.write("\n249374925588");
            file.write("\n876865874927");
            file.close();

            System.out.println("Created new file " + configs.getName() + " in " + configs.getAbsolutePath());
            start();
        }catch(Exception e){
            System.out.println("ERROR COULD NOT RESET");
            e.getStackTrace();
        }
    }
    public static void start(){
        try{
            File configs = new File("configs.ini");
            if(configs.exists()){
                Scanner fileReader = new Scanner(configs);
                
                int i = 0;
                while(fileReader.hasNextLine()){
                    //FORMATTING DATA
                    String data = fileReader.nextLine();
                    data = data.toLowerCase();
                    data = data.replaceAll(" ", "");
                    
                    //READING DATA
                    if(i == 0){
                        mult = Long.parseLong(data);
                    }else if(i == 1){
                        seed = Long.parseLong(data);
                    }else if(i == 2){
                        incr = Long.parseLong(data);
                    }else if(i == 3){
                        modu = Long.parseLong(data);
                    }
                    i++;
                }
                fileReader.close();
            }else{
                //configs.createNewFile();
                FileWriter file = new FileWriter(configs);
                
                //Predetermined values
                file.write("314224455513");
                file.write("\n938394967345");
                file.write("\n249374925588");
                file.write("\n876865874927");
                file.close();
                
                System.out.println("Created new file " + configs.getName() + " in " + configs.getAbsolutePath());
                start();
            }
        }catch(Exception e){
            System.out.println("ERROR: START PROGRAM");
            e.printStackTrace();
        }
    }
}