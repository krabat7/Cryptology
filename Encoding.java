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
    public static final String ERROR_MESSAGE = "Извините, однако мы не можем зашифровать ваш пароль, так как он не содержит хотя бы одного символа из кириллицы или знака пунктуации.";
    public static final String FILE_NAME = "encryptedPassword.txt"; // Имя файла, в который будет записан зашифрованный пароль
    public String path;

    public static void main(String[] args) {
        Encoding encoding = new Encoding();
        originalPassword = encoding.takePassword();
        encryptedPassword = encoding.getEncryptedKey();
        encoding.createFileWithEncryptedPassword(FILE_NAME, encryptedPassword);
    }
    // Получение исходного пароля из текстового файла
    public String takePassword(){
        String lineFromFile = null;
        System.out.print("Введите путь расположения файла с паролем: ");
        Scanner scanner = new Scanner(System.in);
        path = scanner.nextLine();
        // Можно указать путь для разных ОС с разными разделителями
        path = path.replace("/", File.separator);
        path = path.replace("\\", File.separator);
        File file = new File(path);
        while(!file.exists() || file.isDirectory()){
            if (!file.exists()){
                System.out.print("Файл не найден, введите путь еще раз: ");
                path = scanner.nextLine();
                file = new File(path);
            }
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            while ((lineFromFile = bufferedReader.readLine()) == null) {
                System.out.print("Файл - пустой. ");
                takePassword();
            }
        } catch (IOException e) {
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
            if (listAlphabet.contains(Character.toLowerCase(currentChar))) {
                int newIndex = (listAlphabet.indexOf(Character.toLowerCase(currentChar)) + CryptoKey.getCryptoKey()) % listAlphabet.size();
                listPassword.set(i, listAlphabet.get(newIndex));
            }
        }
        if (getString(listPassword).equals(originalPassword)){
            return ERROR_MESSAGE;
        }
        return getString(listPassword);
    }

    // Преобразование строки в лист
    public ArrayList<Character> getList(String str){
        ArrayList<Character> listAlphabet = new ArrayList<>();
        for (char c : str.toCharArray()) {
            listAlphabet.add(c);
        }
        return listAlphabet;
    }

    // Преобразование листа в строку
    public String getString(ArrayList<Character> arrayList){
        StringBuilder sb = new StringBuilder();
        for (char i : arrayList){
            sb.append(i);
        }
        return sb.toString();
    }
    // Создание файла и директории и запись пароля в новосозданный файл
    public void createFileWithEncryptedPassword(String fileName, String password){
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
        System.out.println("Полученный пароль: " + password);
        // Создание файла, если такой не сущетсвует
        if (Files.exists(filePath)){
            writeEncryptedPasswordInFile(filePath.toString(), password);
        }else{
            try{
                Files.createFile(filePath);
                writeEncryptedPasswordInFile(filePath.toString(), password);
            }catch (IOException e){
                System.out.println("Ошибка при создании файла");
                e.printStackTrace();
            }
        }
    }
    // Запись зашифрованного пароля в новосозданный файл
    private void writeEncryptedPasswordInFile(String fileName, String password){
        try(FileWriter fileWriter = new FileWriter(fileName)){
            fileWriter.write(password);
        } catch (IOException e) {
            System.out.println("Ошибка при записи пароля в файл");
            e.printStackTrace();
        }
    }
}

