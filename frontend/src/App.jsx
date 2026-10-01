import { useState } from "react";
import Navbar from "./components/Navbar";
import Dashboard from "./pages/Dashboard";
import Matches from "./pages/Matches";
import Teams from "./pages/Teams";
import Players from "./pages/Players";
import MatchDetails from "./pages/MatchDetails";
import "./App.css";

function App() {
  const [page, setPage] = useState("dashboard");
  const [selectedMatchId, setSelectedMatchId] = useState(1);

  function openMatch(id) {
    setSelectedMatchId(id);
    setPage("match-details");
  }

  function renderPage() {
    switch (page) {
      case "matches":
        return <Matches onOpenMatch={openMatch} />;

      case "teams":
        return <Teams />;

      case "players":
        return <Players />;

      case "match-details":
        return <MatchDetails matchId={selectedMatchId} />;

      default:
        return <Dashboard matchId={1} />;
    }
  }

  return (
    <div className="app-shell">
      <Navbar page={page} setPage={setPage} />
      {renderPage()}

      <footer className="footer">
  <div>
    <strong>CricketLive</strong>
    <span>Cricket Score Management System</span>
  </div>

  <div className="student-info">
    <strong>Ankita Masal</strong>
    <span>Roll No:- 03</span>
  </div>
</footer>
    </div>
  );
}

export default App;