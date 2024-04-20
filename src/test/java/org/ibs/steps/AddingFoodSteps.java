package org.ibs.steps;

import io.cucumber.java.ru.*;
import io.qameta.allure.Step;
import org.ibs.pages.FoodPage;
import org.junit.jupiter.api.Assertions;

public class AddingFoodSteps {
    private FoodPage foodPage;

    @Step("Пользователь открывает страницу добавления продуктов")
    @Дано("пользователь открывает страницу добавления продуктов")
    public void пользовательОткрываетСтраницуДобавленияПродуктов() {
        foodPage = FoodPage.getFoodPage();
    }

    @Step("Проверяем, что продукт {название} успешно добавлен")
    @Тогда("продукт {string} должен быть успешно добавлен")
    public void продуктДолженБытьУспешноДобавлен(String name) {
        Assertions.assertTrue(foodPage.containsProduct(name), "Не удалось добавить продукт: " + name);
    }

    @Step("Очищаем данные")
    @Когда("пользователь очищает данные")
    public void пользовательОчищаетДанные() {
        foodPage.clearData();
    }

    @Step("Проверяем, что список продуктов пустой")
    @Тогда("список продуктов должен быть пустым")
    public void списокПродуктовДолженБытьПустым() {
        Assertions.assertTrue(foodPage.checkTable(), "Не удалось очистить данные");
    }

    @Step("Добавляем продукт: {название} с типом: {тип} и статусом: {статус}")
    @Когда("пользователь добавляет продукт {string} с типом {string} и признаком {string}")
    public void пользовательДобавляетПродуктСТипомИПризнаком(String name, String type, String exotic) {
        boolean status;
        switch (exotic.toLowerCase()) {
            case "экзотический":
                status = true;
                break;
            case "неэкзотический":
                status = false;
                break;
            default:
                Assertions.assertTrue(false, "Не существует такого признака к продукту");
                return;
        }
        foodPage.addProducts(name, type, status);
    }
}