import React from "react";
import { Link, useLoaderData } from "react-router-dom";
import { HalResource } from "../types/HalTypes";
import { UserResource } from "../types/UserTypes";

export default function Users() {
  const userData = useLoaderData() as HalResource;
  const userList = userData._embedded?.users as UserResource[];

  return (
    <div>
      {userList?.map((user) => (
        <div key={user.id}>
          <Link to={user.username}>{user.username}</Link>
        </div>
      ))}
    </div>
  );
}
