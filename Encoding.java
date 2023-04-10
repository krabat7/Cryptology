import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Encoding {
    // Исходный пароль
    private static String originalPassword;
    // Зашифрованный пароль
    private static String encryptedPassword;
    // Криптографический ключ
    private static int cryptoKey;
    public static void main(String[] args) {
        Encoding encoding = new Encoding();
        cryptoKey = CryptoKey.getCryptoKey(); // Получение криптографического ключа
        originalPassword = encoding.takePassword();
        encryptedPassword = encoding.getEncryptedKey(originalPassword);
        System.out.println(encryptedPassword);
    }

    // Получение исходного пароля из текстового файла
    private String takePassword(){
        String lineFromFile = null;
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

    // Шифрование пароля по Шифру Цезаря.
    // Криптографический алфавит сдвигается на определенное количество символов,
    // которое заданно криптографическим ключом)
    private String getEncryptedKey(String originalPassword) {
        CryptoAlphabet cryptoAlphabet = new CryptoAlphabet();
        ArrayList<Character> listAlphabet = getList(cryptoAlphabet.getCryptoAlphabet());
        ArrayList<Character> listPassword = getList(originalPassword);
        for (int i = 0; i < listPassword.size(); i++) {
            Character currentChar = listPassword.get(i);
            if (listAlphabet.contains(currentChar)) {
                int newIndex = (listAlphabet.indexOf(currentChar) + cryptoKey) % listAlphabet.size();
                listPassword.set(i, listAlphabet.get(newIndex));
            }
        }
        return getString(listPassword);
    }

    // Преобразование строки в лист
    private ArrayList<Character> getList(String str){
        ArrayList<Character> listAlphabet = new ArrayList<>();
        for (char c : str.toCharArray()) {
            listAlphabet.add(c);
        }
        return listAlphabet;
    }

    // Преобразование листа в строку
    private String getString(ArrayList<Character> arrayList){
        StringBuilder sb = new StringBuilder();
        for (char i : arrayList){
            sb.append(i);
        }
        return sb.toString();
    }
}

