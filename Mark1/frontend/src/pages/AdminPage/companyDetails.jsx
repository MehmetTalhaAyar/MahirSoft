import { useEffect } from "react";
import "./CompanyDetails.css";
import { replyRequests } from "./api";

function CompanyDetails({handleReply, company }) {

  const handleAcceptRequest = async () =>{

    const response = await replyRequests({
      requestId: company.id,
      isOkey: true
    });

    if(response.status === 200){
      console.log(response.data);
      handleReply(company);
    }

  }
  useEffect(()=>{
    console.log("değişti")
  },[company])

  const handleRefuseRequest = async () =>{

    const response = await replyRequests({
      requestId: company.id,
      isOkey: false
    });

    if(response.status === 200){
      console.log(response.data);
      handleReply(company);
    }

  }

  return (
    <div className="company-details">
      <h1>{company.companyName}</h1>
      <span>{company.requestFor}</span>
      <p>{company.description}</p>
      <button onClick={handleAcceptRequest} >Yes</button>
      <button onClick={handleRefuseRequest} >No</button>
    </div>
  );
}

export default CompanyDetails;
