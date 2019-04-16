package id.moxspoy.mnurilmanbaehaqi.smsdong.utility;

import android.util.Log;

import java.util.ArrayList;

public class SimCardNumber {

    public String getSimCardName(String number) {
        String simCardName = number;

        ArrayList<String> telkomsel = new ArrayList<>();
        telkomsel.add("0811");
        telkomsel.add("0812");
        telkomsel.add("0813");
        telkomsel.add("0821");
        telkomsel.add("0822");
        telkomsel.add("0823");
        telkomsel.add("0852");
        telkomsel.add("0853");
        ArrayList<String> xl = new ArrayList<>();
        xl.add("0817");
        xl.add("0818");
        xl.add("0819");
        xl.add("0859");
        xl.add("0877");
        xl.add("0878");
        xl.add("0831");
        xl.add("0832");
        xl.add("0838");
        ArrayList<String> indosat = new ArrayList<>();
        indosat.add("0814");
        indosat.add("0815");
        indosat.add("0816");
        indosat.add("0855");
        indosat.add("0856");
        indosat.add("0857");
        indosat.add("0858");
        ArrayList<String> smartfren = new ArrayList<>();
        smartfren.add("0881");
        smartfren.add("0882");
        smartfren.add("0887");
        smartfren.add("0888");
        ArrayList<String> three = new ArrayList<>();
        three.add("0896");
        three.add("0897");
        three.add("0898");

        String substringNumber = number.replace(" ", "").substring(0,4);
        Log.d("SimCard", substringNumber);
        if(telkomsel.contains(substringNumber)) {
            simCardName = "Telkomsel";
        } else if (xl.contains(substringNumber)){
            simCardName = "Xl Axis";
        } else if (indosat.contains(substringNumber)) {
            simCardName = "Indosat";
        } else if (smartfren.contains(substringNumber)) {
            simCardName = "Smartfren";
        } else if (three.contains(substringNumber)) {
            simCardName = "Three";
        }
        return  simCardName;
    }
}
