package db;

import models.Match;
import models.Player;
import models.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatchModelStorageTest {
    TeamModelStorage teamStorage;
    PlayerModelStorage playerStorage;
    MatchModelStorage matchStorage;

    @BeforeEach
    void setUp() throws IOException {
        String testDbFilePath = String.join(File.separator, "src", "test", "resources", "db.txt");
        DBStore dbStore = new DBStore(new File(testDbFilePath).getAbsolutePath());

        playerStorage = new PlayerModelStorage(dbStore);
        teamStorage = new TeamModelStorage(dbStore);
        matchStorage = new MatchModelStorage(dbStore);
    }

    @Test
    void parseModel() {
        Team team1 = new Team(1, "St. John Fisher College");
        Team team2 = new Team(2, "University College of Trollhättan/Uddevalla");

        Player player1 = new Player(1, "Araldo", "Lackeye", team1);
        Player player2 = new Player(2, "Armstrong", "Coare", team1);
        Player player3 = new Player(3, "Peter", "Billing", team2);
        Player player4 = new Player(4, "Hanni", "Fairrie", team2);
        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date;
        try {
            date = formatter.parse("12.05.2017");
        } catch (ParseException e) {
            date = new Date();
        }
        Match testMatch = new Match(1, date, "Teklist", "6:5", team1, team2, playerList);

        Match parsedMatch = (Match) matchStorage.parseModel("match\t1\t12.05.2017\tTeklist\t6:5\t1\t2\t1 2 3 4");

        assertEquals(parsedMatch, testMatch);

    }

    @Test
    void getDbRepresentation() {
        Team team1 = new Team(1, "St. John Fisher College");
        Team team2 = new Team(2, "University College of Trollhättan/Uddevalla");

        Player player1 = new Player(1, "Araldo", "Lackeye", team1);
        Player player2 = new Player(2, "Armstrong", "Coare", team1);
        Player player3 = new Player(3, "Peter", "Billing", team2);
        Player player4 = new Player(4, "Hanni", "Fairrie", team2);
        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date;
        try {
            date = formatter.parse("12.05.2017");
        } catch (ParseException e) {
            date = new Date();
        }
        Match testMatch = new Match(1, date, "Teklist", "6:5", team1, team2, playerList);

        String matchString = "match\t1\t12.05.2017\tTeklist\t6:5\t1\t2\t1 2 3 4";

        String resresentation = matchStorage.getDbRepresentation(testMatch);

        assertEquals(matchString, resresentation);
    }
}