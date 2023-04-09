import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Encoding {
    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    try(BufferedReader bufferedReader = new BufferedReader(new FileReader(scanner.nextLine()))){
        String line = bufferedReader.readLine();
        System.out.println(line);
    }catch(IOException e){
        e.printStackTrace();
    }
    }
}
