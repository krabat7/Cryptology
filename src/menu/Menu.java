package src.menu;

import src.operations.data.CryptoKey;
import src.operations.decoding.withoutKey.BruteForce;
import src.operations.decoding.withKey.Decoding;
import src.operations.decoding.withoutKey.StatisticalAnalysis;
import src.operations.encoding.Encoding;

import java.util.Scanner;

/**
 * Класс, реализующий меню для взаимодействия с пользователем через консоль
 */
public class Menu {
    static Scanner scanner;
    static boolean shouldExit = false;

    /**
     * Метод, который реализует осоновную логику взаимодействия с пользователем через консоль
     */
    public void runMenu() {
        scanner = new Scanner(System.in);
        while (!shouldExit) {
            String originalPassword;
            String encryptedPassword;

            System.out.println("Выберите режим:");
            System.out.println("1. Шифрование / расшифровка");
            System.out.println("2. Криптоанализ");
            System.out.print("Введите номер действия: ");

            int action = takeInt();
            Encoding encoding = new Encoding();

            switch (action) {
                case 1 -> {
                    System.out.println("Выбрано: Шифрование / расшифровка\n");
                    System.out.println("Выберите режим:");
                    System.out.println("1. Шифрование");
                    System.out.println("2. Расшифровка");
                    System.out.print("Введите номер режима: ");
                    int subActionA = takeInt();
                    switch (subActionA) {
                        case 1 -> {
                            // Действия для шифврования
                            originalPassword = encoding.takePassword();
                            encryptedPassword = encoding.getEncryptedKey(originalPassword); // Зашифрованный пароль
                            encoding.createFileWithEncryptedPassword(encoding.FILE_NAME, encryptedPassword);
                            continueChoosing();
                        }
                        case 2 -> {
                            // Действия расшифровки
                            Decoding decoding = new Decoding();
                            encryptedPassword = encoding.takePassword(); // Извлекаем зашифрованный пароль из файла
                            originalPassword = decoding.getOriginalKey(encryptedPassword, CryptoKey.getCryptoKey()); // Расшифровка зашифрованного пароля
                            encoding.createFileWithEncryptedPassword(Decoding.FILE_NAME, originalPassword);
                            continueChoosing();
                        }
                        default -> {
                            System.out.println("Неверный номер режима!");
                            continueChoosing();
                        }
                    }
                }
                case 2 -> {
                    System.out.println("Выбрано: Криптоанализ\n");
                    System.out.println("Выберите режим:");
                    System.out.println("1. Метод brute force");
                    System.out.println("2. Метод статистического анализа");
                    System.out.print("Введите номер режима: ");
                    int subActionB = takeInt();
                    switch (subActionB) {
                        case 1 -> {
                            // Действия для криптоанализа методом brute force
                            BruteForce bruteForce = new BruteForce();
                            encryptedPassword = encoding.takePassword();
                            originalPassword = bruteForce.getOriginalKeyForBruteForce(encryptedPassword);
                            encoding.createFileWithEncryptedPassword(Decoding.FILE_NAME, originalPassword);
                            continueChoosing();
                        }
                        case 2 -> {
                            // Действия для криптоанализа методом статистического анализа
                            StatisticalAnalysis statisticalAnalysis = new StatisticalAnalysis();
                            System.out.println("Нужно ввести файл с зашифрованным паролем.");
                            encryptedPassword = encoding.takePassword();
                            System.out.println("Путь с файлом введен. Теперь введите путь дополнительного файла.");
                            String additionalText = encoding.takePassword();
                            originalPassword = statisticalAnalysis.getOriginalPasswordFromStat(encryptedPassword, additionalText);
                            encoding.createFileWithEncryptedPassword(Decoding.FILE_NAME, " Возможный пароль - " + originalPassword);
                            continueChoosing();
                        }
                        default -> {
                            System.out.println("Неверный номер режима!");
                            continueChoosing();
                        }
                    }
                }
                default -> {
                    System.out.println("Неверный номер режима!");
                    continueChoosing();
                }
            }
        }
    }
    /**
     * Метод, который предлагает пользователю выбор между закрытием программы или ее продолжением
     */
    private static void continueChoosing(){
        try{
            Thread.sleep(2000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("\nПродолжить работу в программе?");
        System.out.println("Выберите режим:");
        System.out.println("1. Продолжить");
        System.out.println("2. Закрыть программу");
        System.out.print("Введите номер режима: ");
        int answer = takeInt();
        switch (answer){
            case 1:
                break;
            case 2:
                shouldExit = true;
                break;
            default:
                System.out.println("Неверный номер режима!");
                continueChoosing();
        }
    }

    /**
     * Метод, который выполняет проверку на введенное пользователем число
     * @return Число с консоли
     */
    private static int takeInt(){
        int number;
        while (true) {
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                break;
            } else {
                System.out.println("Ошибка! Некорректный ввод. Попробуйте еще раз:");
                scanner.next();
            }
        }
        return number;
    }
}
