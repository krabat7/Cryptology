public class CryptoAlphabet {
    private static final String ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщьыъэюя";
    private static final String PUNCTUATION_MARKS = ".,””:-!?";
    private static final String WHITE_SPACE = " ";
    public String getCryptoAlphabet(){
        return ALPHABET + PUNCTUATION_MARKS + WHITE_SPACE;
    }
    public String getPunctuationMarks(){
        return PUNCTUATION_MARKS;
    }

    public String getALPHABET() {
        return ALPHABET;
    }
}
