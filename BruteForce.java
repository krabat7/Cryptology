public class BruteForce {
    private static String originalPassword;
    private static String encryptedPassword;
    public static final int avgPercentageOfSpace = 12;

    public static void main(String[] args) {
        Encoding encoding = new Encoding();
        BruteForce bruteForce = new BruteForce();
        encryptedPassword = encoding.takePassword();
        originalPassword = bruteForce.getOriginalKeyForBruteForce();
        encoding.createFileWithEncryptedPassword(Decoding.FILE_NAME, originalPassword);
        System.out.println(originalPassword);
    }

    //Во время работы метода getOriginalKey, идет проверка на частоту использования пробелов в
    //зашифрованном коде, за счет чего реализована логика перебора шифра
    //с использованием разных криптографических ключей.
    private String getOriginalKeyForBruteForce() {
        int cryptoKey = 0;
        Decoding decoding = new Decoding();
        CryptoAlphabet cryptoAlphabet = new CryptoAlphabet();
        while (cryptoKey <= cryptoAlphabet.getCryptoAlphabet().length()) {
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
