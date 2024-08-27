import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import util.ApplicationContext;
import util.jalaliCalender.JalaliDate;
import util.jalaliCalender.JalaliDateUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StudentLoanApp {


    public static void main(String[] args) {


        ApplicationContext.getInstance().getMenu().show();
    }
//        double loanAmount = 2_600_000; // مبلغ وام
//        double annualIncreasePercentage = 100; // درصد افزایش پلکانی هر سال (دو برابر شدن هر سال)
//
//        calculateInstallments(loanAmount, annualIncreasePercentage);
//    }
//
//    public static void calculateInstallments(double loanAmount,double annualIncreasePercentage) {
//        int numberOfMonths = 5 * 12;
//        double monthlyInterestRate = (4.0 / 100) / 12;
//
//        // محاسبه قسط ماهیانه اولیه با استفاده از فرمول اقساط ثابت
//        double initialInstallment = (loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfMonths))
//                / (Math.pow(1 + monthlyInterestRate, numberOfMonths) - 1);
//
////        double totalPaid = 0;
//        double annualInstallment = initialInstallment;
//
//        System.out.printf("قسط ماهیانه اولیه: %.2f تومان%n", initialInstallment);
//        LocalDate startDate = LocalDate.now();
//        // محاسبه و نمایش اقساط سالانه با افزایش پلکانی
//        int count=0;
//        for (int year = 1; year <= 5; year++) {
//
//            for (int i = 0; i < 12; i++) {
//                startDate = startDate.plusMonths(1);
//                count++;
//                JalaliDate from =JalaliDateUtil.MiladyToShamsy(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
//                System.out.print("Updated Date: " + from);
//                System.out.print("Updated Date: " + startDate);
//                System.out.printf("قسط ماهیانه در سال %d: %.2f تومان%n", year, annualInstallment);
//                // Print the updated date
//            }
//
//
//            // دو برابر کردن قسط برای سال بعد
//            annualInstallment *= (1 + annualIncreasePercentage / 100);
//
//        }
//    }
}
