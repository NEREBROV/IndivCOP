package db;

import models.Match;
import models.Model;
import models.Player;
import models.Team;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MatchModelStorage extends ModelStorage {
    public MatchModelStorage(DBStore dbStore) {
        super(dbStore);
    }

    @Override
    protected Model parseModel(String string) {
        String[] data = string.split("\t");
        Integer id = Integer.parseInt(data[1]);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

        Date date = null;
        try {
            date = formatter.parse(data[2]);
        } catch (ParseException e) {
            date = new Date();
        }
        String location = data[3];
        String result = data[4];

        TeamModelStorage teamStorage = new TeamModelStorage(dbStore);
        Integer team1Id = Integer.parseInt(data[5]);
        Integer team2Id = Integer.parseInt(data[6]);
        Team team1 = (Team) teamStorage.getById(team1Id);
        Team team2 = (Team) teamStorage.getById(team2Id);

        List<Player> players = new ArrayList<>();
        PlayerModelStorage playerStorage = new PlayerModelStorage(dbStore);
        for (String playerId : data[7].split(" ")) {
            players.add((Player) playerStorage.getById(Integer.parseInt(playerId)));
        }

        return new Match(id, date, location, result, team1, team2, players);
    }

    @Override
    protected String getDbFilteringKey() {
        return "match";
    }

    @Override
    protected String getDbRepresentation(Model model) {
        Match match = (Match) model;

        StringBuilder players = new StringBuilder();
        for (Player player : match.getPlayers()) {
            players.append(player.getId().toString()).append(" ");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return String.join("\t",
                getDbFilteringKey(),
                match.getId().toString(),
                dateFormat.format(match.getDate()),
                match.getLocation(),
                match.getResult(),
                match.getTeam_1().getId().toString(),
                match.getTeam_2().getId().toString(),
                players.toString().trim()
        );
    }
}
