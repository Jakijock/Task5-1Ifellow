package regresIn.ApiTests;
import io.restassured.RestAssured;
import org.apache.hc.core5.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import regresIn.Models.UserDTO;
import regresIn.Models.Registr;
import regresIn.Specifications.Specifications;
import regresIn.Models.Successful;
import java.util.List;
import static io.restassured.RestAssured.*;
import static regresIn.Specifications.Specifications.requestSpecification;


public class RegresInTests {
    private final static String URL = "https://reqres.in";

    @Test
    public void checkTomato() {
        Specifications.installSpec(requestSpecification(URL), Specifications.responseSpecification200(200));
        List<UserDTO> users = given()
                .when()
                .get("api/users")
                .then().log().all()
                .extract().body().jsonPath().getList("name", UserDTO.class);
        users.forEach(x -> Assert.assertTrue(x.getName().contains("Tomato")));
    }

    @Test
    public void createUserAndCheckAnswer() {
        Specifications.installSpec(requestSpecification(URL), Specifications.responseSpecification200(201));
        String name = "Tomato";
        String job = "Eat market";
        Registr user = new Registr(name, job);
        Successful successful = given()
                .body(user)
                .when()
                .post("api/users")
                .then().log().all()
                .extract().as(Successful.class);
        Assert.assertEquals(name, successful.getName());
        Assert.assertEquals(job, successful.getJob());
    }
    @Test
    public void getRequestCheckResponseJsonBody() {
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












