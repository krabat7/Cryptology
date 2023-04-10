public class CryptoKey {
    // Число будет сдвигаться при шифровке/расишфровеке
    // на numberToShift в криптографическом алфавите.
    private static int numberToShift = 6;
    public static int getNumberToShift(){
        return numberToShift;
    }
}
