import java.util.ArrayList;

public class Decoding {
    private static String originalPassword;
    private static String encryptedPassword;
    public static final String fileName = "originalPassword.txt"; // Имя файла, в который будет записан расшифрованный пароль

    public static void main(String[] args) {
        Encoding encoding = new Encoding();
        Decoding decoding = new Decoding();

        encryptedPassword = encoding.takePassword(); // Извлекаем зашифрованный пароль из файла
        originalPassword = decoding.getOriginalKey(); // Расшифровка зашифрованного пароля
        encoding.createFileWithEncryptedPassword(fileName, originalPassword);
        System.out.println(originalPassword);
    }
//    public Decoding(){
//        Encoding encoding = new Encoding();
//        encryptedPassword = encoding.takePassword(); // Извлекаем зашифрованный пароль из файла
//        originalPassword = getOriginalKey(); // Расшифровка зашифрованного пароля
//        encoding.createFileWithEncryptedPassword(fileName, originalPassword);
//        System.out.println(originalPassword);
//    }
    // Получение расшифрованного пароля
    private String getOriginalKey() {
        CryptoAlphabet cryptoAlphabet = new CryptoAlphabet();
        Encoding encoding = new Encoding();
        ArrayList<Character> listAlphabet = encoding.getList(cryptoAlphabet.getCryptoAlphabet());
        ArrayList<Character> listPassword = encoding.getList(encryptedPassword);
        for (int i = 0; i < listPassword.size(); i++) {
            Character currentChar = listPassword.get(i);
            if (listAlphabet.contains(currentChar)) {
                int newIndex = (listAlphabet.indexOf(currentChar) - CryptoKey.getCryptoKey() + listAlphabet.size()) % listAlphabet.size();
                listPassword.set(i, listAlphabet.get(newIndex));
            }
        }
        return encoding.getString(listPassword);
    }
}
