import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Encoding {
    private String lineFromFile;
    private static String originalPassword;
    private static String encryptedPassword;
    public static void main(String[] args) {
        Encoding encoding = new Encoding();
        originalPassword = encoding.takePassword();
    }
    private String takePassword(){
        System.out.print("Введите путь расположения файла с паролем: ");
        Scanner scanner = new Scanner(System.in);
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(scanner.nextLine()))){
            lineFromFile = bufferedReader.readLine();
            System.out.println(lineFromFile);
        }catch(IOException e){
            e.printStackTrace();
        }
        return lineFromFile;
    }
    private void getEncryptedKey(String originalPassword){
        CryptoAlphabet cryptoAlphabet = new CryptoAlphabet();
        cryptoAlphabet.getCryptoAlphabet();
    }
}

