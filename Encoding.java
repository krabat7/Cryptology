import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Encoding {
    private String line;
    public static void main(String[] args) {
        Encoding encoding = new Encoding();
        encoding.takePassword();
    }
    private String takePassword(){
        System.out.print("Введите путь расположения файла с паролем: ");
        Scanner scanner = new Scanner(System.in);
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(scanner.nextLine()))){
            line = bufferedReader.readLine();
            System.out.println(line);
        }catch(IOException e){
            e.printStackTrace();
        }
        return line;
    }
}
