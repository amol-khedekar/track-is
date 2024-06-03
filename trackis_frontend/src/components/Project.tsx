import React from "react";
import { Link, useLoaderData } from "react-router-dom";
import { ProjectResource } from "../types/ProjectTypes";

export default function Project() {
  const projectData = useLoaderData() as ProjectResource;

  return (
    <div>
      <div>{projectData.title}</div>
      <div>{projectData.description}</div>
      <Link to="bugs">Bugs</Link>
    </div>
  );
}
