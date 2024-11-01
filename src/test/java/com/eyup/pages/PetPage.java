package com.eyup.pages;

import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PetPage {

    private String baseUrl;

    public PetPage(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Response addPet(String petName, List<String> photoUrls) {
        return given().baseUri(baseUrl)
                .contentType("application/json")
                .body(buildBodyByMap(petName, photoUrls))
                .when()
                .post("/pet");
    }

    public Response addPetWithoutName(List<String> photoUrls) {
        String[] photoUrlsArray = photoUrls.stream().toArray(String[]::new);
        Map<String, Object> body = Map.of(
                "photoUrls", photoUrlsArray
        );

        return given().baseUri(baseUrl)
                .contentType("application/json")
                .body(body)
                .when()
                .post("/pet");
    }

    public Response getPetById(Long petId) {
        return given().baseUri(baseUrl)
                .when()
                .get("/pet/" + petId);
    }

    public Response updatePet(Long petId, String petName) {
        Response response = getPetById(petId);
        String body = response.getBody().asString();
        String bodyUpdated = body.replace("nametobeupdated", petName);
        return given().baseUri(baseUrl)
                .contentType("application/json")
                .body(bodyUpdated)
                .when()
                .put("/pet");
    }

    public Response updatePetWithoutPhotoUrls(Long petId, String petName) {
        Response response = getPetById(petId);
        Map<String, Object> body = Map.of(
                "name", response.getBody().jsonPath().get("name")
        );

        return given().baseUri(baseUrl)
                .contentType("application/json")
                .body(body)
                .when()
                .put("/pet");
    }

    public Response deletePet(Long petId) {
        return given().baseUri(baseUrl)
                .when()
                .delete("/pet/" + petId);
    }


    private static StringBuilder buildBody(String petName, List<String> photoUrls) {
        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("{\n")
                .append("  \"name\": \"").append(petName).append("\",\n")
                .append("  \"photoUrls\": [\n");

        for (int i = 0; i < photoUrls.size(); i++) {
            bodyBuilder.append("    \"").append(photoUrls.get(i)).append("\"");
            if (i < photoUrls.size() - 1) {
                bodyBuilder.append(",");
            }
            bodyBuilder.append("\n");
        }

        bodyBuilder.append("  ]\n")
                .append("}");
        return bodyBuilder;
    }
    private static Map buildBodyByMap(String petName, List<String> photoUrls) {
        String[] photoUrlsArray = photoUrls.stream().toArray(String[]::new);
        Map<String, Object> body = Map.of(
                "name", petName,
                "photoUrls", photoUrlsArray
        );

        return body;
    }

}
