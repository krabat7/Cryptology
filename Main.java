import java.util.Scanner;

public class Main {
    /**
     * Класс, реализующий взаимодействие пользователя с программой и логику классов.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String originalPassword;
            String encryptedPassword;

            System.out.println("Выберите режим:");
            System.out.println("1. Шифрование / расшифровка");
            System.out.println("2. Криптоанализ");
            System.out.print("Введите номер действия: ");

            int action = scanner.nextInt();
            Encoding encoding = new Encoding();

            switch (action) {
                case 1:
                    System.out.println("Выбрано: Шифрование / расшифровка");
                    System.out.println("Выберите режим:");
                    System.out.println("1. Шифрование");
                    System.out.println("2. Расшифровка");
                    System.out.print("Введите номер режима: ");

                    int subActionA = scanner.nextInt();

                    switch (subActionA) {
                        case 1:
                            System.out.println("Результат шифрования:");
                            // Действия для шифврования
                            originalPassword = encoding.takePassword();
                            encryptedPassword = encoding.getEncryptedKey(originalPassword); // Зашифрованный пароль
                            encoding.createFileWithEncryptedPassword(encoding.FILE_NAME, encryptedPassword);
                            break;
                        case 2:
                            System.out.println("Результат расшифровки");
                            // Действия расшифровки
                            Decoding decoding = new Decoding();
                            encryptedPassword = encoding.takePassword(); // Извлекаем зашифрованный пароль из файла
                            originalPassword = decoding.getOriginalKey(encryptedPassword, CryptoKey.getCryptoKey()); // Расшифровка зашифрованного пароля
                            encoding.createFileWithEncryptedPassword(Decoding.FILE_NAME, originalPassword);
                            break;
                        default:
                            System.out.println("Неверный номер режима!");
                    }

                    break;
                case 2:
                    System.out.println("Выбрано: Криптоанализ");
                    System.out.println("Выберите режим:");
                    System.out.println("1. Метод brute force");
                    System.out.println("2. Метод статистического анализа");
                    System.out.print("Введите номер режима: ");

                    int subActionB = scanner.nextInt();

                    switch (subActionB) {
                        case 1:
                            System.out.println("Результат криптоанализа методом brute force");
                            // Действия для криптоанализа методом brute force
                            BruteForce bruteForce = new BruteForce();
                            encryptedPassword = encoding.takePassword();
                            originalPassword = bruteForce.getOriginalKeyForBruteForce(encryptedPassword);
                            encoding.createFileWithEncryptedPassword(Decoding.FILE_NAME, originalPassword);
                            break;
                        case 2:
                            System.out.println("Результат криптоанализа методом статистического анализа");
                            // Действия для криптоанализа методом статистического анализа
                            StatisticalAnalysis stat = new StatisticalAnalysis();
                            encryptedPassword = encoding.takePassword();
                            String additionalText = encoding.takePassword();
                            originalPassword = stat.getOriginalPasswordFromStat(encryptedPassword, additionalText);
                            encoding.createFileWithEncryptedPassword(Decoding.FILE_NAME, originalPassword);
                            break;
                        default:
                            System.out.println("Неверный номер режима!");
                    }

                    break;
                default:
                    System.out.println("Неверный номер режима!");
            }
        }
    }
}
