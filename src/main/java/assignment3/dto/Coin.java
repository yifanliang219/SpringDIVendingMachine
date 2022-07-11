package assignment3.dto;

import java.math.BigDecimal;

public enum Coin {

    TWENTY_POUND(20), TEN_POUND(10), FIVE_POUND(5), TWO_POUND(2),
    ONE_POUND(1), FIFTY_PENCE(0.5), TWENTY_PENCE(0.2), TEN_PENCE(0.1), FIVE_PENCE(0.05), ONE_PENNY(0.01);

    private BigDecimal value;

    Coin(double value) {
        this.value = new BigDecimal(value);
    }

    public BigDecimal getValue(){
        return value;
    }
}
