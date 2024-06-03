import { HalResource } from "./HalTypes";
import { UserDTO } from "./UserTypes";

interface BugDTO {
  id: number;
  title: string;
  description: string;
  author: UserDTO;
}

interface BugResource extends BugDTO, HalResource {}

export type { BugResource, BugDTO };
