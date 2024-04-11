package org.ibs.steps;

import io.cucumber.java.ru.*;
import org.ibs.pages.FoodPage;
import org.junit.jupiter.api.Assertions;

public class AddingFoodSteps {
    private FoodPage foodPage = FoodPage.getFoodPage();

    @Дано("пользователь открывает страницу добавления продуктов")
    public void пользовательОткрываетСтраницуДобавленияПродуктов() {
        // Реализация открытия страницы добавления продуктов
    }

    @Тогда("продукт {string} должен быть успешно добавлен")
    public void продуктДолженБытьУспешноДобавлен(String название) {
        Assertions.assertTrue(foodPage.containsProduct(название), "Не удалось добавить продукт: " + название);
    }

    @Когда("пользователь очищает данные")
    public void пользовательОчищаетДанные() {
        foodPage.clearData();
    }

    @Тогда("список продуктов должен быть пустым")
    public void списокПродуктовДолженБытьПустым() {
        Assertions.assertTrue(foodPage.checkTable(), "Не удалось очистить данные");
    }

    @Когда("пользователь добавляет продукт {string} с типом {string} и статусом {string}")
    public void пользовательДобавляетПродуктСТипомИСтатусом(String название, String тип, String статус) {
        boolean status = Boolean.parseBoolean(статус);
        foodPage.addProducts(название, тип, status);
    }
}
