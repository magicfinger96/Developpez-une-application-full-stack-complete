export interface Post {
    id: number,
	title: string,
    created_at: Date,
    content: string,
    author_id: number,
    topic_id: number
}