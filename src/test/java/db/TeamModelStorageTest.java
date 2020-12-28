package db;

import models.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeamModelStorageTest {
    TeamModelStorage storage;

    @BeforeEach
    void setUp() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        String testDbFilePath = String.join(File.separator, "src", "test", "resources", "db.txt");
        storage = new TeamModelStorage(new DBStore(new File(testDbFilePath).getAbsolutePath()));
    }

    @Test
    void parseModel() {
        String team = "team\t10\tTechnical University of Czestochowa";
        Team parcedTeam = (Team) storage.parseModel(team);
        Team testTeam = new Team(10, "Technical University of Czestochowa");

        assertEquals(testTeam, parcedTeam);

    }

    @Test
    void getDbRepresentation() {
        String teamRepresentation = "team\t10\tTechnical University of Czestochowa";

        Team teamTest = new Team(10, "Technical University of Czestochowa");
        String teamTestRepresentation = storage.getDbRepresentation(teamTest);

        assertEquals(teamRepresentation, teamTestRepresentation);
    }
}