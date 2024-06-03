import { HalResource } from "./HalTypes";
import { RoleDTO } from "./RoleTypes";

interface UserDTO {
  id: number;
  username: string;
  password?: string;
  email: string;
  roles: RoleDTO[];
}

interface UserResource extends UserDTO, HalResource {}

export type { UserDTO, RoleDTO, UserResource };
