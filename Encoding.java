import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Encoding {
    // Исходный пароль
    private static String originalPassword;
    // Зашифрованный пароль
    private static String encryptedPassword;
    // Криптографический ключ
    private static int cryptoKey;
    private static String path;
    public static void main(String[] args) {
        Encoding encoding = new Encoding();
        cryptoKey = CryptoKey.getCryptoKey(); // Получение криптографического ключа
        originalPassword = encoding.takePassword();
        encryptedPassword = encoding.getEncryptedKey();
        encoding.createFileWithEncryptedPassword();
        System.out.println(encryptedPassword);
    }

    // Получение исходного пароля из текстового файла
    private String takePassword(){
        String lineFromFile = null;
        System.out.print("Введите путь расположения файла с паролем: ");
        Scanner scanner = new Scanner(System.in);
        path = scanner.nextLine();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
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
    private String getEncryptedKey() {
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
    // Создание файла и директории и запись зашифрованного пароля в новосозданный файл
    private void createFileWithEncryptedPassword(){
        String fileName = "encryptedPassword.txt";
        Path filePathDirectory = Path.of(path).getParent();
        // Создание директории, если такой не сущетсвует
        if (!Files.exists(filePathDirectory)){
            try{
                Files.createDirectory(filePathDirectory);
            } catch (IOException e) {
                System.out.println("Ошибка при создании директории");
                throw new RuntimeException(e);
            }
        }
        Path filePath = Paths.get(filePathDirectory.toString(), fileName);
        // Создание файла, если такой не сущетсвует
        if (Files.exists(filePath)){
            writeEncryptedPasswordInFile(filePath.toString());
        }else{
            try{
                Files.createFile(filePath);
                writeEncryptedPasswordInFile(filePath.toString());
            }catch (IOException e){
                System.out.println("Ошибка при создании файла");
                e.printStackTrace();
            }
        }
    }
    // Запись зашифрованного пароля в новосозданный файл
    private void writeEncryptedPasswordInFile(String fileName){
        try(FileWriter fileWriter = new FileWriter(fileName)){
            fileWriter.write(encryptedPassword);
        } catch (IOException e) {
            System.out.println("Ошибка при записи шифрованного пароля в файл");
            e.printStackTrace();
        }
    }
}

