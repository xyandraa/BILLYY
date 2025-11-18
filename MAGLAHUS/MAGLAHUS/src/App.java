import java.util.Scanner;
import java.util.Random;

class User {
    private String username;
    private String role;

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}

class PermissionManager {
    public String getPermissions(User user) {
        switch (user.getRole().toLowerCase()) {
            case "student":
                return "VIEW ONLY";
            case "teacher":
                return "VIEW + EDIT";
            case "admin":
                return "FULL ACCESS";
            default:
                return "NO PERMISSION";
        }
    }
}

class SecurityLayer {
    public boolean passwordCheck(String password) {
        return password.equals("password");
    }

    public int generate2FACode() {
        return 100000 + new Random().nextInt(900000);
    }

    public boolean twoFactorCheck(int realCode, int userCode) {
        return realCode == userCode;
    }

    public boolean intrusionDetection(boolean otpMatch) {
        return otpMatch;
    }
}
public class App {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = input.nextLine();

        if (!username.equalsIgnoreCase("billy")) {
            System.out.println("Access denied. Unknown user.");
            return;
        }

        System.out.print("Enter role (student, teacher, admin): ");
        String role = input.nextLine();

        User user = new User(username, role);
        PermissionManager pm = new PermissionManager();
        SecurityLayer sl = new SecurityLayer();

        System.out.println("Logging in as: " + user.getUsername());

        System.out.print("Enter password: ");
        String password = input.nextLine();
        if (!sl.passwordCheck(password)) {
            System.out.println("Password failed.");
            return;
        }
 int otp = sl.generate2FACode();
        System.out.println("Your 2FA code: " + otp);

        System.out.print("Enter the 2FA code: ");
        int typedOtp = input.nextInt();
        input.nextLine(); 

        if (!sl.intrusionDetection(sl.twoFactorCheck(otp, typedOtp))) {
            System.out.println("Suspicious activity detected! Access blocked.");
            return;
        }

        System.out.println("Login successful!");
        System.out.println("Permissions granted: " + pm.getPermissions(user));
    }
}

