import java.util.Random;
import java.util.Scanner;

public class Driver{
    public static Configs c = new Configs();
    public static Linear lin;
    
    public static String in, out, rand;
    
    public static void main(String[]args){
        //Start up
        c.start();
        
        //Assigning values to algorithm
        reset();
        
        //Heading
        System.out.println("Welcome to CIPHER       alpha 1.0.0");
        for(int i=0;i<10;i++)
            System.out.println();
            
        //UI Loop
        Scanner reader = new Scanner(System.in);
        String input;
        while(true){
            input = reader.nextLine();
            
            if(input.substring(0,3).equals("in:")){
                input = minimize(input);
                input = input.substring(3);
                in = format(input);
            }else if(input.equals("in.encrypt")){
                encrypt(in);
            }else if(input.equals("in.decrypt")){
                decrypt(in);
            }else if(input.equals("in.print")){
                System.out.println(in);
            }else if(input.equals("out.print")){
                System.out.println(out);
            }else if(input.equals("rand.print")){
                System.out.println(rand);
            }else if(input.length() > 11 && input.substring(0,11).equals("rand.print,")){
                reset();
                System.out.println(lin.run(Integer.parseInt(input.substring(11))));
                reset();
            }else if(input.length() > 10 && input.substring(0,10).equals("rand.give,")){
                give(Integer.parseInt(input.substring(10)));
            }else if(input.length() > 10 && input.substring(0,10).equals("mult.edit:")){
                c.setMult(Long.parseLong(input.substring(10)));
            }else if(input.length() > 10 && input.substring(0,10).equals("seed.edit:")){
                c.setSeed(Long.parseLong(input.substring(10)));
            }else if(input.length() > 10 && input.substring(0,10).equals("incr.edit:")){
                c.setIncr(Long.parseLong(input.substring(10)));
            }else if(input.length() > 10 && input.substring(0,10).equals("modu.edit:")){
                c.setModu(Long.parseLong(input.substring(10)));
            }else if(input.equals("configs")){
                c.printConfigs();
            }else if(input.equals("configs.restore")){
                c.restore();
            }else if(input.equals("exit")){
                System.exit(0);
            }else{
                System.out.println("UNRECOGNIZED INPUT");
            }
        }
    }
    public static void give(int iterations){
        Random rand = new Random();
        String output = "";
        
        for(int i=0;i<iterations;i++){
            output+=rand.nextInt(10);
        }
        System.out.println(output);
    }
    public static void reset(){
        lin = new Linear(
            c.getMult(),
            c.getSeed(),
            c.getIncr(),
            c.getModu()
        );
    }
    public static String format(String input){
        String output = "";
        String temp = "";
        
        for(int i=0;i<input.length();i++){
            if(i%5==0){
                if(i < input.length()-4){
                    output+=input.substring(i,i+5) + " ";
                }else{
//                    for(int j=0;j<5-(input.length()%5);j++){  //This would add 'x's to make blocks of 5
//                        temp+="x";
//                    }
                    output = output + input.substring(i) + temp;
                }
            }
        }
        return output;
    }
    public static String minimize(String input){
        input = input.toLowerCase();
        input = input.replaceAll(" ", "");
        return input;
    }
    public static String charToNum(String input){
        String output = "";
        String temp = "";
        
        char c = ' ';
        
        //Converting input to numbers
        for(int i=0;i<input.length();i++){
            c = input.charAt(i);
            temp = "" + ((int)c - 97);
            
            if(temp.length() < 2){
                temp = "0" + temp;
            }
            
            output+=temp;
        }
        return output;
    }
    public static String numToChar(String input){
        String output = "";
        int n = 0;
        for(int i=0;i<input.length();i+=2){
            n = Integer.parseInt(input.substring(i,i+2));
            n+=97;
            output+="" + (char)n;
        }
        return output;
    }
    public static String addToRand(String input){
        String output = "";
        String temp = "";
        
        int a = 0;
        int b = 0;
        
        for(int i=0;i<input.length();i+=2){
            a = Integer.parseInt(input.substring(i,i+2));
            b = Integer.parseInt(rand.substring(i,i+2));
            
            temp = "" + (int)(a + b)%26;//mod output by 26
            if(temp.length() < 2){
                temp = "0" + temp;
            }
            output+=temp;
        }
        return output;
    }
    public static String subFromRand(String input){
        String output = "";
        String temp = "";
        
        int a = 0;
        int b = 0;
        
        for(int i=0;i<input.length();i+=2){
            a = Integer.parseInt(input.substring(i,i+2));
            b = Integer.parseInt(rand.substring(i,i+2));
            
            boolean search = true;
            int j = 0;
            while(search){
                if((b+j)%26 == a){
                    temp = "" + j;
                    search = false;
                }
                j++;
            }
            
            if(temp.length() < 2){
                temp = "0" + temp;
            }
            output+=temp;
        }
        return output;
    }
    public static void encrypt(String input){
        out = "";
        
        //Converting to spaceless and lowercase
        input = minimize(input);
        
        //Running the LGM
        reset();
        rand = lin.run(input.length());
        
        //Converting chars to numerical values
        String combine = charToNum(input);
        
        //Adding input numbers to rand  (mod 26)
        String result = addToRand(combine);
        
        //Returning encrypted message to letters
        String output;
        output = numToChar(result);
        
        //Asthetic spacing
        for(int i=0;i<output.length();i++){
            if(i%5==0){
                if(i < output.length()-4){
                    out+=output.substring(i,i+5) + " ";
                }else{
                    out+=output.substring(i);
                }
            }
        }
    }
    public static void decrypt(String input){
        out = "";
        
        //Converting to spaceless and lowercase
        input = minimize(input);
        
        //Running the LGM
        reset();
        rand = lin.run(input.length());
        
        //Converting chars to numerical values
        String combine = charToNum(input);
        
        //Subtracting input numbers to rand  (mod 26)
        String result = subFromRand(combine);
        
        //Returning decrypted message to letters        
        String output;
        output = numToChar(result);
        
        //Asthetic spacing
        for(int i=0;i<output.length();i++){
            if(i%5==0){
                if(i < output.length()-4){
                    out+=output.substring(i,i+5) + " ";
                }else{
                    out+=output.substring(i);
                }
            }
        }
    }
}