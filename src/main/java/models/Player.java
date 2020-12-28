package models;

import java.util.Objects;

public class Player extends Model {
    private final String firstName;
    private final String lastName;
    private final Team team;


    public Player(int id, String firstName, String lastName, Team team) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.team = team;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public String toString() {
        return "Player " + String.format("name: %s %s team: %s", getFirstName(), getLastName(), team);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        if (!super.equals(o)) return false;
        Player player = (Player) o;
        return getFirstName().equals(player.getFirstName()) && getLastName().equals(player.getLastName()) && getTeam().equals(player.getTeam());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFirstName(), getLastName(), getTeam());
    }
}
