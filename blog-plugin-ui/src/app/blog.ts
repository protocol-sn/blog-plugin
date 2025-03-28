export interface Blog {
  id: string | null;
  blogTitle: string | null;
  tags: string | null;
  blogText:string | null;
  blogFormat: string | null;
  createdAt: Date | null;
  updatedAt: Date | null;
}
