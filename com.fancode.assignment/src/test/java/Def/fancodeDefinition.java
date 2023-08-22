package Def;

import Steps.fancodeSteps;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

public class fancodeDefinition {

    fancodeSteps FancodeSteps;
    List<String> Users;

    @When("I hit the API users")
    public void IHitTheApiUsers() throws JsonProcessingException {
        this.FancodeSteps = new fancodeSteps();
        FancodeSteps.getUsersAPI();
    }

    @Then("Fetch the Users belong to Fancode city")
    public void fetchTheUsersBelongToFancodeCity() {
        Users = FancodeSteps.getUsersBelongToFancodeCity();
    }

    @Then("Validate Users completed tasks percentage is greated than {int}%")
    public void validateUsersCompletedTasksPercentageIsGreatedThan(int percentage) throws JsonProcessingException {
        FancodeSteps.getTodosAPI();
        FancodeSteps.checkCompletedTaskPercentage(Users);
    }
}
