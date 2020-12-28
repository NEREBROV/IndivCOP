package models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Match extends Model {

    private final Date date;
    private final String location;
    private final String result;
    private final Team team_1;
    private final Team team_2;
    private final List<Player> players;

    public Match(int id, Date date, String location, String result, Team team_1, Team team_2, List<Player> players) {
        super(id);
        this.date = date;
        this.location = location;
        this.result = result;
        this.team_1 = team_1;
        this.team_2 = team_2;
        this.players = players;
    }

    @Override
    public String toString() {
        String pattern = "dd.MM.yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        return "Match " + String.format("by %s: %s vs. %s", dateFormat.format(date), team_1, team_2);
    }

    public Date getDate() {
        return date;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public String getLocation() {
        return location;
    }

    public Team getTeam_1() {
        return team_1;
    }

    public Team getTeam_2() {
        return team_2;
    }

    public String getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Match)) return false;
        if (!super.equals(o)) return false;
        Match match = (Match) o;
        return getDate().equals(match.getDate()) && getLocation().equals(match.getLocation()) && Objects.equals(getResult(), match.getResult()) && getTeam_1().equals(match.getTeam_1()) && getTeam_2().equals(match.getTeam_2()) && Objects.equals(getPlayers(), match.getPlayers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDate(), getLocation(), getResult(), getTeam_1(), getTeam_2(), getPlayers());
    }
}

