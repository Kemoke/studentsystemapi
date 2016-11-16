import javax.inject.*;
import play.*;
import play.mvc.EssentialFilter;
import play.http.HttpFilters;

@Singleton
public class Filters implements HttpFilters {

    private final Environment env;

    /**
     * @param env Basic environment settings for the current application.
     */
    @Inject
    public Filters(Environment env){
        this.env = env;
    }

    @Override
    public EssentialFilter[] filters() {
      // Use the example filter if we're running development mode. If
      // we're running in production or test mode then don't use any
      // filters at all.
        return new EssentialFilter[]{};
    }

}
