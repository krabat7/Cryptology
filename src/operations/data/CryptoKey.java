package src.operations.data;

/**
 * Класс, описывающий криптографичесчкий ключ
 */
public class CryptoKey {
    private static final int cryptoKey = 6; // Число для сдвига (Шифр Цезаря)

    /**
     * Геттер, для получения криптографического ключа
     * @return Криптографический ключ
     */
    public static int getCryptoKey(){ return cryptoKey; }
}
