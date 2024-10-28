package com.eyup.pages;

import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class PetPage {

    private String baseUrl;

    public PetPage(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Response addPet(String petName, List<String> photoUrls) {
        StringBuilder bodyBuilder = buildBody(petName, photoUrls);

        return given().baseUri(baseUrl)
                .contentType("application/json")
                .body(bodyBuilder.toString())
                .when()
                .post("/pet");
    }

    public Response addPetWithoutName(List<String> photoUrls) {
        String name = "name";
        StringBuilder bodyBuilder = buildBody(name, photoUrls);
        bodyBuilder = new StringBuilder(bodyBuilder.toString().replace("name", ""));
        return given().baseUri(baseUrl)
                .contentType("application/json")
                .body(bodyBuilder.toString())
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
        String body = response.getBody().asString();
        String bodyUpdated = body.replace("photoUrls", "").replace("petnottobeupdated", petName);
        return given().baseUri(baseUrl)
                .contentType("application/json")
                .body(bodyUpdated)
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

}
