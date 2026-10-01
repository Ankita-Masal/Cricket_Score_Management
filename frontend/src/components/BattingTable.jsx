function BattingTable({ stats, playerName }) {
  if (!stats) {
    return <p className="muted">Batting statistics are not available yet.</p>;
  }

  return (
    <div className="table-scroll">
      <table>
        <thead>
          <tr>
            <th>Batter</th>
            <th>R</th>
            <th>B</th>
            <th>4s</th>
            <th>6s</th>
            <th>SR</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td className="player-name">{playerName}</td>
            <td>{stats.runs}</td>
            <td>{stats.balls}</td>
            <td>{stats.fours}</td>
            <td>{stats.sixes}</td>
            <td>{stats.strikeRate}</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}

export default BattingTable;