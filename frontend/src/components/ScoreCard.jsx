function ScoreCard({ innings }) {
  const overs = innings.overs ?? "0.0";

  return (
    <article className="score-card">
      <div className="score-card-top">
        <span className="team-label">{innings.battingTeam}</span>
        <span className={innings.status === "IN_PROGRESS" ? "pill live-pill" : "pill"}>
          {innings.status === "IN_PROGRESS" ? "LIVE" : innings.status}
        </span>
      </div>

      <div className="score-number">
        {innings.runs ?? 0}
        <span className="score-divider">/</span>
        {innings.wickets ?? 0}
      </div>

      <div className="score-meta">
        <span>Overs</span>
        <strong>{overs}</strong>
      </div>

      <div className="overs-track">
        <div
          className="overs-progress"
          style={{
            width: `${Math.min(
              ((Number(overs.split(".")[0]) * 6 +
                Number(overs.split(".")[1] || 0)) / 120) * 100,
              100
            )}%`,
          }}
        />
      </div>

      <p className="score-footer">
        {innings.inningsNumber === 1 ? "1st Innings" : "2nd Innings"}
      </p>
    </article>
  );
}

export default ScoreCard;