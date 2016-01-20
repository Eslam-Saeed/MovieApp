package Model;

/**
 * Created by esveer on 1/4/16.
 */
public class Trailers {

    private String trailerName;
    private String trailerURL;
    private String trailerImagePath;

    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }

    public void setTrailerImagePath(String trailerKey) {
        //this.trailerImagePath = trailerImagePath;
        this.trailerImagePath = "http://img.youtube.com/vi/"+trailerKey+"/1.jpg";
    }

    public String getTrailerImagePath() {
        return trailerImagePath;
    }

    public void setTrailerURL(String trailerURL) {
        this.trailerURL = trailerURL;
    }

    public String getTrailerURL() {
        return trailerURL;
    }
}
