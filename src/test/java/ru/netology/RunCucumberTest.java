package ru.netology;

import com.codeborne.selenide.Configuration;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "summary"},
        features = {"src/test/resources/features"},
        glue = {"ru.netology.steps"}
)
public class RunCucumberTest {
    @BeforeAll
    public static void setUp(){
        Configuration.browser = "chrome";
        Configuration.baseUrl = "http://localhost:9999";
        Configuration.reportsFolder = "build/reports/tests/test/screenshots";
    }
}
