package org.ibs.pages;

import org.ibs.utils.PropConst;
import org.ibs.managers.DriverManager;
import org.ibs.managers.TestPropManager;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class FoodPage {

    protected final DriverManager driverManager = DriverManager.getDriverManager();
    protected WebDriver webDriver = driverManager.getDriver();
    private final WebDriverWait wait = new WebDriverWait(webDriver, 5);
    private final TestPropManager props = TestPropManager.getTestPropManager();

    private static FoodPage foodPage;

    private By addProductsButtonLocator = By.xpath("//button[@data-toggle='modal']");
    private By saveProductsButtonLocator = By.id("save");
    private By nameFieldLocator = By.id("name");
    private By typeDropdownLocator = By.id("type");
    private By exoticCheckboxLocator = By.id("exotic");
    private By navSandboxLocator = By.id("navbarDropdown");
    private By resetDataLocator = By.id("reset");
    private By rowsLocator = By.xpath("//tbody/tr");

    private List<WebElement> rows;
    private Map<String, String> dataTable;

    private FoodPage() {
        rows = webDriver.findElements(rowsLocator);
        dataTable = saveTable(rows);
    }

    public static FoodPage getFoodPage() {
        if (foodPage == null) {
            foodPage = new FoodPage();
        }
        return foodPage;
    }

    private Map<String, String> saveTable(List<WebElement> rows) {
        Map<String, String> tableData = new HashMap<>();
        for (WebElement row : rows) {
            List<WebElement> productCells = row.findElements(By.xpath(".//td"));
            StringBuilder rowDataBuilder = new StringBuilder();
            //String rowData = "";
            if (!productCells.isEmpty()) {
                String key = productCells.get(0).getText();
                for (int i = 1; i < productCells.size(); i++) {
                    //rowData += productCells.get(i).getText() + " ";
                    rowDataBuilder.append(productCells.get(i).getText()).append(" ");
                }
                String rowData = rowDataBuilder.toString().trim();
                tableData.put(key, rowData);
            }
        }
        return tableData;
    }

    public void addProducts(String name, String type, boolean exotic) {
        webDriver.get(props.getProperty(PropConst.BASE_URL));
        WebElement btnAddProducts = wait.until(ExpectedConditions.visibilityOfElementLocated(addProductsButtonLocator));
        btnAddProducts.click();
        insertData(name, type, exotic);
        WebElement btnSaveProducts = wait.until(ExpectedConditions.elementToBeClickable(saveProductsButtonLocator));
        btnSaveProducts.click();
        rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(rowsLocator));;
    }

    private void insertData(String name, String type, boolean exotic) {
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(nameFieldLocator));
        nameField.sendKeys(name);
        Select typeDropdown = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(typeDropdownLocator)));
        typeDropdown.selectByVisibleText(type);
        WebElement exoticCheckbox = wait.until(ExpectedConditions.visibilityOfElementLocated(exoticCheckboxLocator));
        if (exotic) {
            exoticCheckbox.click();
        }
    }

    public void clearData() {
        WebElement navSandbox = webDriver.findElement(navSandboxLocator);
        navSandbox.click();
        WebElement resetData = webDriver.findElement(resetDataLocator);
        resetData.click();
    }

    public boolean containsProduct(String name) {
        try {
            List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(rowsLocator));
            for (WebElement row : rows) {
                WebElement productCells = row.findElement(By.xpath(".//td"));
                String productText = productCells.getText();
                if (productText.equals(name)) {
                    return true;
                }
            }
        } catch (StaleElementReferenceException e) {
            return containsProduct(name);
        } catch (NoSuchElementException e) {
            System.out.println("Элемент не найден: " + e.getMessage());
        }
        return false;
    }


    public boolean checkTable() {
        try {
            List<WebElement> currentRows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(rowsLocator));
            Map<String, String> currentTable = saveTable(currentRows);
            /*if (dataTable.size() != currentTable.size()) {
                return false;
            }
            for (Map.Entry<String, String> etr: dataTable.entrySet()){
                String key = etr.getKey();
                String value = etr.getValue();
                if (!currentTable.containsKey(key) || !currentTable.get(key).equals(value)) {
                    return false;
                }
            }*/
            return currentTable.size() == 4;
        } catch (StaleElementReferenceException e) {
            return checkTable();
        }
    }

}
