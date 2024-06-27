import React, { useEffect, useState } from "react";
import "./AdminDashboard.css";
import CompanyDetails from "./companyDetails";
import { getAllRequests } from "./api";

function AdminDashboard() {
  
  const [requestDatas,setRequestDatas] = useState([]);
  const [selectedCompany, setSelectedCompany] = useState();

  // Function to handle click on company item
  const handleCompanyClick = (company) => {
    setSelectedCompany(company);
  };

  useEffect(()=>{
    handleGettingAllRequests()
  },[])

  const handleReply = (selectedValue) =>{
    const newRequests = requestDatas.filter((request)=>{
      if(request.id !== selectedValue.id){
        return request;
      }
    })

    setRequestDatas(newRequests);

  }

  const handleGettingAllRequests = async() =>{

    const response = await getAllRequests();

    if(response.status === 200){
      setRequestDatas(response.data.map((request) => {
        return {
        id : request.companyCreateRequestId,
        companyName : request.name,
        requestFor : request.user.fullName,
        description : request.description
      }
      }));

    }



  }

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
                {requestDatas.map((item) => (
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
              {selectedCompany !== undefined && selectedCompany !== null ? <CompanyDetails handleReply={handleReply} company={selectedCompany} /> : <></>}
            </td>
          </tr>
        </tbody>
      </table>
    </main>
  );
}

export default AdminDashboard;
