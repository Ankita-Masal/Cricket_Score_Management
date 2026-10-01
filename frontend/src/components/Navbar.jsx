function Navbar({ page, setPage }) {
  const links = [
    { label: "Dashboard", value: "dashboard" },
    { label: "Matches", value: "matches" },
    { label: "Teams", value: "teams" },
    { label: "Players", value: "players" },
  ];

  return (
    <header className="navbar">
      <button
        className="brand"
        onClick={() => setPage("dashboard")}
      >
        <span className="brand-icon">🏏</span>
        <span>Cricket<span className="brand-accent">Live</span></span>
      </button>

      <nav className="nav-links">
        {links.map((link) => (
          <button
            key={link.value}
            className={page === link.value ? "nav-link active" : "nav-link"}
            onClick={() => setPage(link.value)}
          >
            {link.label}
          </button>
        ))}
      </nav>

      <div className="nav-status">
        <span className="status-dot" />
        Score Center
      </div>
    </header>
  );
}

export default Navbar;