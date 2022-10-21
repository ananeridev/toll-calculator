import services.Car;
import services.TollCalculator;

import java.util.Date;

public class Client {

    public static void main(String[] args) {
        TollCalculator tollCalculator = new TollCalculator();

        final Date date = new Date(2022, 10, 20, 16, 59);

        Integer result = tollCalculator.getTollFee(date, new Car());
        System.out.println(result);
    }
}
