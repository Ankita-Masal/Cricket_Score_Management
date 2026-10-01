import "./Teams.css";
import { useEffect, useState } from "react";
import API from "../services/api";

function Teams() {
  const [teams, setTeams] = useState([]);

  const [form, setForm] = useState({
    name: "",
    country: "",
    shortName: "",
    logoUrl: "",
  });

  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  async function loadTeams() {
    try {
      const response = await API.get("/teams");

      setTeams(response.data);
      setError("");
    } catch (err) {
      console.error(err);
      setError("Unable to load teams.");
    }
  }

  useEffect(() => {
    loadTeams();
  }, []);

  function handleChange(event) {
    setForm({
      ...form,
      [event.target.name]: event.target.value,
    });
  }

  async function handleSubmit(event) {
    event.preventDefault();

    setMessage("");
    setError("");

    try {
      await API.post("/teams", form);

      setForm({
        name: "",
        country: "",
        shortName: "",
        logoUrl: "",
      });

      setMessage("Team created successfully.");

      loadTeams();
    } catch (err) {
      console.error(err);
      setError("Could not create team.");
    }
  }

  async function deleteTeam(id) {
    if (!window.confirm("Delete this team?")) {
      return;
    }

    try {
      await API.delete(`/teams/${id}`);

      setMessage("Team deleted.");

      loadTeams();
    } catch (err) {
      console.error(err);
      setError("Could not delete team.");
    }
  }

  return (
<div className="page-content teams-page">
      <div className="page-title-row">

        <div>

          <p className="eyebrow">
            TEAM MANAGEMENT
          </p>

          <h1>
            Teams
          </h1>

          <p className="muted">
            Manage cricket teams registered in the system.
          </p>

        </div>

      </div>


      <div className="management-grid">


        {/* Add Team */}

        <section className="panel form-panel">

          <div className="panel-heading">

            <div>

              <h3>
                Add Team
              </h3>

              <p>
                Enter team details.
              </p>

            </div>

          </div>


          <form
            onSubmit={handleSubmit}
            className="form-stack"
          >

            <label>

              Team Name

              <input
                name="name"
                value={form.name}
                onChange={handleChange}
                placeholder="India"
                required
              />

            </label>


            <label>

              Country

              <input
                name="country"
                value={form.country}
                onChange={handleChange}
                placeholder="India"
                required
              />

            </label>


            <label>

              Short Name

              <input
                name="shortName"
                value={form.shortName}
                onChange={handleChange}
                placeholder="IND"
                maxLength="10"
                required
              />

            </label>


            <label>

              Logo URL

              <input
                name="logoUrl"
                value={form.logoUrl}
                onChange={handleChange}
                placeholder="https://example.com/logo.png"
              />

            </label>


            <button
              type="submit"
              className="primary-button"
            >
              + Create Team
            </button>

          </form>


          {message && (
            <p className="success-text">
              {message}
            </p>
          )}


          {error && (
            <p className="error-text">
              {error}
            </p>
          )}

        </section>



        {/* Team List */}

        <section className="panel">

          <div className="panel-heading">

            <div>

              <h3>
                Registered Teams
              </h3>

              <p>
                {teams.length} teams
              </p>

            </div>

          </div>


          <div className="entity-list">

            {teams.map((team) => (

              <div
                className="entity-row"
                key={team.id}
              >

                <div className="entity-avatar">
                  {team.shortName}
                </div>


                <div className="entity-info">

                  <strong>
                    {team.name}
                  </strong>

                  <p>
                    {team.country} · {team.shortName}
                  </p>

                </div>


                <button
                  className="delete-button"
                  onClick={() => deleteTeam(team.id)}
                >
                  Delete
                </button>

              </div>

            ))}


            {teams.length === 0 && (

              <p className="empty-message">
                No teams registered yet.
              </p>

            )}

          </div>

        </section>

      </div>

    </div>
  );
}

export default Teams;