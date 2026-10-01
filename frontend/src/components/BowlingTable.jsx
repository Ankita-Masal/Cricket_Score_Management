function BowlingTable({ stats, playerName }) {
  if (!stats) {
    return <p className="muted">Bowling statistics are not available yet.</p>;
  }

  return (
    <div className="table-scroll">
      <table>
        <thead>
          <tr>
            <th>Bowler</th>
            <th>Overs</th>
            <th>Runs</th>
            <th>Wickets</th>
            <th>Economy</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td className="player-name">{playerName}</td>
            <td>{stats.overs}</td>
            <td>{stats.runsConceded}</td>
            <td>{stats.wickets}</td>
            <td>{stats.economy}</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}

export default BowlingTable;