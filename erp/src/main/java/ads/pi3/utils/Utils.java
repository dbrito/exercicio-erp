package ads.pi3.utils;

import ads.pi3.model.Usuario;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

public class Utils {
    public static String numToBrl(Double num) {        
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(num);
    }
    
    public static Double brlToNum(String brl) {                
        brl = brl.replace(".", "").replace(",", ".");
        return Double.parseDouble(brl);
    }
    
    public static Usuario getCurrentUser(HttpServletRequest request) {
        Usuario user = null;
        try {            
            user = (Usuario) request.getSession().getAttribute("funcionario");            
        } catch(Exception e) {}
        return user;
    }
}
