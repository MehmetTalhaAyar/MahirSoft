import "./projectDetails.css";
export default function ProjectDetails() {
  const data = [
    {
      fullName: "John Doe",
      email: "john@example.com",
      GSB: "GSB1",
      company: "ABC Inc",
    },
    {
      fullName: "Jane Smith",
      email: "jane@example.com",
      GSB: "GSB2",
      company: "XYZ Corp",
    },
    {
      fullName: "Bob Johnson",
      email: "bob@example.com",
      GSB: "GSB3",
      company: "123 Industries",
    },
    {
      fullName: "John Doe",
      email: "john@example.com",
      GSB: "GSB1",
      company: "ABC Inc",
    },
    {
      fullName: "Jane Smith",
      email: "jane@example.com",
      GSB: "GSB2",
      company: "XYZ Corp",
    },
    {
      fullName: "Bob Johnson",
      email: "bob@example.com",
      GSB: "GSB3",
      company: "123 Industries",
    },
    {
      fullName: "John Doe",
      email: "john@example.com",
      GSB: "GSB1",
      company: "ABC Inc",
    },
    {
      fullName: "Jane Smith",
      email: "jane@example.com",
      GSB: "GSB2",
      company: "XYZ Corp",
    },
    {
      fullName: "Bob Johnson",
      email: "bob@example.com",
      GSB: "GSB3",
      company: "123 Industries",
    },
    {
      fullName: "Bob Johnson",
      email: "bob@example.com",
      GSB: "GSB3",
      company: "123 Industries",
    },
    {
      fullName: "John Doe",
      email: "john@example.com",
      GSB: "GSB1",
      company: "ABC Inc",
    },
    {
      fullName: "Jane Smith",
      email: "jane@example.com",
      GSB: "GSB2",
      company: "XYZ Corp",
    },
    {
      fullName: "Bob Johnson",
      email: "bob@example.com",
      GSB: "GSB3",
      company: "123 Industries",
    },
    {
      fullName: "Bob Johnson",
      email: "bob@example.com",
      GSB: "GSB3",
      company: "123 Industries",
    },
    {
      fullName: "John Doe",
      email: "john@example.com",
      GSB: "GSB1",
      company: "ABC Inc",
    },
    {
      fullName: "Jane Smith",
      email: "jane@example.com",
      GSB: "GSB2",
      company: "XYZ Corp",
    },
    {
      fullName: "Bob Johnson",
      email: "bob@example.com",
      GSB: "GSB3",
      company: "123 Industries",
    },
  ];

  return (
    <main>
      <h1>Project Details</h1>
      <h2 className="project_details_name">Proje Name</h2>
      <h2 className="project_leader_name">Leader Name</h2>

      <div className="project_member_stages">
        <section className="project_member">
          <div>
            <h1>Project Member</h1>
          </div>

          <div className="table-container">
            <table>
              <thead>
                <tr>
                  <th>Full Name</th>
                  <th>Email</th>
                  <th>GSB</th>
                  <th>Company</th>
                </tr>
              </thead>
              <tbody>
                {data.map((item, index) => (
                  <tr key={index}>
                    <td>{item.fullName}</td>
                    <td>{item.email}</td>
                    <td>{item.GSB}</td>
                    <td>{item.company}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </section>
        <section className="project_stages">
          <div>
            <h1>Project Stages</h1>
          </div>
        </section>
      </div>
    </main>
  );
}
