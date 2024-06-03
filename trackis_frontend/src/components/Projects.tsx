import React from "react";
import { Link, useLoaderData } from "react-router-dom";
import { ProjectResource } from "../types/ProjectTypes";
import { HalResource } from "../types/HalTypes";

export default function Projects() {
  const projectData = useLoaderData() as HalResource;
  const projectList = projectData._embedded?.projects as ProjectResource[];

  return (
    <div>
      {projectList?.map((project) => (
        <div key={project.id}>
          <Link to={"/projects/" + project.id.toString()}>{project.title}</Link>
        </div>
      ))}
    </div>
  );
}
