package com.eyup.stepDefinitions.api;

import com.eyup.pages.PetPage;
import com.eyup.utils.ConfigurationReader;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.*;

public class PetSteps {

    private static final Logger logger = LogManager.getLogger(PetSteps.class);
    private PetPage petPage = new PetPage(ConfigurationReader.get("pet_baseurl"));
    private Response response;
    private Long petId;
    private String petName;
    private List<String> photoUrls;

    // Positive Scenarios
    @Given("I have the pet data with name {string} and photoUrls")
    public void i_have_the_pet_data_with_name_and_photo_urls(String petName, List<String> photoUrls) {
        this.petName = petName;
        this.photoUrls = photoUrls;
        logger.info("Pet data: name: {}, photoUrls: {}", petName, photoUrls);
    }
    @When("I send a POST request to add the pet")
    public void i_send_a_post_request_to_add_the_pet() {
        logger.info("Sending POST request to add the pet");
        response = petPage.addPet(petName, photoUrls);
        response.prettyPrint();
        petId = response.jsonPath().get("id");
        logger.info("Pet added with ID: {}", petId);
    }
    @Then("the response status should be {int}")
    public void the_response_status_should_be(Integer statusCode) {
        response.prettyPrint();
        response.then().statusCode(statusCode);
        logger.info("Response status code: {}", statusCode);

    }
    @Then("the pet's name should be {string}")
    public void the_pet_s_name_should_be(String name) {
        response.prettyPrint();
        response.then().body("name", equalTo(name));
        logger.info("Verified pet's name: {}", name);
    }
    @Then("the pet's photoUrls should contain")
    public void the_pet_s_photo_urls_should_contain(List<String> photoUrls) {
        response.prettyPrint();
        response.then().body("photoUrls", hasItems(photoUrls.get(0), photoUrls.get(1), photoUrls.get(2)));
        logger.info("Verified pet's photoUrls: {}", photoUrls);
    }
    @Then("response should match the schema")
    public void response_should_match_the_schema() {
        response.prettyPrint();
        response.then().body(matchesJsonSchemaInClasspath("data/pet_json_schema.json"));
        logger.info("Response matches the schema");
    }


    @Given("the pet exists with id {string}")
    public void the_pet_exists_with_id(String id) {
        if (id != null && !id.isEmpty()) {
            petId = Long.parseLong(id);
            logger.info("Pet exists with ID: {}", petId);
        } else {
            logger.error("Invalid pet ID: {}", id);
            throw new IllegalArgumentException("Pet ID cannot be null or empty");
        }
    }
    @When("I send a GET request to get the pet by ID")
    public void i_send_a_get_request_to_get_the_pet_by_id()  {
        response = petPage.getPetById(petId);
        response.prettyPrint();
    }

    @When("I send a PUT request to update the pet's name to {string}")
    public void i_send_a_put_request_to_update_the_pet_s_name_to(String newName) {
        logger.info("Sending PUT request to update the pet's name to: {}", newName);
        response = petPage.updatePet(petId,newName);
        logger.info("Pet name updated to: {}", newName);
    }


    @When("I send a DELETE request to delete the pet")
    public void i_send_a_delete_request_to_delete_the_pet() {
        logger.info("Sending DELETE request to delete the pet with ID: {}", petId);
        response = petPage.deletePet(petId);
    }
    @Then("the pet should no longer exist")
    public void the_pet_should_no_longer_exist() {
        logger.info("Verifying the pet no longer exists with ID: {}", petId);
        response = petPage.getPetById(petId);
        response.then().statusCode(404);
        response.then().body("message", containsString("Pet not found"));
        response.prettyPrint();
    }
    @When("I send a POST request to add the pet without name")
    public void i_send_a_post_request_to_add_the_pet_without_name() {
        logger.info("Sending POST request to add the pet without name");
        response = petPage.addPetWithoutName(photoUrls);
        response.prettyPrint();
    }


    @Then("the error message should contain {string}")
    public void the_error_message_should_contain(String errorMessage) {
        response.prettyPrint();
        response.then().body("message", containsString(errorMessage));
        logger.info("Error message contains: {}", errorMessage);
    }

    @When("I send a PUT request to update the pet's name to {string} without photoUrls")
    public void iSendAPUTRequestToUpdateThePetSNameToWithoutPhotoUrls(String newName) {
        logger.info("Sending PUT request to update the pet's name to: {} without photoUrls", newName);
        response = petPage.updatePetWithoutPhotoUrls(petId, newName);
        response.prettyPrint();
    }
}
