package db;

import models.Player;
import models.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerModelStorageTest {
    PlayerModelStorage playerStorage;
    TeamModelStorage teamModelStorage;

    @BeforeEach
    void setStorage() throws IOException {
        String testDbFilePath = String.join(File.separator, "src", "test", "resources", "db.txt");
        DBStore dbStore = new DBStore(new File(testDbFilePath).getAbsolutePath());
        playerStorage = new PlayerModelStorage(dbStore);
        teamModelStorage = new TeamModelStorage(dbStore);
    }

    @Test
    void parseModel() {
        Team team1 = new Team(1, "St. John Fisher College");
        Player testPlayer = new Player(1, "Araldo", "Lackeye", team1);

        String playerString = "player\t1\tAraldo\tLackeye\t1";
        Player parcedPlayer = (Player) playerStorage.parseModel(playerString);

        assertEquals(parcedPlayer, testPlayer);
    }

    @Test
    void getDbRepresentation() {
        Team team1 = new Team(1, "St. John Fisher College");
        Player player = new Player(1, "Araldo", "Lackeye", team1);

        String playerString = "player\t1\tAraldo\tLackeye\t1";

        String testString = playerStorage.getDbRepresentation(player);

        assertEquals(playerString, testString);
    }
}