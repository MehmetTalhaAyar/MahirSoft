import React, { useState } from "react";
import "./AdminDashboard.css";
import CompanyDetails from "./companyDetails";

function AdminDashboard() {
  const data = [
    {
      id: 1,
      companyName: "Company A",
      requestFor: "Product X",
      description: "Description for Product X ",
    },
    {
      id: 2,
      companyName: "Company B",
      requestFor: "Service Y",
      description: "Description for Service Y",
    },
    {
      id: 4,
      companyName: "Company C",
      requestFor: "Product X",
      description: "Description for Product X",
    },
    {
      id: 5,
      companyName: "Company D",
      requestFor: "Service Y",
      description: "Description for Service Y",
    },
    {
      id: 6,
      companyName: "Company E",
      requestFor: "Product X",
      description: "Description for Product X",
    },
    {
      id: 7,
      companyName: "Company F",
      requestFor: "Service Y",
      description: "Description for Service Y",
    },
    {
      id: 8,
      companyName: "Company E",
      requestFor: "Product X",
      description: "Description for Product X",
    },
    {
      id: 9,
      companyName: "Company F",
      requestFor: "Service Y",
      description: "Description for Service Y",
    },
    // Add more data as needed
  ];

  const [selectedCompany, setSelectedCompany] = useState(data[0]);

  // Function to handle click on company item
  const handleCompanyClick = (company) => {
    setSelectedCompany(company);
  };

  return (
    <main className="dashboard_company">
      <h1>Dashboard</h1>
      <table className="company-table">
        <thead>
          <tr>
            <th>Company</th>
            <th>Company Details</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>
              <ul className="requests">
                {data.map((item) => (
                  <li
                    key={item.id}
                    className="companylist"
                    onClick={() => handleCompanyClick(item)}
                  >
                    {item.companyName}
                  </li>
                ))}
              </ul>
            </td>
            <td>
              <CompanyDetails company={selectedCompany} />
            </td>
          </tr>
        </tbody>
      </table>
    </main>
  );
}

export default AdminDashboard;
