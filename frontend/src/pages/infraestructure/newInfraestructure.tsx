import React from "react";
import { InfraestructureForm } from "components/templates";
import Page from "../page";



const NewInfraestructurePage = () => {
  return <Page title="Nueva Infraestructura">
      <div className="FORM">
        <InfraestructureForm />
      </div>
      
  </Page>;
};

export default NewInfraestructurePage;