package service.impl;

import dto.CardDtoWithId;
import dto.LoanRegistrationDto;
import dto.mapStruct.LoanMapper;
import entity.*;
import entity.enumration.LoanType;
import entity.enumration.MaritalStatus;
import entity.enumration.UniversityType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import menu.util.Input;
import repository.LoanRepository;
import service.InstallmentService;
import service.LoanService;
import service.StudentService;
import util.UserSession;
import util.jalaliCalender.JalaliDate;
import util.jalaliCalender.JalaliDateUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final UserSession USER_SESSION;
    private final Input INPUT;
    private final InstallmentService INSTALLMENT_SERVICE;
    private final StudentService STUDENT_SERVICE;
    private final LoanMapper loanMapper;
    private Validator validator;


    public LoanServiceImpl(LoanRepository loanRepository, UserSession userSession, Input input, InstallmentService installmentService, StudentService studentService, LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        USER_SESSION = userSession;
        INPUT = input;
        INSTALLMENT_SERVICE = installmentService;
        STUDENT_SERVICE = studentService;
        this.loanMapper = loanMapper;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Override
    public Loan registerLoan(Loan loan) {
        return loanRepository.registerLoan(loan);
    }

    @Override
    public Loan FindStudentLoan(Student student, LoanType loanType) {
        return loanRepository.findStudentLoan(student, loanType);
    }

    @Override
    public Loan findLoanByNationalCode(String nationalCode) {
        return loanRepository.findLoanByNationalCode(nationalCode);
    }

    @Override
    public boolean checkRequestDateIsValid(LoanType loanType, Student student) {
        try {

            //date of start and end registration
            Calendar cal = Calendar.getInstance();
            int currentYear = cal.get(Calendar.YEAR);
            // first period 1 aban to 8 aban
            cal.set(currentYear, Calendar.AUGUST, 22, 0, 0, 0); // 1 آبان
            Date startPeriod1 = cal.getTime();
            cal.set(currentYear, Calendar.SEPTEMBER, 29, 23, 59, 59); // 8 آبان
            Date endPeriod1 = cal.getTime();

            //second period 25 bahman to 2 esfand
            cal.set(currentYear, Calendar.FEBRUARY, 14, 0, 0, 0); // 25 بهمن
            Date startPeriod2 = cal.getTime();
            cal.set(currentYear, Calendar.FEBRUARY, 21, 23, 59, 59); // 2 اسفند
            Date endPeriod2 = cal.getTime();

            // current date
            Date currentDateTime = new Date();


            // چک کردن تاریخ جاری با دوره‌های ثبت‌نام
//        if ((currentDateTime.after(startPeriod1) && currentDateTime.before(endPeriod1)) ||
//                (currentDateTime.after(startPeriod2) && currentDateTime.before(endPeriod2))) {

            Loan studentLoans = FindStudentLoan(student, loanType);

            if (studentLoans != null) {
                if (loanType != LoanType.HOUSING_LOAN) {
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
                } else {
                    System.out.println("شما قبلا تسهیلات ودیعه مسکن را دریافت کرده اید و قادر به دریافت مجدد نیستید");
                    return false;

                }
            }
            return true;
//
//        } else
//        {
//            System.out.println("در حال حاضر پنجره ثبت‌ نام وام بسته است.");
//            return false;
//        }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public int getCheckLoanCondition(LoanTypeCondition loanTypeCondition) {
        try {
            Student student = STUDENT_SERVICE.findStudentById(USER_SESSION.getTokenId());
            if (loanTypeCondition.getLoanType() == LoanType.TUITION_FEE_LOAN && USER_SESSION.getUniversityType() == UniversityType.Rozane) {
                System.out.println("این وام متعلق به دانشجویان شهریه پرداز است");
                return 0;
            }
            if (loanTypeCondition.getLoanType() == LoanType.HOUSING_LOAN) {
                if (student.getMaritalStatus() == MaritalStatus.SINGLE || student.isDormitoryResident()) {
                    System.out.println("این وام متعلق به دانشجویان متاهلی است که ساکن خوابگاه نیستند");
                    return 0;
                }
            }


            if (checkRequestDateIsValid(loanTypeCondition.getLoanType(), student)) {
                String loanAmountt = String.format("%.0f", loanTypeCondition.getAmount());
                if (loanTypeCondition != null) {
                    System.out.println("""
                            تسهیلاتی انتخابی شما به مبلغ %s می باشدآیا مایل به دریافت این تسهیلات می باشید؟
                            1 .تایید
                            2 .انصراف
                            """.formatted(loanAmountt));
                    return INPUT.scanner.nextInt();
                } else {
                    System.out.println("وام درخواستی در حال حاضر در سیستم تعریف نشده است.");
                    return 0;
                }
            } else {

                return 0;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }

    }

    @Override
    public Date calcInstallmentStartDate(Student student) {

        Date entryYear = student.getEntryYear();
        LocalDate localDbDate = entryYear.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate updatedEntryYearDate = localDbDate;
        switch (student.getEducationGrade()) {
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
    public Set<Installment> calculateInstallments(double loanAmount, double annualIncreasePercentage, Loan loan, Date startDate) {
        Set<Installment> installments = new HashSet<>();
        double monthlyInterestRate = (4.0 / 100) / 12;
        double initialInstallment = (loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, 60))
                / (Math.pow(1 + monthlyInterestRate, 60) - 1);
        initialInstallment = Math.round(initialInstallment * 10.0) / 10.0;
        int count = 0;
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
                Installment installment = Installment.builder().installmentAmount(initialInstallment)
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
    public Boolean registerLoan(LoanRegistrationDto loanRegistrationDTO) {
        try {
            Set<ConstraintViolation<LoanRegistrationDto>> violations = validator.validate(loanRegistrationDTO);


            if (!violations.isEmpty()) {
                System.out.println("Registration failed please fix this errors and try again:");
                for (ConstraintViolation<LoanRegistrationDto> violation : violations) {
                    System.out.println("\u001B[31m" + violation.getMessage() + "\u001B[0m");
                }
                throw new IllegalArgumentException("Loan registration failed due to validation errors.");

            }


            Student student = STUDENT_SERVICE.findStudentById(USER_SESSION.getTokenId());

            if (loanRegistrationDTO.loanType().getLoanType() == LoanType.HOUSING_LOAN) {

                Spouse spouse = loanMapper.toSpouse(loanRegistrationDTO.spouse());
                student.setSpouse(spouse);
            }

            student.setAddress(loanRegistrationDTO.address());
            student.setContractNumber(loanRegistrationDTO.contractNumber());
            Loan loan = loanMapper.toLoan(loanRegistrationDTO);

            loan.setPaymentDate(new Date());
            loan.setRegisterLoanDate(new Date());
            loan.setStudent(student);
            loan.setRemainLoanAmount(loanRegistrationDTO.loanType().getAmount());
            loan.setStartInstallments(calcInstallmentStartDate(student));

            Date startDate = calcInstallmentStartDate(student);
            Set<Installment> installments = calculateInstallments(loanRegistrationDTO.loanType().getAmount(), 100, loan, startDate);
            INSTALLMENT_SERVICE.setInstallment(installments);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    @Override
    public Boolean checkSpouseLoan(String natCode) {
        try {
            Loan loanByNationalCode = loanRepository.findLoanByNationalCode(natCode);
            if (loanByNationalCode != null) {
                throw new IllegalArgumentException("همسر شما وام ودیعه مسکن دریافت کرده است و شما قادر به دریافت این وام نخواهید بود");

            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Boolean checkIsOpenRegisterDate() {
    //date of start and end registration
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        // first period 1 aban to 8 aban
        cal.set(currentYear, Calendar.AUGUST, 22, 0, 0, 0); // 1 آبان (ماه‌ها از 0 شروع می‌شوند)
        Date startPeriod1 = cal.getTime();
        cal.set(currentYear, Calendar.SEPTEMBER, 29, 23, 59, 59); // 8 آبان
        Date endPeriod1 = cal.getTime();

        //second period 25 bahman to 2 esfand
        cal.set(currentYear, Calendar.FEBRUARY, 14, 0, 0, 0); // 25 بهمن
        Date startPeriod2 = cal.getTime();
        cal.set(currentYear, Calendar.FEBRUARY, 21, 23, 59, 59); // 2 اسفند
        Date endPeriod2 = cal.getTime();

        // current date
        Date currentDateTime = new Date();


        // check current date with registration's period
        if ((currentDateTime.after(startPeriod1) && currentDateTime.before(endPeriod1)) ||
                (currentDateTime.after(startPeriod2) && currentDateTime.before(endPeriod2))) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public List<Loan> getAllStudentLoan(Long studentId) {
        return loanRepository.getAllStudentLoan(studentId);
    }


}
