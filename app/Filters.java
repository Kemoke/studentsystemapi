import javax.inject.*;
import play.*;
import play.filters.cors.CORSFilter;
import play.filters.gzip.GzipFilter;
import play.http.DefaultHttpFilters;
import play.mvc.EssentialFilter;
import play.http.HttpFilters;


public class Filters extends DefaultHttpFilters {

    @Inject
    public Filters(GzipFilter gzipFilter, CORSFilter corsFilter){
        super(gzipFilter, corsFilter);
    }
}
