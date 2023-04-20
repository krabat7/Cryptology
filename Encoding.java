import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Класс, реализующий щифрование по криптографическому ключу
 */
public class Encoding {
    private String path;
    private static final String ERROR_MESSAGE = "Извините, однако мы не можем зашифровать ваш пароль, так как он не содержит хотя бы одного символа из кириллицы или знака пунктуации.";
    public final String FILE_NAME = "encryptedPassword.txt"; // Имя файла, в который будет записан зашифрованный пароль

    /**
     * Метод, получающий исходного пароля из текстового файла
     * @return lineFromFile Исходный пароль
     */
    public String takePassword(){
        String lineFromFile = null;
        System.out.print("Введите путь расположения файла: ");
        Scanner scanner = new Scanner(System.in);
        path = scanner.nextLine();
        // Можно указать путь для разных ОС с разными разделителями
        path = path.replace("\\", File.separator);
        path = path.replace("/", File.separator);
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

    /**
     * Метод, который реализует шифрование пароля по Шифру Цезаря.
     * Криптографический алфавит сдвигается на определенное количество символов, которое заданно криптографическим ключом)
     * @param originalPassword Исходный пароль
     * @return Зашифрованный пароль
     */
    public String getEncryptedKey(String originalPassword) {
        ArrayList<Character> listAlphabet = getList(CryptoAlphabet.getCryptoAlphabet());
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

    /**
     * Метод, который преобразовывает строку в лист
     * @param str Строка
     * @return list Список
     */
    public ArrayList<Character> getList(String str){
        ArrayList<Character> list = new ArrayList<>();
        for (char c : str.toCharArray()) {
            list.add(c);
        }
        return list;
    }

    /**
     * Метод, который преобразовывает листа в строку
     * @param arrayList Список
     * @return Строка
     */
    public String getString(ArrayList<Character> arrayList){
        StringBuilder sb = new StringBuilder();
        for (char i : arrayList){
            sb.append(i);
        }
        return sb.toString();
    }

    /**
     * Метод, который создает файл и директорию, а также записывает пароль в новосозданный файл
     * @param fileName Имя файла
     * @param password Пароль
     */
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
        System.out.println("Полученные данные: " + password);
        System.out.println("Пароль записан в директорию " + filePathDirectory);
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

    /**
     * Метод, который записывает зашифрованный пароль в новосозданный файл
     * @param fileName Имя файла
     * @param password Пароль
     */
    private void writeEncryptedPasswordInFile(String fileName, String password){
        try(FileWriter fileWriter = new FileWriter(fileName)){
            fileWriter.write(password);
        } catch (IOException e) {
            System.out.println("Ошибка при записи пароля в файл");
            e.printStackTrace();
        }
    }
}

