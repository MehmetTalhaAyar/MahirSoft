import "./CompanyDetails.css";

function CompanyDetails({ company }) {
  return (
    <div className="company-details">
      <h1>{company.companyName}</h1>
      <span>{company.requestFor}</span>
      <p>{company.description}</p>
      <button>Yes</button>
      <button>No</button>
    </div>
  );
}

export default CompanyDetails;
