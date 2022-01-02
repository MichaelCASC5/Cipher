public class Linear{
    private long mult, seed, incr, modu;
    private int len;
    
    public Linear(){
        
    }
    public Linear(long mu, long se, long in, long mo){
        mult = mu;
        seed = se;
        incr = in;
        modu = mo;
    }
    public String run(int iterations){
        String output = "";
        
        //Saving intital seed length
        String str = "" + seed;
        len = str.length();
        
        //Running algorithm
        String temp;
        for(int i=0;i<iterations;i++){
            seed = (mult * seed + incr) % modu;//LGA
            
            str = "" + Math.abs(seed);
            
            //Adding 0s to front
            if(str.length() < len){
                temp = "";
                for(int j=0;j<len-str.length();j++){
                    temp+="0";
                }
                str = temp + str;
            }
            
            //Returning only two middle number
            output+=str.substring(len/2-1,len/2+1);
        }
        return output;
    }
}