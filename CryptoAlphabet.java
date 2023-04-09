public class CryptoAlphabet {
    private static final String ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщьыъэюя";
    private static final String PUNCTUATION_MARKS = ".,””:-!? ";
    public String getCryptoAlphabet(){
        return ALPHABET + PUNCTUATION_MARKS;
    }
}
