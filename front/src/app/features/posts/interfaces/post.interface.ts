import { User } from "../../../core/interfaces/user.interface";
import { Topic } from "../../topics/interfaces/topic.interface";

export interface Post {
    id: number,
	title: string,
    creationDate: Date,
    content: string,
    author: User,
    topic: Topic
}