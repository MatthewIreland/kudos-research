package parsing;

import java.util.regex.Pattern;

public class WorkParserConstants {

    // Magic constants identifying the different groups in the regular expression.
    public static final String LANGUAGE_GROUP = "language";
    public static final String MARKER_HOST_GROUP = "host";
    public static final String MARKER_PORT_GROUP = "port";
    public static final String UUID_GROUP = "uuid";
    public static final String CONTENTS_GROUP = "contents";

    /**
     * A regular expression to find `automarkable` environments, and extract args and contents.
     *
     * Valid environments look like:
     *
     * <p><code><pre>
     * \begin{automarkable}{language}{host}{port}{uuid}
     *   ...
     *   contents
     *   ...
     * \end{automarkable}
     * </pre></code>
     * */
    public static final Pattern automarkableRegex;

    static {
        String languageArg = "\\{(?<" + LANGUAGE_GROUP + ">.*?)\\}";
        String hostArg = "\\{(?<" + MARKER_HOST_GROUP + ">.*?)\\}";
        String portArg = "\\{(?<" + MARKER_PORT_GROUP + ">.*?)\\}";
        String uuidArg = "\\{(?<" + UUID_GROUP + ">.*?)\\}";

        String pattern =
                "\\\\begin\\{automarkable\\}" + languageArg + hostArg + portArg + uuidArg
                    + "(?<" + CONTENTS_GROUP + ">.*?)" +
                "\\\\end\\{automarkable\\}";

        automarkableRegex = Pattern.compile(pattern, Pattern.DOTALL);
    }

    public static final String CRSID_GROUP = "crsid";

    /**
     * A regular expression to find the student's CRSID in the submission.
     *
     * For example, it would extract `spqr1` from:
     *
     * <p><code><pre>
     * \newcommand{\studentemail}{spqr1@cam.ac.uk}
     * </pre></code>
     * */
    public static final Pattern crsidRegex;

    static {
        String pattern = "\\\\newcommand\\{\\\\studentemail\\}\\{(?<" + CRSID_GROUP + ">\\w+?)@cam\\.ac\\.uk\\}";

        crsidRegex = Pattern.compile(pattern, Pattern.DOTALL);
    }
}
