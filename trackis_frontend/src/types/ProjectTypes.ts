import { HalResource } from "./HalTypes";
import { UserDTO } from "./UserTypes";

interface ProjectDTO {
  id: number;
  title: string;
  description: string;
  owner: UserDTO;
}

interface ProjectResource extends ProjectDTO, HalResource {}

export type { ProjectResource, ProjectDTO };
