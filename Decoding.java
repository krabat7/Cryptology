import java.util.ArrayList;

public class Decoding {
    private final String originalPassword;
    public static final String ERROR_MESSAGE = "Извините, но ваш пароль не был расшифрован.";
    public static final String FILE_NAME = "originalPassword.txt"; // Имя файла, в который будет записан расшифрованный пароль

    /**
     * Конструктор класса
     */
    public Decoding() {
        Encoding encoding = new Encoding();
        String encryptedPassword = encoding.takePassword(); // Извлекаем зашифрованный пароль из файла
        originalPassword = getOriginalKey(encryptedPassword, CryptoKey.getCryptoKey()); // Расшифровка зашифрованного пароля
        encoding.createFileWithEncryptedPassword(FILE_NAME, originalPassword);
    }

    /**
     * Метод, который реализует проверку на частоту использования пробелов и позицию знаков пунткуации в тексте
     * @param password Зашифрованный пароль
     * @param cryptoKey Криптографический ключ
     * @return originalPassword Расшифрованный пароль
     */
    public String getOriginalKey(String password, int cryptoKey) {
        Encoding encoding = new Encoding();
        ArrayList<Character> listAlphabet = encoding.getList(CryptoAlphabet.getCryptoAlphabet());
        ArrayList<Character> listPassword = encoding.getList(password);

        for (int i = 0; i < listPassword.size(); i++) {
            Character currentChar = listPassword.get(i);
            if (listAlphabet.contains(Character.toLowerCase(currentChar))) {
                int newIndex = (listAlphabet.indexOf(Character.toLowerCase(currentChar)) - cryptoKey + listAlphabet.size()) % listAlphabet.size();
                listPassword.set(i, listAlphabet.get(newIndex));
            }
        }

        String origPassword = encoding.getString(listPassword);
        char whiteSpace = ' ';
        if (Math.round(1.0f * findCountOfChar(whiteSpace) / origPassword.length() * 100) >= BruteForce.avgPercentageOfSpace && isPunctuationMarksOnRightPlace(origPassword)) {
            return origPassword;
        }
        return ERROR_MESSAGE;
    }

    /**
     * Метод, который высчитывает количество пробелов в расшифрованном тексте
     * @param character Символ
     * @return count Число вхождений символа в текст
     */
    private int findCountOfChar(Character character){
        char[] arr = originalPassword.toCharArray();
        int count = 0;
        for (char c : arr){
            if (c == character){
                count++;
            }
        }
        return count;
    }

    /**
     * Метод, который проверяет знак пунктуации (без пробела) на его положение в тексте пароля
     * @param password Пароль
     */
    private boolean isPunctuationMarksOnRightPlace(String password){
        String[] arr = password.split("");
        for (int i = 1; i < arr.length - 1; i++) {
            if (CryptoAlphabet.getPunctuationMarks().contains(arr[i])){
                if (CryptoAlphabet.getALPHABET().contains(arr[i - 1]) && CryptoAlphabet.getALPHABET().contains(arr[i + 1])){
                    return false;
                }
            }
        }
        return true;
    }
}
