public class BruteForce {
    private static String originalPassword;
    private static String encryptedPassword;

    public static void main(String[] args) {
        Encoding encoding = new Encoding();
        BruteForce bruteForce = new BruteForce();
        encryptedPassword = encoding.takePassword();
        originalPassword = bruteForce.getOriginalKeyForBruteForce();
        encoding.createFileWithEncryptedPassword(Decoding.FILE_NAME, originalPassword);
        System.out.println(originalPassword);
    }
//    public BruteForce() {
//        Encoding encoding = new Encoding();
//        encryptedPassword = encoding.takePassword();
//        originalPassword = getOriginalKey();
//        encoding.createFileWithEncryptedPassword(Decoding.fileName, originalPassword);
//        System.out.println(originalPassword);
//    }

    //Во время работы метода getOriginalKey, идет проверка на частоту использования пробелов в
    //зашифрованном коде, за счет чего реализована логика перебора шифра
    //с использованием разных криптографических ключей.
    private String getOriginalKeyForBruteForce() {
        final int avgPercentageOfSpace = 12;
        int cryptoKey = 0;
        Decoding decoding = new Decoding();
        CryptoAlphabet cryptoAlphabet = new CryptoAlphabet();
        while (cryptoKey <= cryptoAlphabet.getCryptoAlphabet().length()) {
            String password = decoding.getOriginalKey(encryptedPassword, cryptoKey);
            if (Math.round(1.0f * findCountOfWhiteSpace(password) / password.length() * 100) >= avgPercentageOfSpace && isPunctuationMarksOnRightPlace(password)) {
                return password;
            }
            cryptoKey++;
        }
        return "Извините, но ваш пароль не был расшифрован.";

    }
    // Метод возвращает количество пробелов в расшифрованном тексте
    private int findCountOfWhiteSpace(String password){
        char[] arr = password.toCharArray();
        int count = 0;
        for (char c : arr){
            if (c == ' '){
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
