import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Utils {

    public static void scroll(WebDriver driver , int w , int h){
        JavascriptExecutor js =  (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy("+w+","+h+")", "" );
    }

    public static String generatePhoneNumber(){
        int min = 1000000;
        int max = 9999999;

        int suffixNum = (int) (Math.random()*(max-min)+min);

        String phoneNum = "0173"+suffixNum;

        return  phoneNum;


    }

    private static char getRandomCharacter(String characterSet) {
        int randomIndex = (int) (Math.random() * characterSet.length());
        return characterSet.charAt(randomIndex);
    }
    public static String generateRandomPassword(){
        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String symbols = "!@#$%^&*()-_+=";
        // Combine all character sets
        String allCharacters = uppercaseLetters + lowercaseLetters + digits + symbols;
        // Generate a password with at least one lowercase letter
        StringBuilder password = new StringBuilder();
        password.append(getRandomCharacter(uppercaseLetters)); // At least one uppercase letter
        password.append(getRandomCharacter(lowercaseLetters)); // At least one lowercase letter
        password.append(getRandomCharacter(digits));
        password.append(getRandomCharacter(symbols));

        // Add more random characters if needed
        int remainingLength = 10 - password.length(); // Adjust the length as per your requirement
        for (int i = 0; i < remainingLength; i++) {
            password.append(getRandomCharacter(allCharacters));
        }

        return password.toString();
    }

}
