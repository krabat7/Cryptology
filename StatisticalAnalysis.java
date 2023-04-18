import java.util.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, реализующий расшифровку методом статистического анализа
 */
public class StatisticalAnalysis {
    /**
     * Метод, который расшифровывает пароль путем статистического анализа
     * @param encryptedPassword Зашифрованный пароль
     * @param additionalText Дополнительный текст для статистики
     * @return encryptedPassword Зашифрованный пароль
     */
    public String getOriginalPasswordFromStat(String encryptedPassword, String additionalText){
        Map<Character, Integer> mapPassword = getStatsFromText(encryptedPassword); // Мапа со статисткой вхождений символа в пароль
        Map<Character, Integer> mapAdditionalText = getStatsFromText(additionalText); // Мапа со статисткой вхождений символа в дополнительный текст

        // Получаем итераторы для каждой мапы
        Iterator<Map.Entry<Character, Integer>> iterator1 = mapPassword.entrySet().iterator();
        Iterator<Map.Entry<Character, Integer>> iterator2 = mapAdditionalText.entrySet().iterator();
        ArrayList<Integer> listOfUsedChar = new ArrayList<>(); // Лист использованных индексов в пароле

        while (iterator1.hasNext()) {
            Map.Entry<Character, Integer> entry1 = iterator1.next();
            Map.Entry<Character, Integer> entry2 = iterator2.next();
            Character oldChar = entry1.getKey(); // Получение символа из зашифрованного пароля
            Character newChar = entry2.getKey(); // Получение символа из доплнительного текста
            encryptedPassword = encryptedPassword.replace(oldChar, newChar);
            char[] arrEncryptedPassword = encryptedPassword.toCharArray();
            for (int i = 0; i < arrEncryptedPassword.length; i++) {
                if (arrEncryptedPassword[i] == oldChar && !(listOfUsedChar.contains(i))){
                    listOfUsedChar.add(i);
                }
            }
        }
        return encryptedPassword;
    }

    /**
     * Метод, создающий мапу, в которой собрана статистика по вхождению символов в тексте по убыванию
     * @param text Строка
     * @return sortedMap Отсортированная по убыванию количества вхождений символа мапа
     */
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
