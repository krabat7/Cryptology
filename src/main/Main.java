package src.main;

import src.menu.Menu;

public class Main {
    /**
     * Метод, который реализует осоновную логику взаимодействия с пользователем через консоль
     */
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.runMenu();
    }
}
