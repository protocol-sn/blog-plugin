import {BlogMetadata} from './blog-metadata';

export interface Blog {
  metadata: BlogMetadata;
  blogText:string | null;
  blogFormat: string | null;
}
