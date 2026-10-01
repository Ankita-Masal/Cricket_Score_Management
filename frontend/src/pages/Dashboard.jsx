import { useCallback, useEffect, useState } from "react";
import API from "../services/api";
import ScoreCard from "../components/ScoreCard";
import BattingTable from "../components/BattingTable";
import BowlingTable from "../components/BowlingTable";
import RecentBalls from "../components/RecentBalls";

function Dashboard({ matchId = 1 }) {
  const [summary, setSummary] = useState(null);
  const [batting, setBatting] = useState(null);
  const [bowling, setBowling] = useState(null);
  const [deliveries, setDeliveries] = useState([]);
  const [error, setError] = useState("");

  const loadDashboard = useCallback(async () => {
    try {
      const summaryResponse = await API.get(`/match-summary/${matchId}`);
      const matchData = summaryResponse.data;
      setSummary(matchData);
      setError("");

      const currentInnings =
        matchData.innings?.find((item) => item.status === "IN_PROGRESS") ||
        matchData.innings?.[0];

      if (!currentInnings) return;

      const [deliveryResult, battingResult, bowlingResult] =
        await Promise.allSettled([
          API.get(`/deliveries/innings/${currentInnings.inningsId}`),
          API.get(`/statistics/batting/${currentInnings.inningsId}/1`),
          API.get(`/statistics/bowling/${currentInnings.inningsId}/2`),
        ]);

      if (deliveryResult.status === "fulfilled") {
        setDeliveries(deliveryResult.value.data);
      }

      if (battingResult.status === "fulfilled") {
        setBatting(battingResult.value.data);
      }

      if (bowlingResult.status === "fulfilled") {
        setBowling(bowlingResult.value.data);
      }
    } catch (err) {
      console.error("Dashboard loading failed:", err);
      setError(
        "Unable to load match data. Check that Spring Boot is running on port 8080."
      );
    }
  }, [matchId]);

  useEffect(() => {
    loadDashboard();

    const interval = setInterval(loadDashboard, 10000);

    return () => clearInterval(interval);
  }, [loadDashboard]);

  if (error && !summary) {
    return (
      <div className="page-container">
        <div className="error-card">
          <h2>Unable to load dashboard</h2>
          <p>{error}</p>
          <button className="primary-button" onClick={loadDashboard}>
            Retry
          </button>
        </div>
      </div>
    );
  }

  if (!summary) {
    return <div className="loading-state">Loading cricket dashboard...</div>;
  }

  const currentInnings =
    summary.innings?.find((item) => item.status === "IN_PROGRESS") ||
    summary.innings?.[0];

  return (
    <main className="page-container">
      <section className="welcome-row">
        <div>
          <p className="eyebrow">MATCH CENTER / OVERVIEW</p>
          <h1>Cricket Dashboard</h1>
          <p className="muted">
            Follow the match, innings and player performance.
          </p>
        </div>

        <button className="refresh-button" onClick={loadDashboard}>
          ↻ Refresh
        </button>
      </section>

      {error && <p className="inline-error">{error}</p>}

      <section className="match-banner">
        <div className="match-banner-top">
          <span className="competition-label">{summary.matchType} MATCH</span>
          <span
            className={
              summary.status === "LIVE"
                ? "pill live-pill"
                : "pill"
            }
          >
            {summary.status === "LIVE" && <span className="status-dot" />}
            {summary.status}
          </span>
        </div>

        <div className="match-versus">
          <div className="team-side">
            <div className="team-emblem">IND</div>
            <h2>{summary.team1}</h2>
          </div>

          <div className="versus">VS</div>

          <div className="team-side">
            <div className="team-emblem australia-emblem">AUS</div>
            <h2>{summary.team2}</h2>
          </div>
        </div>

        <div className="match-location">
          <span>⌖ {summary.venue}</span>
          <span>
            {summary.matchDate
              ? new Date(summary.matchDate).toLocaleString()
              : ""}
          </span>
        </div>
      </section>

      <section className="section-heading">
        <div>
          <p className="eyebrow">LIVE SCORE</p>
          <h2>Innings scoreboard</h2>
        </div>
        <span className="muted">Match #{summary.matchId}</span>
      </section>

      <section className="score-grid">
        {summary.innings?.map((innings) => (
          <ScoreCard key={innings.inningsId} innings={innings} />
        ))}
      </section>

      {currentInnings && (
        <section className="info-strip">
          <div>
            <span className="info-label">Batting</span>
            <strong>{currentInnings.battingTeam}</strong>
          </div>
          <div>
            <span className="info-label">Score</span>
            <strong>
              {currentInnings.runs}/{currentInnings.wickets}
            </strong>
          </div>
          <div>
            <span className="info-label">Overs</span>
            <strong>{currentInnings.overs}</strong>
          </div>
          <div>
            <span className="info-label">Innings</span>
            <strong>{currentInnings.inningsNumber}</strong>
          </div>
        </section>
      )}

      <section className="data-grid">
        <article className="panel">
          <div className="panel-heading">
            <div>
              <p className="eyebrow">BATTING</p>
              <h3>Batting statistics</h3>
            </div>
            <span className="panel-icon">🏏</span>
          </div>

          <BattingTable stats={batting} playerName="Virat Kohli" />
          <p className="table-note">
            Statistics for player ID 1 in the selected innings.
          </p>
        </article>

        <article className="panel">
          <div className="panel-heading">
            <div>
              <p className="eyebrow">BOWLING</p>
              <h3>Bowling statistics</h3>
            </div>
            <span className="panel-icon">🎯</span>
          </div>

          <BowlingTable stats={bowling} playerName="Mitchell Starc" />
          <p className="table-note">
            Statistics for player ID 2 in the selected innings.
          </p>
        </article>

        <article className="panel recent-panel">
          <div className="panel-heading">
            <div>
              <p className="eyebrow">BALL BY BALL</p>
              <h3>Recent deliveries</h3>
            </div>
            <span className="muted">Latest first</span>
          </div>

          <RecentBalls deliveries={deliveries} />

          <p className="table-note">
            W = wicket, Wd = wide, Nb = no-ball.
          </p>
        </article>
      </section>
    </main>
  );
}

export default Dashboard;