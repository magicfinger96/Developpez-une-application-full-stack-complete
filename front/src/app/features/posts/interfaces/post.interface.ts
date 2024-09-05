export interface Post {
    id: number,
	title: string,
    created_at: Date,
    description: string,
    author_id: number,
    topic_id: number
}