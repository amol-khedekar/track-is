import React from "react";
import { useLoaderData } from "react-router-dom";
import { BugResource } from "../types/BugTypes";

export default function Bug() {
  const bugData = useLoaderData() as BugResource;

  return (
    <div>
      <div>{bugData.title}</div>
      <div>{bugData.description}</div>
    </div>
  );
}
