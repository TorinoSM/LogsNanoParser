package Parser;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {


    private static final String rx_filter_01 = "^.*?a message was sent to the messenger's server, message is.*?$";
    private static final String rx_filter_02 = "^.*?receive a message in sbermess handler, message is.*?$";
    private static String matching_line_01 = "[2/2/18 15:50:35:550 MSK] 00000106 SystemOut     O 2018-02-02 15:50:35 INFO  [default-workqueue-2]: org.JaxrsClientCallback.handleResponse(77) : a message was sent to the messenger's server, message is: ";
    private static String matching_line_02 = "[2/2/18 15:49:22:371 MSK] 000000f9 SystemOut     O 2018-02-02 15:49:22 INFO  [WebContainer : 0]: sbermess.SbermessRequestHandler.sendMessage(35) : receive a message in sbermess handler, message is: ";
    private static Pattern pattern_filter_01 = Pattern.compile(rx_filter_01);
    private static Pattern pattern_filter_02 = Pattern.compile(rx_filter_02);

    public static void main(String[] args) {

        Matcher matcher_01 = pattern_filter_01.matcher(matching_line_01);
        Matcher matcher_02 = pattern_filter_02.matcher(matching_line_02);

        matcher_01.matches();
//        matcher.group(1);

        System.out.println(matcher_01.group());

        matcher_02.matches();
//        matcher.group(1);

        System.out.println(matcher_02.group());

    }


}
