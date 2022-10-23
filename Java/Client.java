import java.util.Date;

public class Client {

    public static void main(String[] args) {

        RushHoursDatabase rushHoursDatabase = new RushHoursDatabase();
        Car car = new Car();
        Date newDate = new Date(2022,10,22, 6, 00);

         int result = rushHoursDatabase.getTollFeeAtPeakTimesCauculus(newDate, car);
         System.out.println(result);

    }
}
