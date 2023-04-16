/**
 * Класс, реализующий расшифровку методом brute force (грубой силой)
 */
public class BruteForce {
    private final String encryptedPassword;
    public static final int avgPercentageOfSpace = 12; // Средняя частота вхождения пробела в тексте в процентах

    /**
     * Конструктор класса
     */
    public BruteForce() {
        Encoding encoding = new Encoding();
        encryptedPassword = encoding.takePassword();
        String originalPassword = getOriginalKeyForBruteForce();
        encoding.createFileWithEncryptedPassword(Decoding.FILE_NAME, originalPassword);
    }

    /**
     * Метод, который реалузиует проверку на частоту использования пробелов в зашифрованном коде,
     * за счет чего реализована логика перебора шифра с использованием разных криптографических ключей.
     * @return password Расшифрованный пароль
     */
    private String getOriginalKeyForBruteForce() {
        int cryptoKey = 0;
        Decoding decoding = new Decoding();
        while (cryptoKey <= CryptoAlphabet.getCryptoAlphabet().length()) {
            String password = decoding.getOriginalKey(encryptedPassword, cryptoKey);
            if (password.equals(Decoding.ERROR_MESSAGE)) {
                cryptoKey++;
            }else{
                return password;
            }
        }
        return Decoding.ERROR_MESSAGE;
    }
}
