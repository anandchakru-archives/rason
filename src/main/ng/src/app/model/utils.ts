import { BucketSlugs } from './be/bucketslugs';

export class Utils{
    public static push(bucketSlugs: BucketSlugs, slug:string){
        bucketSlugs.slugs[slug]=slug;
    }
}