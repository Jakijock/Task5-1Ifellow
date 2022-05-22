package stepdefs;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.RestAssured;
import org.apache.hc.core5.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Assert;
import regresIn.Models.Registr;
import regresIn.Models.Successful;
import regresIn.Models.UserDTO;
import regresIn.Specifications.Specifications;

import java.util.List;

import static io.restassured.RestAssured.given;
import static regresIn.Specifications.Specifications.requestSpecification;

public class TestStepdefs {

    private final static String URL = "https://reqres.in";

    @Когда("передаем тело запроса")
    public void checkRequestBody(List<String> arg) {
        Specifications.installSpec(requestSpecification(URL), Specifications.responseSpecification200(201));
        Registr user = new Registr(arg.get(0), arg.get(1));
        Successful successful = given()
                .body(user)
                .when()
                .post("api/users")
                .then().log().all()
                .extract().as(Successful.class);
        Assert.assertEquals(arg.get(0), successful.getName());
        Assert.assertEquals(arg.get(1), successful.getJob());
    }

    @Тогда("проверяем, что ответ имеет валидные значения")
    public void checkingValidResponseValues() {
        Specifications.installSpec(requestSpecification(URL), Specifications.responseSpecification200(201));
        RestAssured.given()
                .spec(requestSpecification(URL))
                .body(new UserDTO("Tomato", "Eat market"))
                .post("/api/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .assertThat()
                .body("name", Matchers.is("Tomato"))
                .body("job", Matchers.is("Eat market"));
    }
}

