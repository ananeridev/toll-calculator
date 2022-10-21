package services;

import java.util.*;
import java.util.concurrent.*;

import src.main.model.TollFreeVehicles;


public class TollCalculator {

  /**
   * Calculate the total toll fee for one day
   *
   * @param vehicle - the vehicle
   * @param dates   - date and time of all passes on one day
   * @return - the total toll fee for that day
   */

   // 
  public int getTollFee(Date vehicle, Date... dates) {
    Date intervalStart = dates[0];
    int totalFee = 0;
    for (Date date : dates) {
      int nextFee = getTollFee(date, (Vehicle) vehicle);
      int tempFee = getTollFee(intervalStart, (Vehicle) vehicle);

      TimeUnit timeUnit = TimeUnit.MINUTES;
      long diffInMillies = date.getTime() - intervalStart.getTime();
      long minutes = timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);

      if (minutes <= 60) {
        if (totalFee > 0) totalFee -= tempFee;
        if (nextFee >= tempFee) tempFee = nextFee;
        totalFee += tempFee;
      } else {
        totalFee += nextFee;
      }
    }
    if (totalFee > 60) totalFee = 60;
    return totalFee;
  }

  // verify if is a vehible able to not pay the fee
  public boolean isTollFreeVehicle(Vehicle vehicle) {
    if(vehicle == null) return false;
    String vehicleType = vehicle.getType();
    return vehicleType.equals(TollFreeVehicles.MOTORBIKE.toString()) ||
           vehicleType.equals(TollFreeVehicles.TRACTOR.toString()) ||
           vehicleType.equals(TollFreeVehicles.EMERGENCY.toString()) ||
           vehicleType.equals(TollFreeVehicles.DIPLOMAT.toString()) ||
           vehicleType.equals(TollFreeVehicles.FOREIGN.toString()) ||
           vehicleType.equals(TollFreeVehicles.MILITARY.toString());
  }

  public Boolean isTollFreeDate(Date date) {
    // change gregorian calendar to calendar beacause calendar already attend the gregorian specification
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) return true;

    // all holidays on the year 2013
    if (year == 2013) {
      if (month == Calendar.JANUARY && day == 1 ||
          month == Calendar.MARCH && (day == 28 || day == 29) ||
          month == Calendar.APRIL && (day == 1 || day == 30) ||
          month == Calendar.MAY && (day == 1 || day == 8 || day == 9) ||
          month == Calendar.JUNE && (day == 5 || day == 6 || day == 21) ||
          month == Calendar.JULY ||
          month == Calendar.NOVEMBER && day == 1 ||
          month == Calendar.DECEMBER && (day == 24 || day == 25 || day == 26 || day == 31)) {
        return true;
      }
    }
    return false;
  }

public int getTollFee(final Date date, Vehicle vehicle) {
  if(Boolean.TRUE.equals(isTollFreeDate(date)) || isTollFreeVehicle(vehicle)) return 0;
  Calendar calendar = Calendar.getInstance();
  calendar.setTime(date);
  int hour = calendar.get(Calendar.HOUR_OF_DAY);
  int minute = calendar.get(Calendar.MINUTE);

  int hourMinute = Integer.parseInt(String.format("%d%d", hour, minute));

  // rush hours calculo
  // hashmap hour, minute
  if (hourMinute >= 600 && hourMinute <= 629) return 8;
  else if (hourMinute >= 630 && hourMinute <= 659) return 13;
  else if (hourMinute >= 700 && hourMinute <= 759) return 18;
  else if (hourMinute >= 800 && hourMinute <= 829) return 13;
  else if (hourMinute >= 830 && hourMinute <= 859) return 8;
  else if (hourMinute >= 150 && hourMinute <= 1529) return 13;
  else if (hourMinute >= 1530 && hourMinute <= 1659) return 18;
  else if (hourMinute >= 170 && hourMinute <= 1759) return 13;
  else if (hourMinute >= 180 && hourMinute <= 1829) return 8;
  else return 0;
}


}

