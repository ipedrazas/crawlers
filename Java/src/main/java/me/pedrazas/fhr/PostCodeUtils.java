package me.pedrazas.fhr;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ivan
 *
 */
public class PostCodeUtils {
    
    public static final int OUTWARD = 0;
    public static final int INWARD = 1;
    
    public static final String AREACODES = "AB AL B BA BB BD BF BH BL BN BR BS BT BX CA CB CF CH CM CO CR CT CV CW DA DD DE DG DH DL DN DT DY E EC EH EN EX FK FY G GIR GL GU GY HA HD HG HP HR HS HU HX IG IM IP IV JE KA KT KW KY L LA LD LE LL LN LS LU M ME MK ML N NE NG NN NP NR NW OL OX PA PE PH PL PO PR RG RH RM S SA SE SG SK SL SM SN SO SP SR SS ST SW SY TA TD TF TN TQ TR TS TW UB W WA WC WD WF WN WR WS WV YO ZE";
    public static final List<String> AREACODESLIST = Arrays.asList(AREACODES.split(" "));
    
    
    public static String getAreaCode(String postcode){    	
    	String outward = getOutward(postcode);
		if(outward!=null){
			return outward.replaceAll("[^A-Za-z]","");
		}
		return null;
    }
    
    /**
     * @param postcode
     * @param ward
     * @return 
     */
    public static String getWard(String postcode, int ward){
        if(validate(postcode)){
            String[] wards = postcode.split(" ");
            if(wards.length > 1){
               return  wards[ward].toUpperCase();               
            }           
        }
        return null;
    }
    
    public static String getOutward(String postcode){
        return getWard(postcode, OUTWARD);
    }
    
    public static String getInward(String postcode){
        return getWard(postcode, INWARD);
    }
    
    /**
     * @param code
     * @return boolean
     * 
     * The RegEx is the one supplied by the UK Government.
     * Check Wikipedia for some discussion on validation.  
     * 
     * @see <a href="http://en.wikipedia.org/wiki/Postcodes_in_the_United_Kingdom#Validation">Wikipedia</a>  
     */
    public static boolean validate(String code) {
    	// String regexp="^([A-PR-UWYZ](([0-9](([0-9]|[A-HJKSTUW])?)?)|([A-HK-Y][0-9]([0-9]|[ABEHMNPRVWXY])?)) [0-9][ABD-HJLNP-UW-Z]{2})|GIR 0AA$";
        String regexp = "^(GIR 0AA)|((([A-Z-[QVX]][0-9][0-9]?)|(([A-Z-[QVX]][A-Z-[IJZ]][0-9][0-9]?)|(([A-Z-[QVX]][0-9][A-HJKSTUW])|([A-Z-[QVX]][A-Z-[IJZ]][0-9][ABEHMNPRVWXY])))) [0-9][A-Z-[CIKMOV]]{2})";
        if(code!=null){
        	Pattern pattern = Pattern.compile(regexp);
            Matcher matcher = pattern.matcher(code.toUpperCase());
            return matcher.matches();
        }
        return false;
    }
}
