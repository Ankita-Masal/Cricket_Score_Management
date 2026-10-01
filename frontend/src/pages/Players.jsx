import { useEffect, useState } from "react";
import API from "../services/api";

function Players() {
  const [players, setPlayers] = useState([]);
  const [teams, setTeams] = useState([]);
  const [name, setName] = useState("");
  const [role, setRole] = useState("BATSMAN");
  const [jerseyNumber, setJerseyNumber] = useState("");
  const [teamId, setTeamId] = useState("");
  const [battingStyle, setBattingStyle] = useState("RIGHT_HAND");
  const [bowlingStyle, setBowlingStyle] = useState("");
  const [message, setMessage] = useState("");

  async function loadData() {
    try {
      const [playerResponse, teamResponse] = await Promise.all([
        API.get("/players"),
        API.get("/teams"),
      ]);

      setPlayers(playerResponse.data);
      setTeams(teamResponse.data);
    } catch {
      setMessage("Unable to load players or teams.");
    }
  }

  useEffect(() => {
    loadData();
  }, []);

  async function addPlayer(event) {
    event.preventDefault();
    setMessage("");

    try {
      await API.post("/players", {
        name,
        role,
        jerseyNumber: jerseyNumber ? Number(jerseyNumber) : null,
        battingStyle: battingStyle || null,
        bowlingStyle: bowlingStyle || null,
        team: { id: Number(teamId) },
      });

      setName("");
      setJerseyNumber("");
      setBowlingStyle("");
      setMessage("Player added successfully.");
      await loadData();
    } catch {
      setMessage("Unable to add player. Check the selected team and entered data.");
    }
  }

  return (
    <main className="page-container">
      <p className="eyebrow">PLAYER MANAGEMENT</p>
      <h1>Players</h1>
      <p className="muted">Register players and assign them to teams.</p>

      <form className="panel form-panel" onSubmit={addPlayer}>
        <h3>Add a player</h3>

        <div className="form-grid">
          <label>
            Player name
            <input
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
              placeholder="Player name"
            />
          </label>

          <label>
            Team
            <select
              value={teamId}
              onChange={(e) => setTeamId(e.target.value)}
              required
            >
              <option value="">Select team</option>
              {teams.map((team) => (
                <option key={team.id} value={team.id}>
                  {team.name}
                </option>
              ))}
            </select>
          </label>

          <label>
            Player role
            <select
              value={role}
              onChange={(e) => setRole(e.target.value)}
            >
              <option value="BATSMAN">Batsman</option>
              <option value="BOWLER">Bowler</option>
              <option value="ALL_ROUNDER">All-rounder</option>
              <option value="WICKET_KEEPER">Wicket keeper</option>
            </select>
          </label>

          <label>
            Jersey number
            <input
              type="number"
              min="1"
              value={jerseyNumber}
              onChange={(e) => setJerseyNumber(e.target.value)}
            />
          </label>

          <label>
            Batting style
            <select
              value={battingStyle}
              onChange={(e) => setBattingStyle(e.target.value)}
            >
              <option value="RIGHT_HAND">Right hand</option>
              <option value="LEFT_HAND">Left hand</option>
            </select>
          </label>

          <label>
            Bowling style
            <input
              value={bowlingStyle}
              onChange={(e) => setBowlingStyle(e.target.value)}
              placeholder="e.g. RIGHT_ARM_FAST"
            />
          </label>
        </div>

        <button className="primary-button" type="submit">
          Add player
        </button>

        {message && <p className="form-message">{message}</p>}
      </form>

      <div className="panel list-panel">
        {players.map((player) => (
          <div className="list-row" key={player.id}>
            <div className="player-avatar">
              {player.name?.charAt(0)?.toUpperCase()}
            </div>

            <div className="list-main">
              <strong>{player.name}</strong>
              <p className="muted">
                {player.team?.name || "Team"} · {player.role?.replace("_", " ")}
              </p>
            </div>

            <span className="pill">
              #{player.jerseyNumber ?? "—"}
            </span>
          </div>
        ))}
      </div>
    </main>
  );
}

export default Players;