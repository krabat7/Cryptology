package src.operations.data;

/**
 * Класс, описывающий криптографичесчкий алфавит
 */
public class CryptoAlphabet {
    private static final String ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщьыъэюя";
    private static final String PUNCTUATION_MARKS = ".,””:-!?";
    private static final String WHITE_SPACE = " ";

    /**
     * Геттер, для получения криптографического алфвавита, включащего кириллицу, знаки пунктуации и пробел
     * @return Криптографический алфвавит
     */
    public static String getCryptoAlphabet(){
        return ALPHABET + PUNCTUATION_MARKS + WHITE_SPACE;
    }

    /**
     * Геттер, для получения знаков пунктуации
     * @return Знаки пунктуации
     */
    public static String getPunctuationMarks(){
        return PUNCTUATION_MARKS;
    }

    /**
     * Геттер, для получения алфавита
     * @return Алфвавит
     */
    public static String getALPHABET() {
        return ALPHABET;
    }
}
