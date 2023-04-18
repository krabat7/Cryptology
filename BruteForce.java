/**
 * Класс, реализующий расшифровку методом brute force (грубой силой)
 */
public class BruteForce {
    public static final int avgPercentageOfSpace = 12; // Средняя частота вхождения пробела в тексте в процентах

    /**
     * Метод, который реалузиует проверку на частоту использования пробелов в зашифрованном коде,
     * за счет чего реализована логика перебора шифра с использованием разных криптографических ключей.
     * @return password Расшифрованный пароль
     */
    public String getOriginalKeyForBruteForce(String encryptedPassword) {
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
