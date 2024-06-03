import React from "react";
import {
  createBrowserRouter,
  createRoutesFromElements,
  Route,
} from "react-router-dom";
import Home from "./Home";
import Registration from "./Registration";
import User from "./User";
import Projects from "./Projects";
import Project from "./Project";
import { fetchData } from "../utils";
import Bug from "./Bug";
import Bugs from "./Bugs";

const SERVER_URL: string = import.meta.env.VITE_API_CONTEXT_PATH;

const USERS_URL = SERVER_URL + "/users";
const PROJECTS_URL = SERVER_URL + "/projects";
const BUGS_URL = SERVER_URL + "/bugs";

const router = createBrowserRouter(
  createRoutesFromElements(
    <>
      <Route path="/">
        <Route index element={<Home />} />
        <Route path=":username">
          <Route
            index
            loader={({ params }) =>
              fetchData(USERS_URL + `/${params.username}`)
            }
            element={<User />}
          />
          <Route path="projects">
            <Route
              path="owned"
              loader={({ params }) =>
                fetchData(PROJECTS_URL + "?owner=" + `${params.username}`)
              }
              element={<Projects />}
            ></Route>
            <Route
              path="shared"
              loader={({ params }) =>
                fetchData(
                  PROJECTS_URL + "?collaborator=" + `${params.username}`
                )
              }
              element={<Projects />}
            ></Route>
          </Route>
        </Route>
      </Route>
      <Route path="projects">
        <Route
          index
          loader={() => fetchData(PROJECTS_URL)}
          element={<Projects />}
        />
        <Route path=":projectId">
          <Route
            index
            loader={({ params }) =>
              fetchData(PROJECTS_URL + `/${params.projectId}`)
            }
            element={<Project />}
          />
          <Route path="bugs">
            <Route
              index
              element={<Bugs />}
              loader={({ params }) =>
                fetchData(PROJECTS_URL + `/${params.projectId}` + "/bugs")
              }
            />
            <Route
              path=":bugId"
              loader={({ params }) => fetchData(BUGS_URL + `/${params.bugId}`)}
              element={<Bug />}
            />
          </Route>
        </Route>
      </Route>
      <Route path="register" element={<Registration />} />
    </>
  )
);

export default router;
