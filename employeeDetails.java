import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class employeeDetails {
    private static final int Officer_Vacation = 15;
    private static final int Officer_Sick = 10;
    private static final int Staff_Vacation = 10;
    private static final int Staff_Sick = 7;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter employee information:");

        for (int i = 0; i < 3; i++) {
            System.out.print("ID: ");
            String id = sc.nextLine();

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Date of Birth (dd/MM/yyyy): ");
            String dobString = sc.nextLine();
            Date dateOfBirth = parseDate(dobString);

            System.out.print("Email: ");
            String email = sc.nextLine();

            System.out.print("Joining Date (dd/MM/yyyy): ");
            String joiningDateString = sc.nextLine();
            Date joiningDate = parseDate(joiningDateString);

            System.out.print("Employee Type (1. Officer, 2. Staff): ");
            int employeeType = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            Employee employee = null;
            if (employeeType == 1) {
                employee = new Officer(id, name, dateOfBirth, email, joiningDate);
            } else if(employeeType == 2) {
                employee = new Staff(id, name, dateOfBirth, email, joiningDate);
            }
            else {
                System.out.println("Invalid entry. Re-enter again.");
                continue;
            }


            int vacationLeave = calculateLeave(employee, "Vacation");
            int sickLeave = calculateLeave(employee, "Sick");

            System.out.println("\nEmployee Details:");
            System.out.println("ID: " + employee.getId());
            System.out.println("Name: " + employee.getName());
            System.out.println("Date of Birth: " + formatDate(employee.getDateOfBirth()));
            System.out.println("Email: " + employee.getEmail());
            System.out.println("Joining Date: " + formatDate(employee.getJoiningDate()));
            System.out.println("Vacation Leave: " + vacationLeave);
            System.out.println("Sick Leave: " + sickLeave);
        }

        sc.close();
    }

    private static Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    private static int calculateLeave(Employee employee, String leaveType) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, 12); // December
        calendar.set(Calendar.DAY_OF_MONTH, 30); // Last day of the year

        int totalDays = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
        if (calendar.getActualMaximum(Calendar.DAY_OF_YEAR) == 366) {
            totalDays = 366;
        }

        double leaveDays = ((double) (calendar.getTime().getTime() - employee.getJoiningDate().getTime()) / (24 * 60 * 60 * 1000))
                * getTotalLeaveDays(employee, leaveType);

        DecimalFormat decimalFormat = new DecimalFormat("#.##########");
        double dayFraction = Double.parseDouble(decimalFormat.format(leaveDays / totalDays));

        if (dayFraction <0.5) {
            return (int) Math.floor(dayFraction);
        } else {
            return (int) Math.ceil(dayFraction);
        }
    }

    private static int getTotalLeaveDays(Employee employee, String leaveType) {
        if (employee instanceof Officer) {
            if (leaveType.equals("Vacation")) {
                return Officer_Vacation;
            } else if (leaveType.equals("Sick")) {
                return Officer_Sick;
            }
        } else if (employee instanceof Staff) {
            if (leaveType.equals("Vacation")) {
                return Staff_Vacation;
            } else if (leaveType.equals("Sick")) {
                return Staff_Sick;
            }
        }

        return 0;
    }
}
