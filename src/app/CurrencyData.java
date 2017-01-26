
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This class receives currency data from the internet. 
 * @author Burak
 */
public class CurrencyData {
    
    /**
     * This method works by iterating through each line of the html 
     * code in the xml file.
     * @return the double representation of the value of dollar against Turkish
     * lira
     */
    public static double getTRYagainstUSD() {
        try{
            URL url = new URL("http://usd.fxexchangerate.com/try.xml");
            InputStream is =  url.openStream();
            try( BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if(line.contains("1.00 USD = ") ){
                        Scanner in = new Scanner(line);
                        String current = "";
                        while(!"=".equals(current)){
                            current = in.next();
                        }
                        String a = in.next();
                        return Double.parseDouble(a);
                    }
                }
            }
            catch (MalformedURLException e) {
                System.out.println(e);
            }
            catch (IOException e) {
                System.out.println(e);
            }
            return 1;
        }
        catch (MalformedURLException ex) {
            Logger.getLogger(CurrencyData.class.getName()).log(Level.SEVERE, null,ex);
        } catch (IOException ex) {
            Logger.getLogger(CurrencyData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1; // If this method does not work, it returns 1 so that it doesnt erroneously affect transactions.
    }
}
