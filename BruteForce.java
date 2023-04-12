import java.util.ArrayList;

public class BruteForce {
    private static String originalPassword;
    // Зашифрованный пароль
    private static String encryptedPassword;

    public static void main(String[] args) {
        Encoding encoding = new Encoding();
        BruteForce bruteForce = new BruteForce();
        encryptedPassword = encoding.takePassword();
        originalPassword = bruteForce.getOriginalKey();
        encoding.createFileWithEncryptedPassword(Decoding.fileName, originalPassword);
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
    private String getOriginalKey() {
        final int avgPercentageOfSpace = 12;
        String password = null;

        CryptoAlphabet cryptoAlphabet = new CryptoAlphabet();
        Encoding encoding = new Encoding();

        ArrayList<Character> listAlphabet = encoding.getList(cryptoAlphabet.getCryptoAlphabet());
        ArrayList<Character> listPassword = encoding.getList(encryptedPassword);

        for (int i = 0; i < listPassword.size(); i++) {
            Character currentChar = listPassword.get(i);
            if (listAlphabet.contains(currentChar)) {
                int cryptoKey = 0;
                while (cryptoKey <= cryptoAlphabet.getCryptoAlphabet().length()) {
                    int newIndex = (listAlphabet.indexOf(currentChar) - CryptoKey.getCryptoKey() + listAlphabet.size()) % listAlphabet.size();
                    listPassword.set(i, listAlphabet.get(newIndex));
                    password = encoding.getString(listPassword);

                    if (Math.round(findCountOfWhiteSpace(password) / password.length() * 100) > avgPercentageOfSpace) {
                        break;
                    } else {
                        cryptoKey++;
                    }
                }
            }
        }
        return password;
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
}
