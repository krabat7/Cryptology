import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class StatisticalAnalysis {
    private static String originalPassword;
    private static String encryptedPassword;
    private static String additionalText;
    private static Map<Character, Integer> mapPassword; // Мапа со статисткой вхождений символа в пароль
    private static Map<Character, Integer> mapAdditionalText; // Мапа со статисткой вхождений символа в дополнительный текст
    public static void main(String[] args) {

        Encoding encoding = new Encoding();
        encryptedPassword = encoding.takePassword();
        additionalText = encoding.takePassword();

        mapPassword = getStatsFromText(encryptedPassword);
        mapAdditionalText = getStatsFromText(additionalText);

        for (var entry : mapPassword.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        for (var entry : mapAdditionalText.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        originalPassword = getOriginalPasswordFromStat(mapPassword, mapAdditionalText);
        System.out.println("Конечный пароль " + originalPassword);

    }
    // Получение исходного пароля, исходя из статистики вхождения символов,
    // которая была получена из дополнительного файла
    private static String getOriginalPasswordFromStat(Map<Character, Integer> mapPassword, Map<Character, Integer> mapAdditionalText){

        // Получаем итераторы для каждой мапы
        Iterator<Map.Entry<Character, Integer>> iterator1 = mapPassword.entrySet().iterator();
        Iterator<Map.Entry<Character, Integer>> iterator2 = mapAdditionalText.entrySet().iterator();
        ArrayList<Integer> listOfUsedChar = new ArrayList<>();

        while (iterator1.hasNext()) {
            Map.Entry<Character, Integer> entry1 = iterator1.next();
            Map.Entry<Character, Integer> entry2 = iterator2.next();
            Character oldChar = entry1.getKey(); // Получение символа из зашифрованного пароля
            Character newChar = entry2.getKey(); // Получение символа из доплнительного текста
            System.out.println(oldChar + " __ " + newChar);
            System.out.println(encryptedPassword);
            encryptedPassword = encryptedPassword.replace(oldChar, newChar);
            char[] arrEncryptedPassword = encryptedPassword.toCharArray();
            for (int i = 0; i < arrEncryptedPassword.length; i++) {
                if (arrEncryptedPassword[i] == oldChar && !(listOfUsedChar.contains(i))){
                    listOfUsedChar.add(i);
                }
            }
            System.out.println(encryptedPassword.replace(oldChar, newChar));
        }
        return encryptedPassword;

    }
    // Метод для получения мапы, в которой собрана статистика по вхождению символов в тексте
    private static Map<Character, Integer> getStatsFromText(String text){

        HashMap<Character, Integer> map = new HashMap<>();

        for(int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if(map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        // Создание списка пар ключ-значение
        List<Map.Entry<Character, Integer>> list = new ArrayList<>(map.entrySet());

        // Сортировка списка в порядке убывания значений_
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        // Создание отсортированной мапы
        Map<Character, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Character, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;

    }
}
