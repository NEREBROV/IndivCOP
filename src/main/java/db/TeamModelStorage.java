package db;

import models.Model;
import models.Team;

public class TeamModelStorage extends ModelStorage {
    public TeamModelStorage(DBStore dbStore) {
        super(dbStore);
    }

    @Override
    protected Model parseModel(String string) {
        String[] data = string.split("\t");
        int id = Integer.parseInt(data[1]);
        String title = data[2];

        return new Team(id, title);
    }

    @Override
    protected String getDbFilteringKey() {
        return "team";
    }

    @Override
    protected String getDbRepresentation(Model model) {
        Team team = (Team) model;
        return String.join("\t", getDbFilteringKey(), team.getId().toString(), team.getTitle());
    }
}
