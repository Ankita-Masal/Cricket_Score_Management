function RecentBalls({ deliveries }) {
  function getBallLabel(delivery) {
    if (delivery.deliveryType === "WIDE") return "Wd";
    if (delivery.deliveryType === "NO_BALL") return "Nb";
    if (delivery.wicket) return "W";
    if (delivery.extras > 0) return `${delivery.runs}+${delivery.extras}`;
    return String(delivery.runs);
  }

  function getBallClass(delivery) {
    if (delivery.wicket) return "ball wicket-ball";
    if (delivery.deliveryType === "WIDE" ||
        delivery.deliveryType === "NO_BALL") {
      return "ball extra-ball";
    }
    if (delivery.runs === 4 || delivery.runs === 6) {
      return "ball boundary-ball";
    }
    return "ball";
  }

  if (!deliveries?.length) {
    return <p className="muted">No deliveries available yet.</p>;
  }

  return (
    <div className="recent-balls">
      {deliveries
        .slice()
        .sort((a, b) => b.id - a.id)
        .slice(0, 12)
        .map((delivery) => (
          <div
            key={delivery.id}
            className={getBallClass(delivery)}
            title={`Over ${delivery.overNumber}, ball ${delivery.ballNumber}`}
          >
            {getBallLabel(delivery)}
          </div>
        ))}
    </div>
  );
}

export default RecentBalls;