package easyfit.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RoundNumbers {

    // Método para redondear a dos decimales
    public static double redondear(double valor) {
        BigDecimal bd = new BigDecimal(valor);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
