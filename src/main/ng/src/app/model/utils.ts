import { BucketSlugs } from './be/bucketslugs';

export class Utils{
    private static ALPH_NUMERIC = new RegExp(/^[a-z0-9]{3,10}$/i);
    public static push(bucketSlugs: BucketSlugs, slug:string){
        bucketSlugs.slugs[slug]=slug;
    }
    public static isAlphaNumeric3To10(slug:string):boolean{
        return Utils.ALPH_NUMERIC.test(slug);
    }
}