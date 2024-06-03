import React from "react";
import { UserResource } from "../types/UserTypes";
import { Link, useLoaderData, useParams } from "react-router-dom";

export default function User() {
  const userData = useLoaderData() as UserResource;
  let { username } = useParams();

  return (
    <div>
      <div>{username}</div>
      <Link to="projects/owned">Owned Projects</Link>
      <br></br>
      <Link to="projects/shared">Shared Projects</Link>
    </div>
  );
}
