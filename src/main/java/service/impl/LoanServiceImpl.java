package service.impl;

import entity.*;
import entity.enumration.Bank;
import entity.enumration.LoanType;
import entity.enumration.MaritalStatus;
import entity.enumration.UniversityType;
import menu.util.Input;
import menu.util.Message;
import repository.LoanRepository;
import service.InstallmentService;
import service.LoanService;
import util.UserSession;
import util.jalaliCalender.JalaliDate;
import util.jalaliCalender.JalaliDateUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final UserSession USER_SESSION;
    private final Input INPUT;
    private final InstallmentService INSTALLMENT_SERVICE;
    public LoanServiceImpl(LoanRepository loanRepository, UserSession userSession, Input input, InstallmentService installmentService) {
        this.loanRepository = loanRepository;
        USER_SESSION = userSession;
        INPUT = input;

        INSTALLMENT_SERVICE = installmentService;
    }

    @Override
    public Loan registerLoan(Loan loan) {
        return loanRepository.registerLoan(loan);
    }

    @Override
    public Loan FindStudentLoan(Student student, LoanType loanType) {
        return loanRepository.findStudentLoan(student,loanType);
    }

    @Override
    public Loan findLoanByNationalCode(String nationalCode) {
        return loanRepository.findLoanByNationalCode(nationalCode);
    }

    @Override
    public boolean checkRequestDateIsValid(LoanType loanType,Student student) {


        // تاریخ‌های شروع و پایان دوره‌های ثبت‌نام
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        // دوره اول: 1 آبان تا 8 آبان
        cal.set(currentYear, Calendar.AUGUST, 22, 0, 0, 0); // 1 آبان (ماه‌ها از 0 شروع می‌شوند)
        Date startPeriod1 = cal.getTime();
        cal.set(currentYear, Calendar.SEPTEMBER, 29, 23, 59, 59); // 8 آبان
        Date endPeriod1 = cal.getTime();

        // دوره دوم: 25 بهمن تا 2 اسفند
        cal.set(currentYear, Calendar.FEBRUARY, 14, 0, 0, 0); // 25 بهمن
        Date startPeriod2 = cal.getTime();
        cal.set(currentYear, Calendar.FEBRUARY, 21, 23, 59, 59); // 2 اسفند
        Date endPeriod2 = cal.getTime();

        // تاریخ جاری سیستم
        Date currentDateTime = new Date();



        // چک کردن تاریخ جاری با دوره‌های ثبت‌نام
        if ((currentDateTime.after(startPeriod1) && currentDateTime.before(endPeriod1)) ||
                (currentDateTime.after(startPeriod2) && currentDateTime.before(endPeriod2))) {

            Loan studentLoans = FindStudentLoan(student, loanType);

            if (studentLoans != null) {
                if (loanType != LoanType.HOUSING_LOAN){
                    if ((currentDateTime.after(startPeriod1) && currentDateTime.before(endPeriod1)) &&
                            (studentLoans.getRegisterLoanDate().after(startPeriod1) && studentLoans.getRegisterLoanDate().before(endPeriod1))
                    ) {

                        System.out.println("شما قبلاً در این نیمسال ثبت ‌نام کرده‌اید و نمی‌توانید دوباره ثبت ‌نام کنید.");

                        return false;
                    } else if ((currentDateTime.after(startPeriod2) && currentDateTime.before(endPeriod2)) &&
                            (studentLoans.getRegisterLoanDate().after(startPeriod2) && studentLoans.getRegisterLoanDate().before(endPeriod2))
                    ) {
                        System.out.println("شما قبلاً در این نیمسال ثبت ‌نام کرده‌اید و نمی‌توانید دوباره ثبت ‌نام کنید.");
                        return false;
                    }
                }
                else
                {
                    System.out.println("شما قبلا تسهیلات ودیعه مسکن را دریافت کرده اید و قادر به دریافت مجدد نیستید");
                    return false;

                }
            }
            return true;

        } else
        {
            System.out.println("در حال حاضر پنجره ثبت‌ نام وام بسته است.");
            return false;
        }
    }

    @Override
    public int getCheckLoanCondition(LoanTypeCondition loanTypeCondition, Student student) {
        if(loanTypeCondition.getLoanType()==LoanType.TUITION_FEE_LOAN && USER_SESSION.getUniversityType()== UniversityType.Rozane){
            System.out.println("این وام متعلق به دانشجویان شهریه پرداز است");
            return 0;
        }
        else if(loanTypeCondition.getLoanType()==LoanType.HOUSING_LOAN
                && USER_SESSION.getMaritalStatus()== MaritalStatus.SINGLE && USER_SESSION.getIsDormitoryResident()){
            System.out.println("این وام متعلق به دانشجویان متاهلی است که ساکن خوابگاه نیستند");
            return 0;
        }
        else{

            if (checkRequestDateIsValid(loanTypeCondition.getLoanType(),student)) {
                if (loanTypeCondition != null) {
                    System.out.println("""
                        تسهیلاتی که در قالب تسهیلات دانشجویی در اختیار شما قرار خواهد 
                        گرفت به مبلغ %s می باشدآیا مایل به دریافت این تسهیلات می باشید؟
                        1 .تایید
                        2 .انصراف
                        """.formatted(loanTypeCondition.getAmount()));
                    return INPUT.scanner.nextInt();
                }
                else {
                    System.out.println("وام درخواستی در حال حاضر در سیستم تعریف نشده است.");
                    return 0;
                }
            } else {

                return 0;
            }
        }
    }

    @Override
    public Date calcInstallmentStartDate(Student student)
    {

        Date entryYear=student.getEntryYear();
        LocalDate localDbDate = entryYear.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate updatedEntryYearDate=localDbDate;
        switch (student.getEducationGrade()){
            case ASSOCIATE_DEGREE:
            case MASTERS_DISCONTINUOUS:
                updatedEntryYearDate = localDbDate.plusYears(2);
                break;
            case BACHELORS_CONTINUOUS:
            case BACHELORS_DISCONTINUOUS:
                updatedEntryYearDate = localDbDate.plusYears(4);
                break;
            case MASTERS_CONTINUOUS:
                updatedEntryYearDate = localDbDate.plusYears(6);
                break;
            case PROFESSIONAL_DOCTORATE:
            case DOCTORATE_CONTINUOUS:
            case PHD_DISCONTINUOUS:
                updatedEntryYearDate = localDbDate.plusYears(5);
                break;
        }
        return Date.from(updatedEntryYearDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    }


    @Override
    public Set<Installment> calculateInstallments(double loanAmount, double annualIncreasePercentage, Loan loan, Date startDate)
    {
        Set<Installment> installments=new HashSet<>();
        double monthlyInterestRate = (4.0 / 100) / 12;
        double initialInstallment = ( loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, 60))
                / (Math.pow(1 + monthlyInterestRate, 60) - 1);
        int count=0;
        JalaliDate installmentStartDate = JalaliDateUtil.MiladyToShamsy(startDate);
        int year_ = installmentStartDate.Year;
        int month = installmentStartDate.Month;
        int day = installmentStartDate.Day;
        for (int year = 1; year <= 5; year++) {
            for (int i = 0; i < 12; i++) {
                month++;
                if (month > 12) {
                    month = 1;
                    year_++;
                }

                JalaliDate resultDate = new JalaliDate(year_, month, day, 0, 0, 0);
                Calendar calendar = JalaliDateUtil.ShamsyToMilady(resultDate);
                count++;
                Installment installment=Installment.builder().installmentAmount(initialInstallment)
                        .installmentDate(calendar.getTime())
                        .installmentNumber(count)
                        .loan(loan)
                        .build();
                installments.add(installment);
            }

            initialInstallment *= (1 + annualIncreasePercentage / 100);
        }
        return installments;
    }

    @Override
    public void registerHousingLoan(Student student,String nationalCode, String name, String lastName, String address, String contractNumber) {
        Loan loanByNationalCode = findLoanByNationalCode(nationalCode);
        if(loanByNationalCode != null) {
            System.out.println("همسر شما وام ودیعه مسکن دریافت کرده است و شما قادر به دریافت این وام نخواهید بود");
            return;
        }
        Spouse spouse = Spouse.builder().name(name)
                .lastName(lastName)
                .nationalCode(nationalCode)
                .build();
        student.setSpouse(spouse);
        student.setContractNumber(contractNumber);
        student.setAddress(address);

    }

    @Override
    public void finalRegisterLoan(Student student, LoanTypeCondition loanType, String cardNumber, String expireDate, int cvv2, Bank bank) {
        Card card = Card.builder()
                .cardNumber(cardNumber)
                .expireDate(expireDate)
                .cvv2(cvv2)
                .bank(bank)
                .build();

        Loan loan = Loan.builder()
                .card(card)
                .loanType(loanType)
                .paymentDate(new Date())
                .registerLoanDate(new Date())
                .remainLoanAmount(loanType.getAmount())
                .startInstallments(calcInstallmentStartDate(student))
                .student(student)
                .build();

        Date startDate = calcInstallmentStartDate(student);
        Set<Installment> installments = calculateInstallments(loanType.getAmount(), 100, loan, startDate);
        INSTALLMENT_SERVICE.setInstallment(installments);

        // Further processing if required
    }





}
