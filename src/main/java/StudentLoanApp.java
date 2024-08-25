import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import util.ApplicationContext;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentLoanApp {


    public static void main(String[] args) {
        double loanAmount = 2_600_000; // مبلغ وام
        double annualIncreasePercentage = 100; // درصد افزایش پلکانی هر سال (دو برابر شدن هر سال)

        calculateInstallments(loanAmount, annualIncreasePercentage);
    }

    public static void calculateInstallments(double loanAmount,double annualIncreasePercentage) {
        int numberOfMonths = 5 * 12;
        double monthlyInterestRate = (4.0 / 100) / 12;

        // محاسبه قسط ماهیانه اولیه با استفاده از فرمول اقساط ثابت
        double initialInstallment = (loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfMonths))
                / (Math.pow(1 + monthlyInterestRate, numberOfMonths) - 1);

//        double totalPaid = 0;
        double annualInstallment = initialInstallment;

        System.out.printf("قسط ماهیانه اولیه: %.2f تومان%n", initialInstallment);
        LocalDate startDate = LocalDate.now();
        // محاسبه و نمایش اقساط سالانه با افزایش پلکانی
        for (int year = 1; year <= 5; year++) {

            for (int i = 0; i < 12; i++) {
                startDate = startDate.plusMonths(1); // Add 1 month to the date
                System.out.println("Updated Date: " + startDate); // Print the updated date
            }
          //  double yearlyInstallment = annualInstallment * 12; // تبدیل قسط ماهیانه به سالانه
         //   totalPaid += yearlyInstallment; // اضافه کردن به مجموع پرداخت‌ها
//            System.out.printf("قسط ماهیانه در سال %d: %.2f تومان%n", year, annualInstallment);
//            System.out.printf("قسط سالیانه در سال %d: %.2f تومان%n", year, yearlyInstallment);

            // دو برابر کردن قسط برای سال بعد
            annualInstallment *= (1 + annualIncreasePercentage / 100);

        }
    }
}
