export interface Comment {
    id: number;
	message: string;
    created_at: Date;
    author_id: number;
    post_id: number;
}