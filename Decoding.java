import java.util.ArrayList;

public class Decoding {
    private static String originalPassword;
    private static String encryptedPassword;
    public static final String ERROR_MESSAGE = "Извините, но ваш пароль не был расшифрован.";
    public static final String FILE_NAME = "originalPassword.txt"; // Имя файла, в который будет записан расшифрованный пароль

    public static void main(String[] args) {
        Encoding encoding = new Encoding();
        Decoding decoding = new Decoding();

        encryptedPassword = encoding.takePassword(); // Извлекаем зашифрованный пароль из файла
        originalPassword = decoding.getOriginalKey(encryptedPassword, CryptoKey.getCryptoKey()); // Расшифровка зашифрованного пароля
        encoding.createFileWithEncryptedPassword(FILE_NAME, originalPassword);
    }
    // Метод реализует проверку на частоту использования пробелов и позицию знаков пунткуации в тексте,
    // Возвращает расшифрованный пароль.
    public String getOriginalKey(String password, int cryptoKey) {
        CryptoAlphabet cryptoAlphabet = new CryptoAlphabet();
        Encoding encoding = new Encoding();

        ArrayList<Character> listAlphabet = encoding.getList(cryptoAlphabet.getCryptoAlphabet());
        ArrayList<Character> listPassword = encoding.getList(password);

        for (int i = 0; i < listPassword.size(); i++) {
            Character currentChar = listPassword.get(i);
            if (listAlphabet.contains(Character.toLowerCase(currentChar))) {
                int newIndex = (listAlphabet.indexOf(Character.toLowerCase(currentChar)) - cryptoKey + listAlphabet.size()) % listAlphabet.size();
                listPassword.set(i, listAlphabet.get(newIndex));
            }
        }

        originalPassword = encoding.getString(listPassword);
        char whiteSpace = ' ';

        if (Math.round(1.0f * findCountOfChar(whiteSpace) / originalPassword.length() * 100) >= BruteForce.avgPercentageOfSpace && isPunctuationMarksOnRightPlace(originalPassword)) {
            return originalPassword;
        }
        return ERROR_MESSAGE;
    }
    // Метод возвращает количество пробелов в расшифрованном тексте
    public int findCountOfChar(Character character){
        char[] arr = originalPassword.toCharArray();
        int count = 0;
        for (char c : arr){
            if (c == character){
                count++;
            }
        }
        return count;
    }
    //Метод проверяет знак пунктуации (без пробела) на его положение в тексте пароля
    //Если знак находится между 2мя буквами алфавита, то кидает false.
    private boolean isPunctuationMarksOnRightPlace(String password){
        CryptoAlphabet cryptoAlphabet = new CryptoAlphabet();

        String[] arr = password.split("");
        for (int i = 1; i < arr.length - 1; i++) {
            if (cryptoAlphabet.getPunctuationMarks().contains(arr[i])){
                if (cryptoAlphabet.getALPHABET().contains(arr[i - 1]) && cryptoAlphabet.getALPHABET().contains(arr[i + 1])){
                    return false;
                }
            }
        }
        return true;
    }
}
