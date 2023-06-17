package src;

import java.util.ArrayList;

/**
 * Класс, реализующий расшифровку по криптографическому ключу
 */
public class Decoding {
    public static final String ERROR_MESSAGE = "Извините, но ваш пароль не был расшифрован.";
    public static final String FILE_NAME = "originalPassword.txt"; // Имя файла, в который будет записан расшифрованный пароль

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

        String originalPassword = encoding.getString(listPassword);
        if (isReallyPassword(originalPassword)) {
            return originalPassword;
        }
        return ERROR_MESSAGE;
    }

    /**
     * Метод, который проверяет строку на правильное расставление пробелов и знаков пунктуации
     * @param password Пароль
     */
    private boolean isReallyPassword(String password) {
        char whiteSpace = ' ';
        if (Math.round(1.0f * findCountOfChar(whiteSpace, password) / password.length() * 100) >= BruteForce.avgPercentageOfSpace && isPunctuationMarksOnRightPlace(password)) {
            return true;
        }
        return false;
    }

    /**
     * Метод, который высчитывает количество пробелов в расшифрованном тексте
     * @param character Символ
     * @return count Число вхождений символа в текст
     */
    private int findCountOfChar(Character character, String password){
        char[] arr = password.toCharArray();
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
