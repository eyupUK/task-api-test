package com.eyup.runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "json:target/cucumber.json",
                "html:target/cucumber-report.html",
                "me.jvt.cucumber.report.PrettyReports",
                "pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "rerun:target/rerun.txt"
        },
        features = "src/test/resources/features",
        glue = "com/eyup/stepDefinitions",
        dryRun = false
        //tags = "@petCRUD"
)
public class TestRunner {
}

