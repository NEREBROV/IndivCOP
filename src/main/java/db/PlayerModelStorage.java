package db;

import models.Model;
import models.Player;
import models.Team;

public class PlayerModelStorage extends ModelStorage {

    public PlayerModelStorage(DBStore dbStore) {
        super(dbStore);
    }

    @Override
    protected Model parseModel(String string) {
        String[] data = string.split("\t");
        int id = Integer.parseInt(data[1]);
        String firstName = data[2];
        String lastName = data[3];
        int team_id = Integer.parseInt(data[4]);
        TeamModelStorage teamStorage = new TeamModelStorage(dbStore);
        Team team = (Team) teamStorage.getById(team_id);

        return new Player(id, firstName, lastName, team);
    }

    @Override
    protected String getDbFilteringKey() {
        return "player";
    }

    @Override
    protected String getDbRepresentation(Model model) {
        Player player = (Player) model;
        return String.join("\t",
                getDbFilteringKey(),
                player.getId().toString(),
                player.getFirstName(),
                player.getLastName(),
                player.getTeam().getId().toString()
        );
    }
}
