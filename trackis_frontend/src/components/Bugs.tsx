import React from "react";
import { Link, useLoaderData } from "react-router-dom";
import { BugResource } from "../types/BugTypes";
import { HalResource } from "../types/HalTypes";

export default function Bugs() {
  const bugData = useLoaderData() as HalResource;
  const bugList = bugData._embedded?.bugs as BugResource[];

  return (
    <div>
      {bugList?.map((bug) => (
        <div key={bug.id}>
          <Link to={bug.id.toString()}>{bug.title}</Link>
        </div>
      ))}
    </div>
  );
}
