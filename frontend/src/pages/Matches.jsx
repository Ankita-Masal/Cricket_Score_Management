import { useEffect, useState } from "react";
import API from "../services/api";

function Matches({ onOpenMatch }) {
  const [matches, setMatches] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    API.get("/matches")
      .then((response) => setMatches(response.data))
      .catch(() => setError("Unable to load matches."));
  }, []);

  return (
    <main className="page-container">
      <p className="eyebrow">MATCH MANAGEMENT</p>
      <h1>Matches</h1>
      <p className="muted">Matches registered in your database.</p>

      {error && <p className="inline-error">{error}</p>}

      <div className="panel list-panel">
        {matches.length === 0 && !error ? (
          <p className="muted">No matches found.</p>
        ) : (
          matches.map((match) => (
            <div className="list-row" key={match.id}>
              <div>
                <strong>
                  {match.team1?.name || "Team 1"} vs{" "}
                  {match.team2?.name || "Team 2"}
                </strong>
                <p className="muted">
                  {match.matchType} · {match.venue}
                </p>
              </div>

              <span className="pill">{match.status}</span>

              <button
                className="primary-button"
                onClick={() => onOpenMatch(match.id)}
              >
                View score
              </button>
            </div>
          ))
        )}
      </div>
    </main>
  );
}

export default Matches;